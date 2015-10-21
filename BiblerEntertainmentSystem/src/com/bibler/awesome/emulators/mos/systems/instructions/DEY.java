package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class DEY extends Instruction { 

    public DEY(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	int Y = cpu.getY();
    	Y = (Y - 1) & 0xFF;
    	cpu.setY(Y);
        cpu.updateZero(Y == 0 ? 1 : 0);
        cpu.updateSign(Y >> 7 & 1);
    } 
}