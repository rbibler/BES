package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class STA extends Instruction { 

    public STA(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	mode.read(cpu);
    	final int accumulator = cpu.getAccumulator();
    	cpu.write(cpu.getAddress(), accumulator);
        
    } 
}