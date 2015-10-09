package com.bibler.awesome.emulators.mos.systems;

public class Instruction {
	
	private int opCode;
	private int cycles;
	private int length;
	private String mnemonic;
	private int memoryType;
	
	public Instruction() {
		
	}
	
	public Instruction(int opCode, String mnemonic, int length, int cycles, int memoryType) {
		this.opCode = opCode;
		this.mnemonic = mnemonic;
		this.length = length;
		this.cycles = cycles;
		this.memoryType = memoryType;
	}
	
	public void setOpCode(int opCode) {
		this.opCode = opCode;
	}
	
	public int getOpCode() {
		return opCode;
	}
	
	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}
	
	public String getMnemonic() {
		return mnemonic;
	}
	
	public void setCycles(int cycles) {
		this.cycles = cycles;
	}
	
	public int getCycles() {
		return cycles;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setMemoryType(int memoryType) {
		this.memoryType = memoryType;
	}
	
	public int getMemoryType() {
		return memoryType;
	}

}
