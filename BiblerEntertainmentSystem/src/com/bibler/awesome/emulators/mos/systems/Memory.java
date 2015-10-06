package com.bibler.awesome.emulators.mos.systems;

import java.util.Arrays;

public class Memory {
	
	public int[] memory = new int[0xFFFF];
	
	public Memory() {
		
	}
	
	public Memory(int size) {
		memory = new int[size + 1];
	}
	
	public int read(int address) {
		if(address < 0 || address > memory.length) {
			return 0;
		}
		return memory[address];
	}
	
	public void write(int address, int byteToWrite) {
		if(address < 0 || address >= memory.length)
			return;
		memory[address] = byteToWrite;
	}
	
	public void reset() {
		Arrays.fill(memory, 0xFF);
	}

}
