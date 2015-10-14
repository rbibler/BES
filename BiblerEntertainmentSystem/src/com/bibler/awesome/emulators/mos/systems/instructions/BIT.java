package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class BIT extends Instruction { 

    public BIT(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	int operand = mode.read(cpu);
    	int accumulator = cpu.getAccumulator();
    	int value = (operand & accumulator) % 256;
    	cpu.updateZero(value == 0 ? 1 : 0);
    	cpu.updateSign(operand >> 7 & 1);
    	cpu.updateOverflow(operand >> 6 & 1);
        
    } 
}