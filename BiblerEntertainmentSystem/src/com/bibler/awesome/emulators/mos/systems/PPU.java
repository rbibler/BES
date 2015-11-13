/*
 * HalfNES by Andrew Hoffman
 * Licensed under the GNU GPL Version 3. See LICENSE file
 */
package com.bibler.awesome.emulators.mos.systems;


import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_BGR;
import java.util.Arrays;

import com.bibler.awesome.emulators.mos.ui.MainFrame;

import static java.util.Arrays.fill;

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
	
	private int scanline;
	private int preRenderScanline = 261;
	
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
			return manager.read(OAMAddr);
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
	
	public void updatePPUStatus(boolean status, int flag) {
		if(status) {
			ppuStatus |= 1 << flag;
		} else {
			ppuStatus &= ~(1 << flag);
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
}
