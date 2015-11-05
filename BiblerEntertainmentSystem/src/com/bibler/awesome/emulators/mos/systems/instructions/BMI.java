package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class BMI extends Instruction { 

    public BMI(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	int operand = mode.read(cpu, true);
    	if(cpu.getSign() == 1) {
    		cpu.setPC(operand);
    	}
    } 
}