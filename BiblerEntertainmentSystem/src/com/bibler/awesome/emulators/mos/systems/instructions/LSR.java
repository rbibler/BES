package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class LSR extends Instruction { 

    public LSR(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	int operand = mode.read(cpu, true);
    	int value = (operand >> 1) % 256;
    	cpu.updateCarry(operand & 1);
    	cpu.updateSign(value >> 7 & 1);
    	cpu.updateZero(value == 0 ? 1 : 0);
    	cpu.write(cpu.getAddress(), value);
        
    } 
}