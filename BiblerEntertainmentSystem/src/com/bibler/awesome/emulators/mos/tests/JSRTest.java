package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class JSRTest extends TestCase {
	
	/*
	 *  MODE           SYNTAX       HEX LEN TIM
		Absolute      JSR $5597     $20  3   6
	 */
	
	private CPU6502 cpu;
	
	public void testJSR() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000,  0x20);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x4400, cpu.getPC());
		assertEquals(0x02, cpu.stackPop());
		assertEquals(0x80, cpu.stackPop());
		
	}

}
