package com.bibler.awesome.emulators.mos.systems;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.bibler.awesome.emulators.mos.interfaces.Notifiable;
import com.bibler.awesome.emulators.mos.utils.OpcodeTables;
import com.bibler.awesome.emulators.mos.utils.StringUtils;

public class Emulator implements Runnable {
	
	private CPU cpu;
	private PPU ppu;
	private byte currentOpcode;
	
	private Object pauseLock = new Object();
	private boolean pause;
	private boolean running;
	private boolean NMINext;
	private boolean canNMI;
	private boolean drewFrame;
	private boolean frameByFrame;
	private Notifiable frame;
	private int state;
	private int initialPC;
	private int currentPC;
	private int cycles;
	private int frameCycles;
	private long frameWait;
	private final int FPS = 1000 / 30;
	private final long clock = 560;
	private long cycleDuration;
	private String[] opCodes;
	private FileWriter w;
	
	public final static int DEBUG = 0x01;
	public final static int RUN = 0x02;
	
	private ArrayList<Integer> breakPoints = new ArrayList<Integer>();
	
	
	public Emulator() {
		opCodes = opcodes();
		try {
            w = new FileWriter(new File("C:/users/ryan/desktop/nesdebugBES.txt"));
        } catch (IOException e) {
            System.err.println("Cannot create debug log" + e.getLocalizedMessage());
        }
	}
	
	public void setCPU(CPU cpu) {
		this.cpu = cpu;
		ppu = cpu.mem.ppu;
		ppu.setEmulator(this);
	}
	
	public void setPPU(PPU ppu) {
		this.ppu = ppu;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public void setNotifiable(Notifiable frame) {
		this.frame = frame;
	}
	
	public CPU getCPU() {
		return cpu;
	}
	
	public void pause() {
		synchronized(pauseLock) {
			pause = true;
		}
	}
	
	public void resume() {
		synchronized(pauseLock) {
			pause = false;
			pauseLock.notifyAll();
		}
	}
	
	public void nextCycle() {
		currentOpcode = (byte) cpu.fetch();
		String op = String.format(opCodes[currentOpcode & 0xFF],
                cpu.mem.read(cpu.PC),
                cpu.mem.read(cpu.PC + 1),
                cpu.PC + (byte) (cpu.mem.read(cpu.PC)) + 1);
        try {
        	w.write(StringUtils.formatNumber((cpu.PC - 1), 4) + 
        			" " + StringUtils.formatNumber(currentOpcode,  2) +
		            String.format(" %-14s ", op) +
		            " CYC:"  + (ppu.getCycles() - 1) + " SL:" + ppu.getScanline() + "\n");
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
        	try {
				w.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		
		cycles = cpu.execute(currentOpcode);
		frameCycles += cycles;
		int ppuCycles = cycles * 3;
		for(int i = 0; i < ppuCycles; i++) {
			ppu.step();
		}
		if(NMINext) {
			canNMI = false;
			cpu.NMI();
			cycles = 7;
			NMINext = false;
		}
	}
	
	public void startEmulation() {
		Thread t = new Thread(this);
		running = true;
		cpu.state = 1;
		initialPC = cpu.PC;
		state = RUN;
		ppu.setStartTime();
		pause();
		t.start();
		resume();
	}
	
	public void nextFrame() {
		if(state != RUN) {
			startEmulation();
		} else {
			if(pause) {
				resume();
			}
		}
		frameByFrame = true;
	}
	
	public void debug() {
		initialPC = cpu.PC;
		state = DEBUG;
		cpu.state = 1;
	}
	
	public void reset() {
		cpu.reset();
	}
	
	public void stepInto() {
		nextCycle();
		frame.onNotification(this);
	}
	
	public void stepOutOf() {
		resume();
	}
	
	public int getCycles() {
		return cycles;
	}
	
	public void setNMI(boolean NMINext) {
		if(canNMI)
			this.NMINext = NMINext;
	}
	
	public void resetNMI() {
		canNMI = true;
	}
	
	public void addBreakPoint(int pc) {
		if(breakPoints.contains(pc)) {
			return;
		}
		breakPoints.add(pc);
		System.out.println("Added: " + StringUtils.formatNumber(pc, 4));
	}
	
	public void frameAlert(long time) {
		if(frameByFrame) {
			System.out.println("Frame Drawn");
			frame.onNotification(this);
			pause();
		}
	}

	@Override
	public void run() {
		while(running) {
			if(pause) {
				synchronized(pauseLock) {
					try {
						pauseLock.wait();
					} catch(InterruptedException e) {}
				}
			}
			for(Integer bp : breakPoints) {
				if(bp == cpu.PC) {
					pause();
					continue;
				}
			}
			nextCycle();
		}
	}

	public PPU getPPU() {
		return ppu;
	}
	
	public static String[] opcodes() {
        //%1 1st byte, %2 2nd byte, %3 relative offset from PC
        //odd combination of format string and eventual syntax in file here.
        String[] op = new String[0x100];
        op[0x00] = "BRK";
        op[0x01] = "ORA $(%2$02X%1$02X,x)";
        op[0x02] = "KIL";
        op[0x03] = "SLO $(%2$02X%1$02X,x)";
        op[0x04] = "NOP $%1$02X";
        op[0x05] = "ORA $%1$02X";
        op[0x06] = "ASL $%1$02X";
        op[0x07] = "SLO $%1$02X";
        op[0x08] = "PHP";
        op[0x09] = "ORA #$%1$02X";
        op[0x0A] = "ASL A";
        op[0x0B] = "ANC #$%1$02X";
        op[0x0C] = "NOP $%2$02X%1$02X";
        op[0x0D] = "ORA $%2$02X%1$02X";
        op[0x0E] = "ASL $%2$02X%1$02X";
        op[0x0F] = "SLO $%2$02X%1$02X";
        op[0x10] = "BPL $%3$02X";
        op[0x11] = "ORA ($%1$02X), y";
        op[0x12] = "KIL";
        op[0x13] = "SLO ($%1$02X), y";
        op[0x14] = "NOP $%1$02X,x";
        op[0x15] = "ORA $%1$02X,x";
        op[0x16] = "ASL $%1$02X,x";
        op[0x17] = "SLO $%1$02X,x";
        op[0x18] = "CLC";
        op[0x19] = "ORA $%2$02X%1$02X,y";
        op[0x1A] = "NOP";
        op[0x1B] = "SLO $%2$02X%1$02X,y";
        op[0x1C] = "NOP $%2$02X%1$02X,x";
        op[0x1D] = "ORA $%2$02X%1$02X,x";
        op[0x1E] = "ASL $%2$02X%1$02X,x";
        op[0x1F] = "SLO $%2$02X%1$02X,x";
        op[0x20] = "JSR $%2$02X%1$02X";
        op[0x21] = "AND $(%2$02X%1$02X,x)";
        op[0x22] = "KIL";
        op[0x23] = "RLA $(%2$02X%1$02X,x)";
        op[0x24] = "BIT $%1$02X";
        op[0x25] = "AND $%1$02X";
        op[0x26] = "ROL $%1$02X";
        op[0x27] = "RLA $%1$02X";
        op[0x28] = "PLP";
        op[0x29] = "AND #$%1$02X";
        op[0x2A] = "ROL";
        op[0x2B] = "ANC #$%1$02X";
        op[0x2C] = "BIT $%2$02X%1$02X";
        op[0x2D] = "AND $%2$02X%1$02X";
        op[0x2E] = "ROL $%2$02X%1$02X";
        op[0x2F] = "RLA $%2$02X%1$02X";
        op[0x30] = "BMI $%3$02X";
        op[0x31] = "AND ($%1$02X), y";
        op[0x32] = "KIL";
        op[0x33] = "RLA ($%1$02X), y";
        op[0x34] = "NOP $%1$02X,x";
        op[0x35] = "AND $%1$02X,x";
        op[0x36] = "ROL $%1$02X,x";
        op[0x37] = "RLA $%1$02X,x";
        op[0x38] = "SEC";
        op[0x39] = "AND $%2$02X%1$02X,y";
        op[0x3A] = "NOP";
        op[0x3B] = "RLA $%2$02X%1$02X,y";
        op[0x3C] = "NOP $%2$02X%1$02X,x";
        op[0x3D] = "AND $%2$02X%1$02X,x";
        op[0x3E] = "ROL $%2$02X%1$02X,x";
        op[0x3F] = "RLA $%2$02X%1$02X,x";
        op[0x40] = "RTI";
        op[0x41] = "EOR $(%2$02X%1$02X,x)";
        op[0x42] = "KIL";
        op[0x43] = "SRE $(%2$02X%1$02X,x)";
        op[0x44] = "NOP $%1$02X";
        op[0x45] = "EOR $%1$02X";
        op[0x46] = "LSR $%1$02X";
        op[0x47] = "SRE $%1$02X";
        op[0x48] = "PHA";
        op[0x49] = "EOR #$%1$02X";
        op[0x4A] = "LSR";
        op[0x4B] = "ALR #$%1$02X";
        op[0x4C] = "JMP $%2$02X%1$02X";
        op[0x4D] = "EOR $%2$02X%1$02X";
        op[0x4E] = "LSR $%2$02X%1$02X";
        op[0x4F] = "SRE $%2$02X%1$02X";
        op[0x50] = "BVC $%3$02X";
        op[0x51] = "EOR ($%1$02X), y";
        op[0x52] = "KIL";
        op[0x53] = "SRE ($%1$02X), y";
        op[0x54] = "NOP $%1$02X,x";
        op[0x55] = "EOR $%1$02X,x";
        op[0x56] = "LSR $%1$02X,x";
        op[0x57] = "SRE $%1$02X,x";
        op[0x58] = "CLI";
        op[0x59] = "EOR $%2$02X%1$02X,y";
        op[0x5A] = "NOP";
        op[0x5B] = "SRE $%2$02X%1$02X,y";
        op[0x5C] = "NOP $%2$02X%1$02X,x";
        op[0x5D] = "EOR $%2$02X%1$02X,x";
        op[0x5E] = "LSR $%2$02X%1$02X,x";
        op[0x5F] = "SRE $%2$02X%1$02X,x";
        op[0x60] = "RTS";
        op[0x61] = "ADC $(%2$02X%1$02X,x)";
        op[0x62] = "KIL";
        op[0x63] = "RRA $(%2$02X%1$02X,x)";
        op[0x64] = "NOP $%1$02X";
        op[0x65] = "ADC $%1$02X";
        op[0x66] = "ROR $%1$02X";
        op[0x67] = "RRA $%1$02X";
        op[0x68] = "PLA";
        op[0x69] = "ADC #$%1$02X";
        op[0x6A] = "ROR";
        op[0x6B] = "ARR #$%1$02X";
        op[0x6C] = "JMP ($%2$02X%1$02X)";
        op[0x6D] = "ADC $%2$02X%1$02X";
        op[0x6E] = "ROR $%2$02X%1$02X";
        op[0x6F] = "RRA $%2$02X%1$02X";
        op[0x70] = "BVS $%3$02X";
        op[0x71] = "ADC ($%1$02X), y";
        op[0x72] = "KIL";
        op[0x73] = "RRA ($%1$02X), y";
        op[0x74] = "NOP $%1$02X,x";
        op[0x75] = "ADC $%1$02X,x";
        op[0x76] = "ROR $%1$02X,x";
        op[0x77] = "RRA $%1$02X,x";
        op[0x78] = "SEI";
        op[0x79] = "ADC $%2$02X%1$02X,y";
        op[0x7A] = "NOP";
        op[0x7B] = "RRA $%2$02X%1$02X,y";
        op[0x7C] = "NOP $%2$02X%1$02X,x";
        op[0x7D] = "ADC $%2$02X%1$02X,x";
        op[0x7E] = "ROR $%2$02X%1$02X,x";
        op[0x7F] = "RRA $%2$02X%1$02X,x";
        op[0x80] = "NOP #$%1$02X";
        op[0x81] = "STA $(%2$02X%1$02X,x)";
        op[0x82] = "NOP #$%1$02X";
        op[0x83] = "SAX $(%2$02X%1$02X,x)";
        op[0x84] = "STY $%1$02X";
        op[0x85] = "STA $%1$02X";
        op[0x86] = "STX $%1$02X";
        op[0x87] = "SAX $%1$02X";
        op[0x88] = "DEY";
        op[0x89] = "NOP #$%1$02X";
        op[0x8A] = "TXA";
        op[0x8B] = "XAA #$%1$02X";
        op[0x8C] = "STY $%2$02X%1$02X";
        op[0x8D] = "STA $%2$02X%1$02X";
        op[0x8E] = "STX $%2$02X%1$02X";
        op[0x8F] = "SAX $%2$02X%1$02X";
        op[0x90] = "BCC $%3$02X";
        op[0x91] = "STA ($%1$02X), y";
        op[0x92] = "KIL";
        op[0x93] = "AHX ($%1$02X), y";
        op[0x94] = "STY $%1$02X,x";
        op[0x95] = "STA $%1$02X,x";
        op[0x96] = "STX $%1$02X,y";
        op[0x97] = "SAX $%1$02X,y";
        op[0x98] = "TYA";
        op[0x99] = "STA $%2$02X%1$02X,y";
        op[0x9A] = "TXS";
        op[0x9B] = "TAS $%2$02X%1$02X,y";
        op[0x9C] = "SHY $%2$02X%1$02X,x";
        op[0x9D] = "STA $%2$02X%1$02X,x";
        op[0x9E] = "SHX $%2$02X%1$02X,y";
        op[0x9F] = "AHX $%2$02X%1$02X,y";
        op[0xA0] = "LDY #$%1$02X";
        op[0xA1] = "LDA $(%2$02X%1$02X,x)";
        op[0xA2] = "LDX #$%1$02X";
        op[0xA3] = "LAX $(%2$02X%1$02X,x)";
        op[0xA4] = "LDY $%1$02X";
        op[0xA5] = "LDA $%1$02X";
        op[0xA6] = "LDX $%1$02X";
        op[0xA7] = "LAX $%1$02X";
        op[0xA8] = "TAY";
        op[0xA9] = "LDA #$%1$02X";
        op[0xAA] = "TAX";
        op[0xAB] = "LAX #$%1$02X";
        op[0xAC] = "LDY $%2$02X%1$02X";
        op[0xAD] = "LDA $%2$02X%1$02X";
        op[0xAE] = "LDX $%2$02X%1$02X";
        op[0xAF] = "LAX $%2$02X%1$02X";
        op[0xB0] = "BCS $%3$02X";
        op[0xB1] = "LDA ($%1$02X), y";
        op[0xB2] = "KIL";
        op[0xB3] = "LAX ($%1$02X), y";
        op[0xB4] = "LDY $%1$02X,x";
        op[0xB5] = "LDA $%1$02X,x";
        op[0xB6] = "LDX $%1$02X,y";
        op[0xB7] = "LAX $%1$02X,y";
        op[0xB8] = "CLV";
        op[0xB9] = "LDA $%2$02X%1$02X,y";
        op[0xBA] = "TSX";
        op[0xBB] = "LAS $%2$02X%1$02X,y";
        op[0xBC] = "LDY $%2$02X%1$02X,x";
        op[0xBD] = "LDA $%2$02X%1$02X,x";
        op[0xBE] = "LDX $%2$02X%1$02X,y";
        op[0xBF] = "LAX $%2$02X%1$02X,y";
        op[0xC0] = "CPY #$%1$02X";
        op[0xC1] = "CMP $(%2$02X%1$02X,x)";
        op[0xC2] = "NOP #$%1$02X";
        op[0xC3] = "DCP $(%2$02X%1$02X,x)";
        op[0xC4] = "CPY $%1$02X";
        op[0xC5] = "CMP $%1$02X";
        op[0xC6] = "DEC $%1$02X";
        op[0xC7] = "DCP $%1$02X";
        op[0xC8] = "INY";
        op[0xC9] = "CMP #$%1$02X";
        op[0xCA] = "DEX";
        op[0xCB] = "AXS #$%1$02X";
        op[0xCC] = "CPY $%2$02X%1$02X";
        op[0xCD] = "CMP $%2$02X%1$02X";
        op[0xCE] = "DEC $%2$02X%1$02X";
        op[0xCF] = "DCP $%2$02X%1$02X";
        op[0xD0] = "BNE $%3$02X";
        op[0xD1] = "CMP ($%1$02X), y";
        op[0xD2] = "KIL";
        op[0xD3] = "DCP ($%1$02X), y";
        op[0xD4] = "NOP $%1$02X,x";
        op[0xD5] = "CMP $%1$02X,x";
        op[0xD6] = "DEC $%1$02X,x";
        op[0xD7] = "DCP $%1$02X,x";
        op[0xD8] = "CLD";
        op[0xD9] = "CMP $%2$02X%1$02X,y";
        op[0xDA] = "NOP";
        op[0xDC] = "NOP $%2$02X%1$02X,x";
        op[0xDD] = "CMP $%2$02X%1$02X,x";
        op[0xDE] = "DEC $%2$02X%1$02X,x";
        op[0xDF] = "DCP $%2$02X%1$02X,x";
        op[0xE0] = "CPX #$%1$02X";
        op[0xE1] = "SBC $(%2$02X%1$02X,x)";
        op[0xE2] = "NOP #$%1$02X";
        op[0xE3] = "ISC $(%2$02X%1$02X,x)";
        op[0xE4] = "CPX $%1$02X";
        op[0xE5] = "SBC $%1$02X";
        op[0xE6] = "INC $%1$02X";
        op[0xE7] = "ISC $%1$02X";
        op[0xE8] = "INX";
        op[0xE9] = "SBC #$%1$02X";
        op[0xEA] = "NOP";
        op[0xEB] = "SBC #$%1$02X";
        op[0xEC] = "CPX $%2$02X%1$02X";
        op[0xED] = "SBC $%2$02X%1$02X";
        op[0xEE] = "INC $%2$02X%1$02X";
        op[0xEF] = "ISC $%2$02X%1$02X";
        op[0xF0] = "BEQ $%3$02X";
        op[0xF1] = "SBC ($%1$02X), y";
        op[0xF2] = "KIL";
        op[0xF3] = "ISC ($%1$02X), y";
        op[0xF4] = "NOP $%1$02X,x";
        op[0xF5] = "SBC $%1$02X,x";
        op[0xF6] = "INC $%1$02X,x";
        op[0xF7] = "ISC $%1$02X,x";
        op[0xF8] = "SED";
        op[0xF9] = "SBC $%2$02X%1$02X,y";
        op[0xFA] = "NOP";
        op[0xFB] = "ISC $%2$02X%1$02X,y";
        op[0xFC] = "NOP $%2$02X%1$02X,x";
        op[0xFD] = "SBC $%2$02X%1$02X,x";
        op[0xFE] = "INC $%2$02X%1$02X,x";
        op[0xFF] = "ISC $%2$02X%1$02X,x";
        return op;
    }

}
