package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class DEC extends Instruction { 

    public DEC(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	int operand = mode.read(cpu, true);
    	operand = (operand - 1) & 0xFF;
    	cpu.write(cpu.getAddress(), operand);
        cpu.updateZero(operand == 0 ? 1 : 0);
        cpu.updateSign(operand >> 7 & 1);
        
    } 
}