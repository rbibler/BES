package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class TXA extends Instruction { 

    public TXA(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int X = cpu.getX();
    	cpu.setAccumulator(X);
    	cpu.updateZero(X == 0 ? 1 : 0);
    	cpu.updateSign(X >> 7 & 1);
        
    } 
}