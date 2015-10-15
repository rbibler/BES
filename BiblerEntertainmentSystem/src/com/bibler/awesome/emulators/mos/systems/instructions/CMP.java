package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class CMP extends Instruction { 

    public CMP(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int operand = mode.read(cpu);
    	final int accumulator = cpu.getAccumulator();
    	int value = (accumulator - operand);
    	cpu.updateCarry(~(value >> 8 & 1));
    	cpu.updateSign(value >> 7 & 1);
    	cpu.updateZero(value == 0 ? 1 : 0);
    } 
}