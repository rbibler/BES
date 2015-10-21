package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class TYA extends Instruction { 

    public TYA(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int Y = cpu.getY();
    	cpu.setAccumulator(Y);
    	cpu.updateZero(Y == 0 ? 1 : 0);
    	cpu.updateSign(Y >> 7 & 1);
    } 
}