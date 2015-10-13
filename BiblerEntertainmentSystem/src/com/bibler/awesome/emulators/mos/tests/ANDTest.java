package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class ANDTest extends TestCase {
	
	private CPU6502 cpu;
	
	public void testANDImmediate() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x29);
		cpu.write(0x8001, 0x04);
		cpu.setAccumulator(0x44);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x04, cpu.getAccumulator());
		assertEquals(2, cycles);
	}
	
	public void testANDZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x25);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x44);
		cpu.setAccumulator(0x44);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(3, cycles);
	}
	
	public void testANDZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.setX(3);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x35);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0x44);
		cpu.setAccumulator(0x44);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testANDAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x2D);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0x44);
		cpu.setAccumulator(0x44);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testANDAbsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.setX(3);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x3D);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0x44);
		cpu.setAccumulator(0x44);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testANDAbsoluteXPageBoundary() {
		cpu = CPU6502.getInstance(null);
		cpu.setX(3);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x3D);
		cpu.write(0x8001, 0xFF);
		cpu.write(0x8002, 0x00);
		cpu.write(0x102, 0x44);
		cpu.setAccumulator(0x44);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(5, cycles);
	}
	
	public void testANDAbsoluteY() {
		cpu = CPU6502.getInstance(null);
		cpu.setY(3);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x39);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0x44);
		cpu.setAccumulator(0x44);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testANDAbsoluteYPageBoundary() {
		cpu = CPU6502.getInstance(null);
		cpu.setY(3);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x39);
		cpu.write(0x8001, 0xFF);
		cpu.write(0x8002, 0x00);
		cpu.write(0x102, 0x44);
		cpu.setAccumulator(0x44);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(5, cycles);
	}
	
	public void testANDIndirectX() {
		cpu = CPU6502.getInstance(null);
		cpu.setX(3);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x21);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0x00);
		cpu.write(0x48, 0x44);
		cpu.write(0x4400, 0x44);
		cpu.setAccumulator(0x44);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(6, cycles);
	}
	
	public void testANDIndirectY() {
		cpu = CPU6502.getInstance(null);
		cpu.setY(3);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x31);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x10);
		cpu.write(0x45, 0x44);
		cpu.write(0x4413, 0x44);
		cpu.setAccumulator(0x44);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(5, cycles);
	}
	
	public void testANDIndirectYPageBoundaryTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.setY(3);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x31);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0xFF);
		cpu.write(0x45, 0x00);
		cpu.write(0x102, 0x44);
		cpu.setAccumulator(0x44);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(6, cycles);
	}
	
	
	public void testANDZeroFlagTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x29);
		cpu.write(0x8001, 0x00);
		cpu.setAccumulator(0x00);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
	}
	
	public void testANDZeroFlagFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x29);
		cpu.write(0x8001, 0x01);
		cpu.setAccumulator(0x01);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getZero());
	}
	
	public void testANDSignFlagTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x29);
		cpu.write(0x8001, 0xFE);
		cpu.setAccumulator(0xFE);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getSign());
	}
	
	public void testANDSignFlagFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x29);
		cpu.write(0x8001, 0x00);
		cpu.setAccumulator(0xFE);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getSign());
	}
	

}
