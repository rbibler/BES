package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class JSR extends Instruction { 

    public JSR(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int operand = mode.read(cpu);
    	final int PC = (cpu.getPC() - 1);
    	cpu.stackPush(PC >> 8 & 0xFF);
    	cpu.stackPush(PC & 0xFF);
    	cpu.setPC(cpu.getAddress());
    } 
}