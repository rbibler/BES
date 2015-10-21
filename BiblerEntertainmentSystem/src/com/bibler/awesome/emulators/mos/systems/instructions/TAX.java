package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class TAX extends Instruction { 

    public TAX(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
        final int accumulator = cpu.getAccumulator();
        cpu.setX(accumulator);
        cpu.updateZero(accumulator == 0 ? 1 : 0);
        cpu.updateSign(accumulator >> 7 & 1);
    } 
}