package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class RTI extends Instruction { 

    public RTI(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int sR = cpu.stackPop();
    	final int PCLow = cpu.stackPop();
    	final int PCHigh = cpu.stackPop();
    	cpu.setStatusRegister(sR);
    	cpu.setPC(PCHigh << 8 | PCLow);
        
    } 
}