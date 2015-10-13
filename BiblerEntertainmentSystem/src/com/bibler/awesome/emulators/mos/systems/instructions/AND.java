package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class AND extends Instruction { 

    public AND(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int accumulator = cpu.getAccumulator();
    	final int operand = mode.read(cpu);
    	final int value = (accumulator & operand);
    	cpu.setAccumulator(value);
    	cpu.updateSign((value >> 7) & 1);
    	cpu.updateZero(value == 0 ? 1 : 0);
        
    } 
}