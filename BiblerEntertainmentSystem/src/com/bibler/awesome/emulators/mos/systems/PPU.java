
package com.bibler.awesome.emulators.mos.systems;


import com.bibler.awesome.emulators.mos.ui.MainFrame;

public class PPU {

	public PPUMemoryManager manager = new PPUMemoryManager();
	private Emulator emulator;
	private MainFrame mainFrame;
    
	/*
	 * Scroll Registers
	 *  yyy NN YYYYY XXXXX
	 *	||| || ||||| +++++-- coarse X scroll
	 *	||| || +++++-------- coarse Y scroll
	 *	||| ++-------------- nametable select
	 *	+++----------------- fine Y scroll
	 */
	private int v;
	private int t;
	private int x;
	private int w;
	private int vramInc;
	private int spritePatternTable;
	private int bgPatternTable;
	private int spriteSize;
	private int ppuMasterSlave;
	private int vBuffer;
	private int OAMAddr;
	private int lastWrite;
	private int ppuStatus;
	
	private int bgShiftOne;
	private int bgShiftTwo;
	private int attrShiftOne;
	private int attrShiftTwo;
	private int ntLatch;
	private int attrLatchOne;
	private int attrLatchTwo;
	private int tileLowLatch;
	private int tileHighLatch;
	
	private int scanline;
	private int preRenderScanline = 261;
	private int scanlinesPerFrame = 262;
	private int frameCount;
	private int cycle;
	private int renderToggle;
	
	private boolean NMIOn;
	private boolean grayscale;
	private boolean bgCrop;
	private boolean spriteCrop;
	private boolean showBG;
	private boolean showSprites;
	private boolean emphasizeRed;
	private boolean emphasizeGreen;
	private boolean emphasizeBlue;

	
	public final static int SPRITE_OVERFLOW = 5;
	public final static int SPRITE_0_HIT = 6;
	public final static int V_BLANK = 7;
	
	private int[] bitmap = new int[256*240];
	
	
	public void setEmulator(Emulator emulator) {
		this.emulator = emulator;
	}
	
	public int read(int register) {
		int retValue = 0;
		switch(register) {
		case 2:	//PPUSTATUS
			w = 0;
			ppuStatus = (ppuStatus & ~(0b11111)) | lastWrite & 0b11111;
			retValue = ppuStatus;
			ppuStatus &= ~(1 << V_BLANK);
			break;
		case 4: // OAMDATA
			return manager.read(OAMAddr + 0x4000);
		case 7:	//PPUDATA
			if(v < 0x3F00) {
				retValue = vBuffer;
				vBuffer = manager.read(v);
			} else {
				retValue = manager.read(v);
				vBuffer = manager.read(v - 0x1000);
			}
			
			if(!renderingEnabled()) {
				incrementVram(vramInc);
			} else {
				coarseX();
				yIncrement();
				}
			break;
			default:
				retValue = vBuffer;
		}
		return retValue;
	}
	
	
	public void write(int register, int d) {
		d &= 0xFF;
		lastWrite = d;
		switch(register) {
		case 0:	//PPUCTRL
			t &= ~(0b11 << 10);
			t = (d & 0x03) << 10;
			vramInc = (d >> 2 & 1) == 0 ? 1 : 32;
			spritePatternTable = (d >> 3 & 1) == 0 ? 0 : 0x1000;
			bgPatternTable = (d >> 4 & 1) == 0 ? 0 : 0x1000;
			spriteSize = (d >> 5 & 1) == 0 ? 8 : 16;
			ppuMasterSlave = d >> 6 & 1;
			NMIOn = (d >> 7 & 1) == 0 ? false : true;
			break;
			
		case 1:	//PPUMASK
			grayscale = (d & 1) == 0 ? false : true;
			bgCrop = (d >> 1 & 1) == 0 ? true : false;
			spriteCrop = (d >> 2 & 1) == 0 ? true : false;
			showBG = (d >> 3 & 1) == 0 ? false : true;
			showSprites = (d >> 4 & 1) == 0 ? false : true;
			emphasizeRed = (d >> 5 & 1) == 0 ? false : true;
			emphasizeGreen = (d >> 6 & 1) == 0 ? false : true;
			emphasizeBlue = (d >> 7 & 1) == 0 ? false : true;
			break;
		case 3:
			OAMAddr = d;
			break;
			
		case 4:
			if(renderingEnabled() && currentlyRendering()) {
				return;
			} else {
				manager.write(OAMAddr + 0x4000, d);
				OAMAddr++;
			}
		case 5:	//PPUSCROLL
			if(w == 0) {
				t &= ~(0b11111);
				t |= (d >> 3) & 0b11111;
				x = d & 0b111;
				w = 1;
			} else {
				t &= ~(0b111001111100000);
				t |= (d & 0b111) << 12;
				t |= (d >> 6 & 0b11) << 8;
				t |= (d >> 3 & 0b111) << 5;
				w = 0;
			}
			break;
		case 6:	//PPUADDR
			if(w == 0) {
				t &= ~(0b1111100000000);
				t |= (d & 0b111111) << 8;
				t &= ~(1 << 14);
				w = 1;
			} else {
				t &= ~(0xFF);
				t |= (d & 0xFF);
				v = t;
				w = 0;
			}
			break;
		case 7:	//PPUDATA
			manager.write(v, d);
			incrementVram(vramInc);
		}
	}
	
	public void step() {
		
		if(renderingEnabled() && scanline < 240 && cycle > 0 && cycle < 257) {
			renderPixel();
		}
		if(scanline < 240 && renderingEnabled() && 
				((cycle > 0 && cycle <= 257) || (cycle >= 321 && cycle <= 336)))
			shift();
		if(scanline <= 239 && renderingEnabled()) {
			executeReads();
		} else if(scanline == 241) {
			if(cycle == 1) {
				updatePPUStatus(true, V_BLANK);
			}
		} else if(scanline == 261 && renderingEnabled()) {
			executeReads();
			if(cycle == 1) {
				updatePPUStatus(false, V_BLANK);
				updatePPUStatus(false, SPRITE_0_HIT);
				updatePPUStatus(false, SPRITE_OVERFLOW);
			}
			if(cycle >= 280 && cycle <= 304) {
				equalizeTAndVVert();
			}
		}
		
		cycle++;
		checkForNMI();
		checkForFrameEnd();
		
	}
	
	private void incrementVram(int inc) {
		v += inc;
	}
	
	private boolean renderingEnabled() {
		return showBG || showSprites;
	}
	
	private boolean currentlyRendering() {
		return scanline == preRenderScanline || scanline < 240;
	}
	
	private void coarseX() {
		if((v & 0x001F) == 31) {
			v &= ~0x001F;
			v ^= 0x0400;
		} else {
			v += 1;
		}
	}
	
	private void yIncrement() {
		if((v & 0x7000) != 0x7000) {
			v += 0x1000;
		} else {
			v &= ~0x7000;
			int y = (v & 0x03E0) >> 5;
			if(y == 29) {
				y = 0;
				v ^= 0x0800;
			} else if(y == 31) {
				y = 0;
			} else {
				y += 1;
			}
			v = (v & ~0x03E0) | ( y << 5);
		}
	}
	
	private void equalizeTAndVVert() {
		v = (v & 0x841F) | (t & 0x7BE0);
	}
	
	private void equalizeTAndVHoriz() {
		v &= ~0x41F;
        v |= t & 0x41F;
	}
	
	private void executeReads() {
		if(cycle == 338 || cycle == 340) {
			nextNTByte();
		}
		if(!((cycle > 0 && cycle <= 257) || (cycle >= 321 && cycle <= 336))) {
			return;
		}
		final int cycleTemp = (cycle - 1) % 8;
		switch(cycleTemp) {
		case 1:
			ntLatch = nextNTByte();
			break;
		case 3:
			nextAttrByte();
			break;
		case 5:
			tileLowLatch = nextTileLowByte();
			break;
		case 7:
			tileHighLatch = nextTileHighByte();
			shiftInNextTiles();
			if(cycle != 256) {
				coarseX();
			} else {
				yIncrement();
			}
			break;
		}
		if(cycle == 257) {
			equalizeTAndVHoriz();
		}
		
	}
	
	private int nextNTByte() {
		final int address = 0x2000 | (v & 0xFFF);
		return manager.read(address);
	}
	
	private void nextAttrByte() {
		final int attrAdd = 0x23C0 | (v & 0x0C00) | ((v >> 4) & 0x38) | ((v >> 2) & 0x07);
		attrShiftTwo = manager.read(attrAdd);
	}
	
	private int nextTileLowByte() {
		return manager.read(this.bgPatternTable + (ntLatch * 16) + (v >> 12 & 7));
	}
	
	private int nextTileHighByte() {
		return manager.read(this.bgPatternTable + (ntLatch * 16) + 8 + (v >> 12 & 7));
	}
	
	private void shiftInNextTiles() {
		if(scanline == 18)
		System.out.println("SHIFT");
		bgShiftOne &= ~0xFF;
		bgShiftTwo &= ~0xFF;
		bgShiftOne |= tileLowLatch & 0xFF;
		bgShiftTwo |= tileHighLatch & 0xFF;
		attrShiftOne = attrLatchOne;
		attrLatchOne = attrShiftTwo;
	}
	
	private void renderPixel() {
		final int x = cycle - 1;
		final int y = scanline;
		final int offset = y * 256 + x;
		final int pix1 = bgShiftOne >> 15 & 1;
		final int pix2 = (bgShiftTwo >> 15 & 1);
		final int bgPix = pix1 | pix2 << 1;
		int ntX = (x % 32 / 16);
		int ntY = (y % 32 / 16);
		int attrBitShift = (ntX * 2) + (ntY * 4);
		int palVal = ((attrShiftOne >> attrBitShift) & 3) << 2;
		if(scanline == 18)
			System.out.println("palVal: " + (attrShiftOne >> attrBitShift) + " X: " + x + " Attr: " + Integer.toHexString(attrShiftOne));
		final int pix = palVal + bgPix;
		bitmap[offset] = manager.read(0x3F00 | pix);
		
	}
	
	private void shift() {
		bgShiftOne <<= 1;
		bgShiftTwo <<= 1;
		//attrShiftOne <<= 1;
		//attrShiftTwo <<= 1;
		//attrShiftOne |= attrLatchOne & 1;
		//attrShiftTwo |= attrLatchTwo & 1;
	}
	
	public void updatePPUStatus(boolean status, int flag) {
		if(status) {
			ppuStatus |= 1 << flag;
		} else {
			ppuStatus &= ~(1 << flag);
		}
		
	}
	
	private void checkForNMI() {
		if(this.NMIOn && (ppuStatus >> V_BLANK & 1) == 1) {
			emulator.setNMI(true);
		} else {
			emulator.resetNMI();
		}
	}
	
	private void checkForFrameEnd() {
		if (cycle == 340) {
			cycle = 0;
            scanline = (scanline + 1) % scanlinesPerFrame;
            if (scanline == 0) {
                ++frameCount;
                mainFrame.renderFrame(bitmap);
            }
        }
	}
	
	public int getT() {
		return t;
	}
	
	public int getV() {
		return v;
	}
	
	public int getX() {
		return x;
	}
	
	public int getVramInc() {
		return vramInc;
	}
	
	public int getSpriteSize() {
		return spriteSize;
	}
	
	public int getSpritePatternTable() {
		return spritePatternTable;
	}
	
	public int getBGPatternTable() {
		return bgPatternTable;
	}
	
	public int getPPUMasterSlave() {
		return ppuMasterSlave;
	}
	
	public int getOAMAddr() {
		return OAMAddr;
	}
	
	public boolean getNMI() {
		return NMIOn;
	}
	
	public boolean grayscale() {
		return grayscale;
	}
	
	public boolean bgCrop() {
		return bgCrop;
	}
	
	public boolean spriteCrop() {
		return spriteCrop;
	}
	
	public boolean showBG() {
		return showBG;
	}
	
	public boolean showSprites() {
		return showSprites;
	}
	
	public boolean emphasizeRed() {
		return emphasizeRed;
	}
	
	public boolean emphasizeGreen() {
		return emphasizeGreen;
	}
	
	public boolean emphasizeBlue() {
		return emphasizeBlue;
	}
	
	public PPUMemoryManager getManager() {
		return manager;
	}
	
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void setMirroring(boolean horiz, boolean vert) {
		// TODO Auto-generated method stub
		
	}
}
