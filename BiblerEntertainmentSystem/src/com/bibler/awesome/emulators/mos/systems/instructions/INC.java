package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class INC extends Instruction { 

    public INC(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
        final int operand = mode.read(cpu, true);
        final int value = (operand + 1) % 256;
        cpu.write(cpu.getAddress(), value);
        cpu.updateZero(value == 0 ? 1 : 0);
        cpu.updateSign(value >> 7 & 1);
    } 
}