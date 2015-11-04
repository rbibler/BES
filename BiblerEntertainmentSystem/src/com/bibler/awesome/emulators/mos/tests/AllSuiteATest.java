package com.bibler.awesome.emulators.mos.tests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.bibler.awesome.emulators.mos.systems.CPU6502;
import com.bibler.awesome.emulators.mos.ui.MemoryView;

import junit.framework.TestCase;

public class AllSuiteATest extends TestCase {
	
	private CPU6502 cpu;
	
	public void testAllSuiteA() {
		cpu = CPU6502.getInstance(null);
		loadProgram();
		executeProgram();
	}
	
	private void loadProgram() {
		BufferedInputStream input = null;
		File f = new File("C:/users/ryan/downloads/AllSuiteA/AllSuiteATrim.bin");
		byte[] bytes = new byte[0x5C3];
		try {
			input = new BufferedInputStream(new FileInputStream(f));
			input.read(bytes);
		} catch(IOException e) {}
		finally {
			if(input != null) {
				try {
					input.close();
				} catch(IOException e) {}
			}
		}
		for(int i = 0; i < bytes.length; i++) {
			cpu.write(0x4000 + i, bytes[i] & 0xFF);
		}
		cpu.setPC(0x4000);
		setupMemoryTable();
	}
	
	private void setupMemoryTable() {
		MemoryView view = new MemoryView(0xFFFFFF);
		//MemoryViewController viewController = new MemoryViewController(view);
		//cpu.registerObserver(viewController);
		
	}
	
	private void executeProgram() {
		int opCode;
		int executedInstructions = 0;
		while(executedInstructions < 1000) {
			opCode = (cpu.fetch() & 0xFF);
			cpu.execute(opCode);
			System.out.println(" Acc: " + Integer.toHexString(cpu.getAccumulator()));
			executedInstructions++;
		}
		assertEquals(0xFF, cpu.read(0x210));
	}

}
