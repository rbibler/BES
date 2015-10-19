package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class LDX extends Instruction { 

    public LDX(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int operand = mode.read(cpu);
    	cpu.setX(operand);
    	cpu.updateZero(operand == 0 ? 1 : 0);
    	cpu.updateSign(operand >> 7 & 1);
        
    } 
}