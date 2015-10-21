package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class INY extends Instruction { 

    public INY(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	 final int Y = cpu.getY();
         final int value = (Y + 1) % 256;
         cpu.setY(value);
         cpu.updateZero(value == 0 ? 1 : 0);
         cpu.updateSign(value >> 7 & 1);
    } 
}