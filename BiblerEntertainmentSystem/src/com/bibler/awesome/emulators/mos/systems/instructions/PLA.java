package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class PLA extends Instruction { 

    public PLA(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int SP = cpu.getSP();
    	final int accumulator = cpu.read(0x100 + ((SP + 1) & 0xFF));
    	cpu.setAccumulator(accumulator);
    	cpu.setSP((SP + 1) & 0xFF); 
    } 
}