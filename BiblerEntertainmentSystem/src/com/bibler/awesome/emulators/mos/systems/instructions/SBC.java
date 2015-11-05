package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class SBC extends Instruction { 

    public SBC(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int operand = mode.read(cpu, true);
    	int carry = cpu.getCarry();
    	final int accumulator = cpu.getAccumulator();
    	//int value = accumulator + (0xFF - operand) + carry;
    	int value = accumulator - operand - (1 - carry);
    	carry = 1 - ((value >> 8) & 1);
    	int overflow = ((accumulator ^ value)&((0xFF-operand)^value)&0x80) == 0 ? 0 : 1;
    	value &= 0xFF;
    	cpu.setAccumulator(value);
    	cpu.updateCarry(carry);
    	cpu.updateZero(value == 0 ? 1 : 0);
    	cpu.updateOverflow(overflow);
    	cpu.updateSign(value >> 7);
    } 
    
    
}