package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class ROR extends Instruction { 

    public ROR(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int operand = mode.read(cpu);
    	final int carry = cpu.getCarry();
    	cpu.updateCarry(operand & 1);
    	final int value = ((operand >> 1) | (carry << 7)) % 256;
    	cpu.updateZero(value == 0 ? 1 : 0);
    	cpu.updateSign(value >> 7 & 1);
    	cpu.write(cpu.getAddress(), value);
    } 
}