package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class CPY extends Instruction { 

    public CPY(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int operand = mode.read(cpu);
    	final int Y = cpu.getY();
    	int value = (Y - operand);
    	cpu.updateCarry(~(value >> 8 & 1));
    	cpu.updateSign(value >> 7 & 1);
    	cpu.updateZero(value == 0 ? 1 : 0);
        
    } 
}