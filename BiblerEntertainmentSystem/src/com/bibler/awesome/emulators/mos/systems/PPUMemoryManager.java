package com.bibler.awesome.emulators.mos.systems;

public class PPUMemoryManager {
	
	Memory patternTable0;
	Memory patternTable1;
	Memory nameTable0;
	Memory attributeTable0;
	Memory nameTable1;
	Memory attributeTable1;
	Memory nameTable2;
	Memory attributeTable2;
	Memory nameTable3;
	Memory attributeTable3;
	Memory bgPalette;
	Memory spritePalette;
	Memory OAM;
	//public MemoryFrame frame;
	private boolean horiz;
	private boolean vert;
	
	private int totalSize;
	
	public PPUMemoryManager() {
		initializeMemory();
	}
	
	public void setMirroring(boolean horiz, boolean vert) {
		this.horiz = horiz;
		this.vert = vert;
	}
	
	private void initializeMemory() {
		patternTable0 = new Memory(0x1000);
		totalSize = 0x1000;
		patternTable1 = new Memory(0x1000);
		totalSize += 0x1000;
		nameTable0 = new Memory(0x3C0);
		totalSize += 0x3C0;
		attributeTable0 = new Memory(0x40);
		totalSize += 0x40;
		nameTable1 = new Memory(0x3C0);
		totalSize += 0x3C0;
		attributeTable1 = new Memory(0x40);
		totalSize += 0x40;
		nameTable2 = new Memory(0x3C0);
		totalSize += 0x3C0;
		attributeTable2 = new Memory(0x40);
		totalSize += 0x40;
		nameTable3 = new Memory(0x3C0);
		totalSize += 0x3C0;
		attributeTable3 = new Memory(0x40);
		totalSize += 0x40;
		bgPalette = new Memory(0x10);
		totalSize += 0x10;
		spritePalette  = new Memory(0x10);
		totalSize += 0x10;
		OAM = new Memory(0x100);
		totalSize += 0x100;
	}
	
	public void write(int address, int data) {
		if(address < 0x1000) {
			patternTable0.write(address, data);
		} else if(address < 0x2000) {
			patternTable1.write(address - 0x1000, data);
		} else if(address < 0x23C0) {
			nameTable0.write(address - 0x2000, data);
			if(horiz) {
				nameTable1.write(address - 0x2000, data);
			} else if(vert) {
				nameTable2.write(address - 0x2000,  data);
			}
		} else if(address < 0x2400) {
			attributeTable0.write(address - 0x23C0, data);
			if(horiz) {
				attributeTable1.write(address - 0x23C0,  data);
			} else if(vert) {
				attributeTable2.write(address- 0x23C0,  data);
			}
		} else if(address < 0x27C0) {
			nameTable1.write(address - 0x2400, data);
			if(vert) {
				nameTable3.write(address - 0x2400,  data);
			}
		} else if(address < 0x2800) {
			attributeTable1.write(address - 0x27C0, data);
			if(vert) {
				attributeTable3.write(address-0x27C0, data);
			}
		} else if(address < 0x2BC0) {
			nameTable2.write(address - 0x2800, data);
			if(horiz) {
				nameTable3.write(address-0x2800, data);
			}
		} else if(address < 0x2C00) {
			attributeTable2.write(address - 0x2BC0, data);
			if(horiz) {
				attributeTable3.write(address - 0x2BC0, data);
			}
		} else if(address < 0x2FC0) {
			nameTable3.write(address - 0x2C00, data);
		} else if(address < 0x3000) {
			attributeTable3.write(address - 0x2FC0, data);
		} else if(address >= 0x3F00 && address < 0x3F10) {
			bgPalette.write(address - 0x3F00, data);
		} else if(address >= 0x3F10 && address < 0x3F20) {
			spritePalette.write(address - 0x3F10, data);
		} else if(address >= 0x4000) {
			OAM.write(address - 0x4000, data);
		}
		//if(frame != null) {
		//frame.updateTable(address, data, true);
		//}
	}
	
	int[] readNextSprite(int index) {
		int[] bytes = new int[4];
		for(int i = 0; i < 4; i++) {
			bytes[i] = (OAM.read((index * 4) + i) & 0xFF);
		}
		return bytes;
	}
	
	public int read(int address) {
		if(address < 0x1000) {
			return patternTable0.read(address);
		} else if(address < 0x2000) {
			return patternTable1.read(address - 0x1000);
		} else if(address < 0x23C0) {
			return nameTable0.read(address - 0x2000);
		} else if(address < 0x2400) {
			return attributeTable0.read(address - 0x23C0);
		} else if(address < 0x27C0) {
			return nameTable1.read(address - 0x2400);
		} else if(address < 0x2800) {
			return attributeTable1.read(address - 0x27C0);
		} else if(address < 0x2BC0) {
			return nameTable2.read(address - 0x2800);
		} else if(address < 0x2C00) {
			return attributeTable2.read(address - 0x2BC0);
		} else if(address < 0x2FC0) {
			return nameTable3.read(address - 0x2C00);
		} else if(address < 0x3000) {
			return attributeTable3.read(address - 0x2FC0);
		} else if(address >= 0x3F00 && address < 0x3F10) {
			return bgPalette.read(address - 0x3F00);
		} else if(address >= 0x3F10 && address < 0x3F20) {
			return spritePalette.read(address - 0x3F10);
		}
		return -1;
	}
	
	public synchronized int[] consolidateMemory() {
		int[] ret = new int[0xFFFF];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = this.read(i);
		}
		return ret;
	}
	

}
