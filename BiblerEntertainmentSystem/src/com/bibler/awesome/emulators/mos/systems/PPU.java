package com.bibler.awesome.emulators.mos.systems;

import com.bibler.awesome.emulators.mos.ui.MainFrame;
import com.bibler.awesome.emulators.mos.utils.Logger;

public class PPU {
	
	public PPUMemoryManager manager;
	private Emulator emulator;
	
	private int scanline;
	private int cycles;
	private int lastValue;
	boolean vblankFlag;
	boolean sprite0Hit;
	boolean spriteOverflow;
	private boolean nmi;
	
	private int vAddress;
	private int tempVAddress;
	private int xScroll;
	private int wToggle;
	private int vramInc;
	private int spritePT;
	private int bgPT;
	private int spriteOAMAddress;
	private int bitmapShiftOne;
	private int bitmapShiftTwo;
	private int relCycle;
	private int curNT;
	private int curAttr;
	private int nextAttr;
	private int tileLow;
	private int tileHigh;
	private int frameCount;
	
	private boolean showBG;
	private boolean showSprites;
	private Memory secondaryOAM;
	private int[] bitmap = new int[256 * 240];
	private MainFrame mainFrame;
	private long startTime;
	private StringBuilder builder = new StringBuilder();
	private Logger logger = new Logger();
	private boolean firstWrite = true;
	
	public PPU() {
		manager = new PPUMemoryManager();
		secondaryOAM = new Memory(32);
	}
	
	public void setMirroring(boolean horiz, boolean vert) {
		manager.setMirroring(horiz, vert);
	}
	
	public void setEmulator(Emulator emulator) {
		this.emulator = emulator;
	}
	
	public int read(int register) {
		int retValue = 0;
		switch(register) {
			case 2:
				wToggle = 0;
				if (scanline == 241) {
					if (cycles == 1) {//suppress NMI flag if it was just turned on this same cycle
						vblankFlag = false;
					}
				}
				retValue = (vblankFlag ? 0x80 : 0)
						| (sprite0Hit ? 0x40 : 0)
						| (spriteOverflow ? 0x20 : 0)
						| (lastValue & 0x1f);
				vblankFlag = false;
				break;
			case 4:
				break;
			case 7:
				if(vAddress < 0x3F00) {
					retValue = lastValue;
					lastValue = manager.read(vAddress);
				} else {
					retValue = manager.read(vAddress);
					lastValue = manager.read(vAddress - 0x1000);
				}
				vAddress += vramInc;
				break;
			default:
				return retValue;
		}
		return retValue;
	}
	
	public void write(int register, int data) {
		switch(register) {
		case 0:
			tempVAddress &= ~0xC00;
			tempVAddress |= ((data & 0x03) << 10);
			////System.out.println("Writing t0: " + tempVAddress);
			vramInc = (data >> 2 & 1) == 1 ? 32 : 1;
			spritePT = (data >> 3 & 1) == 1 ? 0x1000 : 0;
			bgPT = (data >> 4 & 1) == 1 ? 0x1000 : 0;
			nmi = (data >> 7 & 1) == 1;
			break;
		case 1:
			showBG = (data >> 3 & 1) == 1;
			showSprites = (data >> 4 & 1 ) == 1;
			break;
		case 2:
			break;
		case 3:
			spriteOAMAddress = data;
			break;
		case 4:
			manager.write(0x4000 + spriteOAMAddress++, data);
			break;
		case 5:
			if(wToggle == 0) {
				tempVAddress &= ~0x1F;
				tempVAddress |= (data >> 3);
				xScroll = data & 0x7;
			} else {
				 tempVAddress &= ~0x7000;
                 tempVAddress |= ((data & 7) << 12);
                 tempVAddress &= ~0x3e0;
                 tempVAddress |= (data & 0xf8) << 2;
                 //System.out.println("Writing t5 toggle 1: " + tempVAddress);
			}
			wToggle ^= 1;
			break;
		case 6:
			if(wToggle == 0) {
				 tempVAddress &= 0xc0ff;
                 tempVAddress |= ((data & 0x3f) << 8);
                 tempVAddress &= 0x3fff;
                 ////System.out.println("Writing t6 toggle 1: " + tempVAddress);
			} else {
				 tempVAddress &= 0xfff00;
                 tempVAddress |= data;
                 ////System.out.println("Writing t6 toggle 1: " + tempVAddress);
                 vAddress = tempVAddress;
			}
			wToggle ^= 1;
			break;
		case 7:
			manager.write(vAddress, data);
			vAddress += vramInc;
			break;
		}
		
	}
	
	public int[] renderFrame() {
		int attrX;
		int attrY;
		int x;
		int y;
		int tLB;
		int coarseX = tempVAddress & 0x1F;
		for(int i = 0; i < bitmap.length; i++) {
			x = i % 256;
			y = (i / 256);
			curNT = manager.read(0x2000 + (((y / 8) * 32) + (x / 8) + coarseX)) & 0xFF;
			curAttr = manager.read(0x23C0 + (((y / 32) * 8) + (x / 32) + (coarseX / 4))) & 0xFF;
			tLB = (curNT * 0x10) + (y % 8);
			int tileLowByte = manager.read(bgPT + tLB);
			int tileHighByte = manager.read(bgPT + tLB + 8);
			int pixel = grabBit(tileHighByte, (7 - (x % 8))) << 1 | grabBit(tileLowByte, 7 - (x % 8));
			int attrStart = (((y / 32) * 32) * 256) + (((x / 32) * 32));
			attrX = (x / 32) * 4;
			attrY = (y / 32) * 4;
			int ntX = x / 8;
			int ntY = y / 8;
			attrStart = i - attrStart;
			int attrBitShift = (((ntX - attrX) / 2) * 2) + (((ntY - attrY) / 2) * 4);
			int palVal = ((curAttr >> attrBitShift) & 3) << 2;
			bitmap[i] = manager.read(0x3F00 +(palVal + pixel));
		}
		renderSprites();
		return bitmap;
	}
	
	private void renderSprites() {
		int[] spriteByte = null;
		int x;
		int y;
		int tileNum;
		int tileAttr;
		int tileLowByte;
		int tileHighByte;
		int pixel;
		int palVal;
		int offset;
		int priority;
		int flipHoriz;
		int bitToGet;
		for(int i = 63; i >= 0; i--) {
			spriteByte = manager.readNextSprite(i);
			y = spriteByte[0];
			tileNum = spriteByte[1];
			tileAttr = spriteByte[2];
			x = spriteByte[3];
			if(x >= 256 || x < 0 || y < 0 || y >= 240) {
				continue;
			}
			palVal = tileAttr & 3;
			priority = (tileAttr >> 5) & 1;
			flipHoriz = (tileAttr >> 6) & 1;
			int tLB = (tileNum * 16);
			offset = (y * 256) + x;
			for(int tileY = 0; tileY < 8; tileY++) {
				tileLowByte = manager.read(spritePT + tLB);
				tileHighByte = manager.read(spritePT + tLB + 8);
				for(int tileX = 0; tileX < 8; tileX++) {
					if(flipHoriz > 0) {
						bitToGet = tileX;
					} else {
						bitToGet = 7 - tileX;
					}
					pixel = grabBit(tileHighByte, bitToGet) << 1 | grabBit(tileLowByte, bitToGet);
					if(i == 0) {
						if(bitmap[offset + tileX] != 0){
							sprite0Hit = true;
						}
					}
					if(pixel == 0 || priority == 1)
						continue;
					bitmap[offset + tileX] = manager.read(0x3F10 +((palVal * 4) + pixel));
				}
				tLB += 1;
				offset += 256;
			}
		}
	}
	
	public void step() {
		newClock();
	}
	
	public void setStartTime() {
		startTime = System.currentTimeMillis();
	}
	
	private void newClock() {
		if((showBG | showSprites) && scanline < 240 && cycles > 0 && cycles < 256) {
			renderBG((scanline * 256) + (cycles - 1));
		}	
		switch(cycles) {
				case 1:
					if(scanline == 241) {
						vblankFlag = true;
						emulator.setNMI(vblankFlag && nmi);
					}
					else if(scanline == 261) {
						emulator.resetNMI();
						vblankFlag = false;
						sprite0Hit = false;
						spriteOverflow = false;
					}
					break;
				case 2:
				case 10:
				case 18:
				case 26:
				case 34:
				case 42:
				case 50:
				case 58:
				case 66:
				case 74:
				case 82:
				case 90:
				case 98:
				case 106:
				case 114:
				case 122:
				case 130:
				case 138:
				case 146:
				case 154:
				case 162:
				case 170:
				case 178:
				case 186:
				case 194:
				case 202:
				case 210:
				case 218:
				case 226:
				case 234:
				case 242:
				case 250:
				case 322:
				case 330:
					if(visibleScanline()) {
						grabNT();
					}
					break;
				case 4:
				case 12:
				case 20:
				case 28:
				case 36:
				case 44:
				case 52:
				case 60:
				case 68:
				case 76:
				case 84:
				case 92:
				case 100:
				case 108:
				case 116:
				case 124:
				case 132:
				case 140:
				case 148:
				case 156:
				case 164:
				case 172:
				case 180:
				case 188:
				case 196:
				case 204:
				case 212:
				case 220:
				case 228:
				case 236:
				case 244:
				case 252:
				case 324:
				case 332:
					if(visibleScanline()) {
						grabAT();
					}
					break;
				case 6:
				case 14:
				case 22:
				case 30:
				case 38:
				case 46:
				case 54:
				case 62:
				case 70:
				case 78:
				case 86:
				case 94:
				case 102:
				case 110:
				case 118:
				case 126:
				case 134:
				case 142:
				case 150:
				case 158:
				case 166:
				case 174:
				case 182:
				case 190:
				case 198:
				case 206:
				case 214:
				case 222:
				case 230:
				case 238:
				case 246:
				case 254:
				case 326:
				case 334:
					if(visibleScanline()) {
						grabTileLow();
					}
					break;
				case 8:
				case 16:
				case 24:
				case 32:
				case 40:
				case 48:
				case 56:
				case 64:
				case 72:
				case 80:
				case 88:
				case 96:
				case 104:
				case 112:
				case 120:
				case 128:
				case 136:
				case 144:
				case 152:
				case 160:
				case 168:
				case 176:
				case 184:
				case 192:
				case 200:
				case 208:
				case 216:
				case 224:
				case 232:
				case 240:
				case 248:
				case 328:
				case 336:
					if(visibleScanline()) {
						grabTileHigh();
						incrementHoriz();
						if(cycles == 336) {
							bitmapShiftOne >>= 8;
							bitmapShiftTwo >>= 8;
						}
						bitmapShiftOne &= ~0xFF00;
						bitmapShiftOne |= (tileLow << 8);
						bitmapShiftTwo &= ~0xFF00;
						bitmapShiftTwo |= (tileHigh << 8);
						curAttr = nextAttr;
					}
					break;
				case 256:
					if(visibleScanline()) {
						grabTileHigh();
						incrementVert();
						bitmapShiftOne &= ~0xFF00;
						bitmapShiftOne |= (tileLow << 8);
						bitmapShiftTwo &= ~0xFF00;
						bitmapShiftTwo |= (tileHigh << 8);
						curAttr = nextAttr;
					}
					break;
				case 257:
					if(visibleScanline()) {
						equalizeHoriz();
					}
					break;
				case 280:
				case 281:
				case 282:
				case 283:
				case 284:
				case 285:
				case 286:
				case 287:
				case 288:
				case 289:
				case 290:
				case 291:
				case 292:
				case 293:
				case 294:
				case 295:
				case 296:
				case 297:
				case 298:
				case 299:
				case 300:
				case 301:
				case 302:
				case 303:
				case 304:
					if(scanline == 261) {
						equalizeVert();
					}
					break;
				case 338:
				case 340:
					if(visibleScanline()) {
						grabNT();
					}
					break;
		}
		
		cycles++;
		if(cycles > 340) {
			cycles = 0;
			scanline++;
			if(scanline > 261) {
				frameCount++;
				firstWrite = true;
				scanline = 0;
				mainFrame.renderFrame(bitmap);
				emulator.frameAlert(0);
				logNewFrame();
			}
		}
	}
	
	private void logNewFrame() {
		logger.log("Frame: " + frameCount, true);
		for(int i = 0; i < 4; i++) {
			logger.log("------------------------------------------------------- ", true);
		}
	}
	
	private boolean visibleScanline() {
		return (showBG | showSprites) & (scanline < 240 || scanline == 261);
	}

	public int getXScroll() {
		return xScroll;
	}

	public int getTempVAddress() {
		return tempVAddress;
	}

	public int getVAddress() {
		return vAddress;
	}
	
	
	private void grabNT() {
		int toRead = (0x2000 | (vAddress & 0xFFF));
		curNT = manager.read(toRead);
	}
	
	private void equalizeVert() {
		vAddress &= ~0xFBE0;
		vAddress |= (tempVAddress & 0xFBE0);
	}
	
	private void equalizeHoriz() {
		vAddress &= ~0x41f;
		vAddress |= (tempVAddress & 0x41f);
	}
	
	private void grabAT() {
		nextAttr = manager.read(0x23C0 | (vAddress & 0x0C00) | ((vAddress >> 4) & 0x38) | ((vAddress >> 2) & 0x07));
		int coarseX = (cycles - 1) / 8;
		int coarseY = (vAddress >> 5)  & 0b11111;
		int attrX = (coarseX % 4) / 2;
		int attrY = (coarseY % 4) / 2;
		int attrShift = (attrX * 2) + (attrY * 4);
		nextAttr = (nextAttr >> attrShift) & 3; 
		
	}
	
	private void grabTileLow() {
		tileLow = reverse(manager.read(bgPT + (curNT * 0x10) + (scanline % 8)));
	}
	
	private void grabTileHigh() {
		tileHigh = reverse(manager.read(bgPT + (curNT * 0x10) + (scanline % 8) + 8));
	}
	
	private int reverse(int toReverse) {
		int ret = 0;
		ret |= toReverse >> 7 & 1;
		ret |= (toReverse >> 6 & 1) << 1;
		ret |= (toReverse >> 5 & 1) << 2;
		ret |= (toReverse >> 4 & 1) << 3;
		ret |= (toReverse >> 3 & 1) << 4;
		ret |= (toReverse >> 2 & 1) << 5;
		ret |= (toReverse >> 1 & 1) << 6;
		ret |= (toReverse & 1) << 7;
		return ret;
	}
	
	private void shift() {
		bitmapShiftOne >>= 1;
		bitmapShiftTwo >>= 1;
	}
	
	private void incrementHoriz() {
		if((vAddress & 0x001F) == 31) {
			vAddress &= ~0x001F;
			vAddress ^= 0x0400;
		} else {
			vAddress += 1;
		}
	}
	
	private void incrementVert() {
		if((vAddress & 0x7000) != 0x7000) {
			vAddress += 0x1000;
		} else {
			vAddress &= ~0x7000;
			int y = (vAddress & 0x03E0) >> 5;
			if(y == 29) {
				y = 0;
				vAddress ^= 0x0800;
			} else if(y == 31) {
				y = 0;
			} else {
				y += 1;
			}
			vAddress = (vAddress & ~0x03E0) | (y << 5);
		}
	}
	
	private void renderBG(int offset) {
		final int pixel = ((grabBit(bitmapShiftTwo, xScroll) << 1) |
				grabBit(bitmapShiftOne, xScroll));
		int value = (curAttr << 2) | pixel;
		bitmap[offset] = manager.read(0x3F00 + value);
		logPixel(pixel, value);
		shift();
	}
	
	private void logPixel(int pixel, int value) {
		logger.log("(" + (cycles - 1) + "," + scanline + ")", false);
		logger.log(" | P: " + pixel, false);
		logger.log(" | Pal: " + (value >> 2), false);
		logger.log(" | NT: " + curNT, false);
		logger.log(" | AT: " + curAttr, true);
	}
	
	private int grabBit(int byteFromWhichToGrab, int bitToGrab) {
		return (byteFromWhichToGrab >> bitToGrab) & 1;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
	}

	public PPUMemoryManager getManager() {
		return manager;
	}

	public int getBGPt() {
		return bgPT;
	}

	public Logger getLogger() {
		return logger;
	}

	public int getCycles() {
		return cycles;
	}
	
	public int getScanline() {
		return scanline;
	}

}
