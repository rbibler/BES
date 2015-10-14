package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502;
import com.bibler.awesome.emulators.mos.systems.addressingmode.Accumulator;
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class ASL extends Instruction { 

    public ASL(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	int operand = mode.read(cpu);
    	int carry = operand >> 7 & 1;
    	operand = operand << 1;
    	operand %= 256;
    	cpu.updateCarry(carry);
    	cpu.updateZero(operand == 0 ? 1 : 0);
    	cpu.updateSign(operand >> 7 & 1);
    	if(mode instanceof Accumulator) {
    		cpu.setAccumulator(operand);
    		return;
    	}
    	cpu.write(cpu.getAddress(), operand);
    	
    } 
    
}