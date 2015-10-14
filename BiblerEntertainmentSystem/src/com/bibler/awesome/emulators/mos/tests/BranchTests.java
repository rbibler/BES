package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class BranchTests extends TestCase {
	
	private CPU6502 cpu;
	
	public void testBPLTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.updateSign(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x10);
		cpu.write(0x8001, 0x79);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x807B, cpu.getPC());
	}
	
	public void testBPLFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.updateSign(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x10);
		cpu.write(0x8001, 0x02);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x8002, cpu.getPC());
	}
	
	public void testBPLTrueBackward() {
		cpu = CPU6502.getInstance(null);
		cpu.updateSign(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x10);
		cpu.write(0x8001, 0x80);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x7F82, cpu.getPC());
	}
	
	public void testBMITrue() {
		cpu = CPU6502.getInstance(null);
		cpu.updateSign(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x30);
		cpu.write(0x8001, 0x79);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x807B, cpu.getPC());
	}
	
	public void testBMIFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.updateSign(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x30);
		cpu.write(0x8001, 0x02);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x8002, cpu.getPC());
	}
	
	public void testBMITrueBackward() {
		cpu = CPU6502.getInstance(null);
		cpu.updateSign(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x30);
		cpu.write(0x8001, 0x80);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x7F82, cpu.getPC());
	}
	
	public void testBVCTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.updateOverflow(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x50);
		cpu.write(0x8001, 0x79);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x807B, cpu.getPC());
	}
	
	public void testBVCFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.updateOverflow(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x50);
		cpu.write(0x8001, 0x02);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x8002, cpu.getPC());
	}
	
	public void testBVCTrueBackward() {
		cpu = CPU6502.getInstance(null);
		cpu.updateOverflow(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x50);
		cpu.write(0x8001, 0x80);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x7F82, cpu.getPC());
	}
	
	public void testBVSTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.updateOverflow(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x70);
		cpu.write(0x8001, 0x79);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x807B, cpu.getPC());
	}
	
	public void testBVSFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.updateOverflow(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x70);
		cpu.write(0x8001, 0x02);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x8002, cpu.getPC());
	}
	
	public void testBVSTrueBackward() {
		cpu = CPU6502.getInstance(null);
		cpu.updateOverflow(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x70);
		cpu.write(0x8001, 0x80);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x7F82, cpu.getPC());
	}
	
	public void testBCCTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x90);
		cpu.write(0x8001, 0x79);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x807B, cpu.getPC());
	}
	
	public void testBCCFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x90);
		cpu.write(0x8001, 0x02);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x8002, cpu.getPC());
	}
	
	public void testBCCTrueBackward() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x90);
		cpu.write(0x8001, 0x80);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x7F82, cpu.getPC());
	}
	
	public void testBCSTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xB0);
		cpu.write(0x8001, 0x79);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x807B, cpu.getPC());
	}
	
	public void testBCSFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xB0);
		cpu.write(0x8001, 0x02);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x8002, cpu.getPC());
	}
	
	public void testBCSTrueBackward() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xB0);
		cpu.write(0x8001, 0x80);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x7F82, cpu.getPC());
	}
	
	public void testBNETrue() {
		cpu = CPU6502.getInstance(null);
		cpu.updateZero(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xD0);
		cpu.write(0x8001, 0x79);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x807B, cpu.getPC());
	}
	
	public void testBNEFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.updateZero(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xD0);
		cpu.write(0x8001, 0x02);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x8002, cpu.getPC());
	}
	
	public void testBNETrueBackward() {
		cpu = CPU6502.getInstance(null);
		cpu.updateZero(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xD0);
		cpu.write(0x8001, 0x80);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x7F82, cpu.getPC());
	}
	
	public void testBEQTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.updateZero(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xF0);
		cpu.write(0x8001, 0x79);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x807B, cpu.getPC());
	}
	
	public void testBEQFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.updateZero(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xF0);
		cpu.write(0x8001, 0x02);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x8002, cpu.getPC());
	}
	
	public void testBEQTrueBackward() {
		cpu = CPU6502.getInstance(null);
		cpu.updateZero(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xF0);
		cpu.write(0x8001, 0x80);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x7F82, cpu.getPC());
	}
	
	

}
