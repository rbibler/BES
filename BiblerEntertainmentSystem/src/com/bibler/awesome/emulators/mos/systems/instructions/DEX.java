package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class DEX extends Instruction { 

    public DEX(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	int X = cpu.getX();
    	X = (X - 1) & 0xFF;
    	cpu.setX(X);
        cpu.updateZero(X == 0 ? 1 : 0);
        cpu.updateSign(X >> 7 & 1);
    } 
}