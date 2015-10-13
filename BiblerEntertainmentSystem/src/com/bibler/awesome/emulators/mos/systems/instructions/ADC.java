package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class ADC extends Instruction { 

    public ADC(CPU6502 cpu, AddressingMode mode, String mnemonic) {
		super(cpu, mode, mnemonic);
	}

	@Override 
    public void execute() { 
    	final int operand = mode.read(cpu);
    	int carry = cpu.getCarry();
    	final int accumulator = cpu.getAccumulator();
    	int value = accumulator + carry + operand;
    	carry = (value >> 8) & 1;
    	int overflow = 0;
    	if(accumulator >> 7 == operand >> 7 && value >> 7 != operand >> 7) {
    		overflow = 1;
    	}
    	value %= 256;
    	cpu.setAccumulator(value);
    	cpu.updateCarry(carry);
    	cpu.updateZero(value == 0 ? 1 : 0);
    	cpu.updateOverflow(overflow);
    	cpu.updateSign(value >> 7);
    } 
}