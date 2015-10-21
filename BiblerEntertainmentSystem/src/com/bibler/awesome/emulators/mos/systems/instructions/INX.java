package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class INX extends Instruction { 

    public INX(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	  final int X = cpu.getX();
          final int value = (X + 1) % 256;
          cpu.setX(value);
          cpu.updateZero(value == 0 ? 1 : 0);
          cpu.updateSign(value >> 7 & 1);
    } 
}