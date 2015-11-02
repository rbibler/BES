package com.bibler.awesome.emulators.mos.systems;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.bibler.awesome.emulators.mos.interfaces.CPU;
import com.bibler.awesome.emulators.mos.systems.addressingmode.Absolute;
import com.bibler.awesome.emulators.mos.systems.addressingmode.AbsoluteX;
import com.bibler.awesome.emulators.mos.systems.addressingmode.AbsoluteY;
import com.bibler.awesome.emulators.mos.systems.addressingmode.Accumulator;
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode;
import com.bibler.awesome.emulators.mos.systems.addressingmode.Immediate;
import com.bibler.awesome.emulators.mos.systems.addressingmode.Implied;
import com.bibler.awesome.emulators.mos.systems.addressingmode.Indirect;
import com.bibler.awesome.emulators.mos.systems.addressingmode.IndirectX;
import com.bibler.awesome.emulators.mos.systems.addressingmode.IndirectY;
import com.bibler.awesome.emulators.mos.systems.addressingmode.Relative;
import com.bibler.awesome.emulators.mos.systems.addressingmode.ZeroPage;
import com.bibler.awesome.emulators.mos.systems.addressingmode.ZeroPageX;
import com.bibler.awesome.emulators.mos.systems.addressingmode.ZeroPageY;
import com.bibler.awesome.emulators.mos.systems.instructions.*;
import com.bibler.awesome.emulators.mos.utils.OpcodeTables;

public class CPU6502 extends Observable implements CPU {
	
	private volatile static CPU6502 uniqueInstance;
	private int accumulator;
	private int statusRegister;
	private int SP;
	
	private MemoryManager mem;
	//private Memory mem;
	private int PC = 01;
	
	private int X;
	private int Y;
	
	private PPU ppu;
	private int state;
	private boolean pageBoundaryFlag;
	private int address;
	private Instruction[] opCodeInstructions;
	private int[] opCodeLengths;
	private int[] opCodeCycles;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	public final static int ACCUMULATOR = -13;
	
	private CPU6502(PPU ppu) {
		mem = new MemoryManager();
		mem.resetAll();
		mem.ppu = ppu;
		this.ppu = ppu;
		setupArrays();
	}
	
	public static CPU6502 getInstance(PPU ppu) {
		if(uniqueInstance == null) {
			synchronized(CPU6502.class) {
				if(uniqueInstance == null) {
					uniqueInstance = new CPU6502(ppu);
				}
			}
		}
		return uniqueInstance;
	}
	
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}
	
	@Override
	public void powerOn() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int fetch() {
		return mem.read(PC++) & 0xFF;
	}
	
	@Override
	public int execute(int opCode) {
		opCode &= 0xFF;
		int cycles = opCodeCycles[opCode];
		opCodeInstructions[opCode].execute();
		//opCodeInstructions[opCode].printMnemonic();
		//System.out.print(" Carry: " + getCarry());
		if(pageBoundaryFlag) {
			pageBoundaryFlag = false;
			cycles++;
		}
		return cycles;
	}
	
	@Override
	public void reset() {
		PC = mem.read(0xFFFD) << 8 | mem.read(0xFFFC);
		SP -= 3;
		SP &= 0xFF;
	}

	public void setController(Controller controller) {
		mem.setController(controller);
	}
	
	private void notifyObservers(int address, int data) {
		for(Observer observer : observers) {
			observer.update(this, new int[] {address, data} );
		}
	}
	
	public PPU getPPU() {
		return mem.ppu;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int read(int address) {
		return mem.read(address);
	}
	
	public void write(int address, int data) {
		if(address == ACCUMULATOR) {
			setAccumulator(data);
			return;
		}
		notifyObservers(address, data);
		mem.write(address, data);
	}
	
	public void setPC(int PC) {
		this.PC = PC;
	}
	
	public int getPC() {
		return PC;
	}
	
	public void incrementPC() {
		PC++;
	}

	public int getAccumulator() {
		return accumulator;
	}
	

	public void setX(int X) {
		this.X = X & 0xFF;
	}

	public int getX() {
		return X;
	}
	
	public void setY(int Y) {
		this.Y = Y & 0xFF;
	}

	public int getY() {
		return Y;
	}
	
	public void setSP(int SP) {
		this.SP = SP & 0xFF;
	}
	
	public int getSP() {
		return SP;
	}
	
	public int stackPeek() {
		return read(0x100 + ((SP + 1) & 0xFF));
	}
	
	public void stackPush(int toPush) {
		write(SP + 0x100, toPush);
    	setSP(SP - 1 >= 0 ? SP - 1 : 0xFF);
	}
	
	public int stackPop() {
		final int ret = read(0x100 + ((SP + 1) & 0xFF));
    	setSP((SP + 1) & 0xFF);
    	return ret;
	}

	public void setPageBoundaryFlag() {
		pageBoundaryFlag = true;
	}

	public void setAddress(int address) {
		this.address = address;
	}
	
	public int getAddress() {
		return address;
	}

	public void setAccumulator(int accumulator) {
		this.accumulator = accumulator & 0xFF;
	}
	
	public int getCarry() {
		return statusRegister & 1;
	}
	
	public void updateCarry(int carry) {
		statusRegister &= ~(1);
		statusRegister |= carry;
	}
	
	public int getZero() {
		return statusRegister >> 1 & 1;
	}
	
	public void updateZero(int zero) {
		statusRegister &= ~(1 << 1);
		statusRegister |= zero << 1;
	}
	
	public int getOverflow() {
		return statusRegister >> 6 & 1;
	}
	
	public void updateOverflow(int overflow) {
		statusRegister &= ~(1 << 6);
		statusRegister |= overflow << 6;
	}
	
	public int getSign() {
		return statusRegister >> 7 & 1;
	}
	
	public void updateSign(int sign) {
		statusRegister &= ~(1 << 7);
		statusRegister |= sign << 7;
	}
	
	public int getInterrupt() {
		return statusRegister >> 2 & 1;
	}
	
	public void updateInterrupt(int interrupt) {
		statusRegister &= ~(1 << 2);
		statusRegister |= interrupt << 2;
	}
	
	public int getDecimal() {
		return statusRegister >> 3 & 1;
	}
	
	public void updateDecimal(int decimal) {
		statusRegister &= ~(1 << 3);
		statusRegister |= decimal << 3;
	}
	
	public void setStatusRegister(int statusRegister) {
		this.statusRegister = statusRegister;
	}
	
	public int getStatusRegister() {
		return statusRegister;
	}
	
	private void setupArrays() {
		opCodeInstructions = new Instruction[] {
			new BRK(this, new Implied(),"BRK"), new ORA(this, new IndirectX(),"ORA"), new KIL(this, new Implied(),"KIL"), new SLO(this, new IndirectX(),"SLO"), new NOP(this, new ZeroPage(),"NOP"), new ORA(this, new ZeroPage(),"ORA"), new ASL(this, new ZeroPage(),"ASL"), new SLO(this, new ZeroPage(),"SLO"), new PHP(this, new Implied(),"PHP"), new ORA(this, new Immediate(),"ORA"), new ASL(this, new Accumulator(),"ASL"), new ANC(this, new Immediate(),"ANC"), new NOP(this, new Absolute(),"NOP"), new ORA(this, new Absolute(),"ORA"), new ASL(this, new Absolute(),"ASL"), new SLO(this, new Absolute(),"SLO"), 
			new BPL(this, new Relative(),"BPL"), new ORA(this, new IndirectY(),"ORA"), new KIL(this, new Implied(),"KIL"), new SLO(this, new IndirectY(),"SLO"), new NOP(this, new ZeroPageX(),"NOP"), new ORA(this, new ZeroPageX(),"ORA"), new ASL(this, new ZeroPageX(),"ASL"), new SLO(this, new ZeroPageX(),"SLO"), new CLC(this, new Implied(),"CLC"), new ORA(this, new AbsoluteY(),"ORA"), new NOP(this, new Implied(),"NOP"), new SLO(this, new AbsoluteY(),"SLO"), new NOP(this, new AbsoluteX(),"NOP"), new ORA(this, new AbsoluteX(),"ORA"), new ASL(this, new AbsoluteX(),"ASL"), new SLO(this, new AbsoluteX(),"SLO"), 
			new JSR(this, new Absolute(),"JSR"), new AND(this, new IndirectX(),"AND"), new KIL(this, new Implied(),"KIL"), new RLA(this, new IndirectX(),"RLA"), new BIT(this, new ZeroPage(),"BIT"), new AND(this, new ZeroPage(),"AND"), new ROL(this, new ZeroPage(),"ROL"), new RLA(this, new ZeroPage(),"RLA"), new PLP(this, new Implied(), "PLP"), new AND(this, new Immediate(),"AND"), new ROL(this, new Accumulator(),"ROL"), new ANC(this, new Immediate(),"ANC"), new BIT(this, new Absolute(),"BIT"), new AND(this, new Absolute(),"AND"), new ROL(this, new Absolute(),"ROL"), new RLA(this, new Absolute(),"RLA"), 
			new BMI(this, new Relative(),"BMI"), new AND(this, new IndirectY(),"AND"), new KIL(this, new Implied(),"KIL"), new RLA(this, new IndirectY(),"RLA"), new NOP(this, new ZeroPageX(),"NOP"), new AND(this, new ZeroPageX(),"AND"), new ROL(this, new ZeroPageX(),"ROL"), new RLA(this, new ZeroPageX(),"RLA"), new SEC(this, new Accumulator(),"SEC"), new AND(this, new AbsoluteY(),"AND"), new NOP(this, new Implied(),"NOP"), new RLA(this, new AbsoluteY(),"RLA"), new NOP(this, new AbsoluteX(),"NOP"), new AND(this, new AbsoluteX(),"AND"), new ROL(this, new AbsoluteX(),"ROL"), new RLA(this, new AbsoluteX(),"RLA"), 
			new RTI(this, new Implied(),"RTI"), new EOR(this, new IndirectX(),"EOR"), new KIL(this, new Implied(),"KIL"), new SRE(this, new IndirectX(),"SRE"), new NOP(this, new ZeroPage(),"NOP"), new EOR(this, new ZeroPage(),"EOR"), new LSR(this, new ZeroPage(),"LSR"), new SRE(this, new ZeroPage(),"SRE"), new PHA(this, new Implied(),"PHA"), new EOR(this, new Immediate(),"EOR"), new LSR(this, new Accumulator(),"LSR"), new ALR(this, new Immediate(),"ALR"), new JMP(this, new Absolute(),"JMP"), new EOR(this, new Absolute(),"EOR"), new LSR(this, new Absolute(),"LSR"), new SRE(this, new Absolute(),"SRE"), 
			new BVC(this, new Relative(),"BVC"), new EOR(this, new IndirectY(),"EOR"), new KIL(this, new Implied(),"KIL"), new SRE(this, new IndirectY(),"SRE"), new NOP(this, new ZeroPageX(),"NOP"), new EOR(this, new ZeroPageX(),"EOR"), new LSR(this, new ZeroPageX(),"LSR"), new SRE(this, new ZeroPageX(),"SRE"), new CLI(this, new Implied(),"CLI"), new EOR(this, new AbsoluteY(),"EOR"), new NOP(this, new Implied(),"NOP"), new SRE(this, new AbsoluteY(),"SRE"), new NOP(this, new AbsoluteX(),"NOP"), new EOR(this, new AbsoluteX(),"EOR"), new LSR(this, new AbsoluteX(),"LSR"), new SRE(this, new AbsoluteX(),"SRE"), 
			new RTS(this, new Implied(),"RTS"), new ADC(this, new IndirectX(),"ADC"), new KIL(this, new Implied(),"KIL"), new RRA(this, new IndirectX(),"RRA"), new NOP(this, new ZeroPage(),"NOP"), new ADC(this, new ZeroPage(),"ADC"), new ROR(this, new ZeroPage(),"ROR"), new RRA(this, new ZeroPage(),"RRA"), new PLA(this, new Implied(),"PLA"), new ADC(this, new Immediate(),"ADC"), new ROR(this, new Accumulator(),"ROR"), new ARR(this, new Immediate(),"ARR"), new JMP(this, new Indirect(),"JMP"), new ADC(this, new Absolute(),"ADC"), new ROR(this, new Absolute(),"ROR"), new RRA(this, new Absolute(),"RRA"), 
			new BVS(this, new Relative(),"BVS"), new ADC(this, new IndirectY(),"ADC"), new KIL(this, new Implied(),"KIL"), new RRA(this, new IndirectY(),"RRA"), new NOP(this, new ZeroPageX(),"NOP"), new ADC(this, new ZeroPageX(),"ADC"), new ROR(this, new ZeroPageX(),"ROR"), new RRA(this, new ZeroPageX(),"RRA"), new SEI(this, new Implied(),"SEI"), new ADC(this, new AbsoluteY(),"ADC"), new NOP(this, new Implied(),"NOP"), new RRA(this, new AbsoluteY(),"RRA"), new NOP(this, new AbsoluteX(),"NOP"), new ADC(this, new AbsoluteX(),"ADC"), new ROR(this, new AbsoluteX(),"ROR"), new RRA(this, new AbsoluteX(),"RRA"), 
			new NOP(this, new Immediate(),"NOP"), new STA(this, new IndirectX(),"STA"), new NOP(this, new Immediate(),"NOP"), new SAX(this, new IndirectX(),"SAX"), new STY(this, new ZeroPage(),"STY"), new STA(this, new ZeroPage(),"STA"), new STX(this, new ZeroPage(),"STX"), new SAX(this, new ZeroPage(),"SAX"), new DEY(this, new Implied(),"DEY"), new NOP(this, new Immediate(),"NOP"), new TXA(this, new Implied(),"TXA"), new XAA(this, new Immediate(),"XAA"), new STY(this, new Absolute(),"STY"), new STA(this, new Absolute(),"STA"), new STX(this, new Absolute(),"STX"), new SAX(this, new Absolute(),"SAX"), 
			new BCC(this, new Relative(),"BCC"), new STA(this, new IndirectY(),"STA"), new KIL (this, new Implied(),"KIL"), new AHX(this, new IndirectY(),"AHX"), new STY(this, new ZeroPageX(),"STY"), new STA(this, new ZeroPageX(),"STA"), new STX(this, new ZeroPageY(),"STX"), new SAX(this, new ZeroPageY(),"SAX"), new TYA(this, new Implied(),"TYA"), new STA(this, new AbsoluteY(),"STA"), new TXS(this, new Implied(),"TXS"), new TAS(this, new AbsoluteY(),"TAS"), new SHY(this, new AbsoluteX(),"SHY"), new STA(this, new AbsoluteX(),"STA"), new SHX(this, new AbsoluteY(),"SHX"), new AHX(this, new AbsoluteY(),"AHX"), 
			new LDY(this, new Immediate(),"LDY"), new LDA(this, new IndirectX(),"LDA"), new LDX(this, new Immediate(),"LDX"), new LAX(this, new IndirectX(),"LAX"), new LDY(this, new ZeroPage(),"LDY"), new LDA(this, new ZeroPage(),"LDA"), new LDX(this, new ZeroPage(),"LDX"), new LAX(this, new ZeroPage(),"LAX"), new TAY(this, new Implied(),"TAY"), new LDA(this, new Immediate(),"LDA"), new TAX(this, new Implied(),"TAX"), new LAX(this, new Immediate(),"LAX"), new LDY(this, new Absolute(),"LDY"), new LDA(this, new Absolute(),"LDA"), new LDX(this, new Absolute(),"LDX"), new LAX(this, new Absolute(),"LAX"), 
			new BCS(this, new Relative(),"BCS"), new LDA(this, new IndirectY(),"LDA"), new KIL(this, new Implied(),"KIL"), new LAX(this, new IndirectY(),"LAX"), new LDY(this, new ZeroPageX(),"LDY"), new LDA(this, new ZeroPageX(),"LDA"), new LDX(this, new ZeroPageY(),"LDX"), new LAX(this, new ZeroPageY(),"LAX"), new CLV(this, new Implied(),"CLV"), new LDA(this, new AbsoluteY(),"LDA"), new TSX(this, new Implied(),"TSX"), new LAS(this, new AbsoluteY(),"LAS"), new LDY(this, new AbsoluteX(),"LDY"), new LDA(this, new AbsoluteX(),"LDA"), new LDX(this, new AbsoluteY(),"LDX"), new LAX(this, new AbsoluteY(),"LAX"), 
			new CPY(this, new Immediate(),"CPY"), new CMP(this, new IndirectX(),"CMP"), new NOP(this, new Immediate(),"NOP"), new DCP(this, new IndirectX(),"DCP"), new CPY(this, new ZeroPage(),"CPY"), new CMP(this, new ZeroPage(),"CMP"), new DEC(this, new ZeroPage(),"DEC"), new DCP(this, new ZeroPage(),"DCP"), new INY(this, new Implied(),"INY"), new CMP(this, new Immediate(),"CMP"), new DEX(this, new Implied(),"DEX"), new AXS(this, new Immediate(),"AXS"), new CPY(this, new Absolute(),"CPY"), new CMP(this, new Absolute(),"CMP"), new DEC(this, new Absolute(),"DEC"), new DCP(this, new Absolute(),"DCP"), 
			new BNE(this, new Relative(),"BNE"), new CMP(this, new IndirectY(),"CMP"), new KIL(this, new Implied(),"KIL"), new DCP(this, new IndirectY(),"DCP"), new NOP(this, new ZeroPageX(),"NOP"), new CMP(this, new ZeroPageX(),"CMP"), new DEC(this, new ZeroPageX(),"DEC"), new DCP(this, new ZeroPageX(),"DCP"), new CLD(this, new Implied(),"CLD"), new CMP(this, new AbsoluteY(),"CMP"), new NOP(this, new Implied(),"NOP"), new DCP(this, new AbsoluteY(),"DCP"), new NOP(this, new AbsoluteX(),"NOP"), new CMP(this, new AbsoluteX(),"CMP"), new DEC(this, new AbsoluteX(),"DEC"), new DCP(this, new AbsoluteX(),"DCP"), 
			new CPX(this, new Immediate(),"CPX"), new SBC(this, new IndirectX(),"SBC"), new NOP(this, new Immediate(),"NOP"), new ISC(this, new IndirectX(),"ISC"), new CPX(this, new ZeroPage(),"CPX"), new SBC(this, new ZeroPage(),"SBC"), new INC(this, new ZeroPage(),"INC"), new ISC(this, new ZeroPage(),"ISC"), new INX(this, new Implied(),"INX"), new SBC(this, new Immediate(),"SBC"), new NOP(this, new Implied(),"NOP"), new SBC(this, new Immediate(),"SBC"), new CPX(this, new Absolute(),"CPX"), new SBC(this, new Absolute(),"SBC"), new INC(this, new Absolute(),"INC"), new ISC(this, new Absolute(),"ISC"), 
			new BEQ(this, new Relative(),"BEQ"), new SBC(this, new IndirectY(),"SBC"), new KIL(this, new Implied(),"KIL"), new ISC(this, new IndirectY(),"ISC"), new NOP(this, new ZeroPageX(),"NOP"), new SBC(this, new ZeroPageX(),"SBC"), new INC(this, new ZeroPageX(),"INC"), new ISC(this, new ZeroPageX(),"ISC"), new SED(this, new Implied(),"SED"), new SBC(this, new AbsoluteY(),"SBC"), new NOP(this, new Implied(),"NOP"), new ISC(this, new AbsoluteY(),"ISC"), new NOP(this, new AbsoluteX(),"NOP"), new SBC(this, new AbsoluteX(),"SBC"), new INC(this, new AbsoluteX(),"INC"), new ISC(this, new AbsoluteX(),"ISC") 						
		};
		
		opCodeLengths = new int[] {
				0, 2, 0, 2, 2, 2, 2, 2, 1, 2, 1, 2, 3, 3, 3, 3,  
				2, 2, 0, 2, 2, 2, 2, 2, 1, 3, 1, 3, 3, 3, 3, 3,  
				3, 2, 0, 2, 2, 2, 2, 2, 1, 2, 1, 2, 3, 3, 3, 3,  
				2, 2, 0, 2, 2, 2, 2, 2, 1, 3, 1, 3, 3, 3, 3, 3,  
				1, 2, 0, 2, 2, 2, 2, 2, 1, 2, 1, 2, 3, 3, 3, 3,  
				2, 2, 0, 2, 2, 2, 2, 2, 1, 3, 1, 3, 3, 3, 3, 3,  
				1, 2, 0, 2, 2, 2, 2, 2, 1, 2, 1, 2, 3, 3, 3, 3,  
				2, 2, 0, 2, 2, 2, 2, 2, 1, 3, 1, 3, 3, 3, 3, 3,  
				2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 3, 3, 3, 3,  
				2, 2, 0, 2, 2, 2, 2, 2, 1, 3, 1, 1, 3, 3, 3, 3,  
				2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 3, 3, 3, 3,  
				2, 2, 0, 2, 2, 2, 2, 2, 1, 3, 1, 3, 3, 3, 3, 3,  
				2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 3, 3, 3, 3,  
				2, 2, 0, 2, 2, 2, 2, 2, 1, 3, 1, 3, 3, 3, 3, 3,  
				2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 3, 3, 3, 3,  
				2, 2, 0, 2, 2, 2, 2, 2, 1, 3, 1, 3, 3, 3, 3, 3  
			};
				
			opCodeCycles = new int[] {
				0, 6, 0, 8, 3, 3, 5, 5, 3, 2, 2, 2, 4, 4, 6, 6,  
				3, 5, 0, 8, 4, 4, 6, 6, 2, 4, 2, 7, 4, 4, 7, 7,  
				6, 6, 0, 8, 3, 3, 5, 5, 4, 2, 2, 2, 4, 4, 6, 6,  
				2, 5, 0, 8, 4, 4, 6, 6, 2, 4, 2, 7, 4, 4, 7, 7,  
				6, 6, 0, 8, 3, 3, 5, 5, 3, 2, 2, 2, 3, 4, 6, 6,  
				3, 5, 0, 8, 4, 4, 6, 6, 2, 4, 2, 7, 4, 4, 7, 7,  
				6, 6, 0, 8, 3, 3, 5, 5, 4, 2, 2, 2, 5, 4, 6, 6,  
				2, 5, 0, 8, 4, 4, 6, 6, 2, 4, 2, 7, 4, 4, 7, 7,  
				2, 6, 2, 6, 3, 3, 3, 3, 2, 2, 2, 2, 4, 4, 4, 4,  
				3, 6, 0, 6, 4, 4, 4, 4, 2, 5, 2, 5, 5, 5, 5, 5,  
				2, 6, 2, 6, 3, 3, 3, 3, 2, 2, 2, 2, 4, 4, 4, 4,  
				2, 5, 0, 5, 4, 4, 4, 4, 2, 4, 2, 4, 4, 4, 4, 4,  
				2, 6, 2, 8, 3, 3, 5, 5, 2, 2, 2, 2, 4, 4, 6, 6,  
				3, 5, 0, 8, 4, 4, 6, 6, 2, 4, 2, 7, 4, 4, 7, 7,  
				2, 6, 2, 8, 3, 3, 5, 5, 2, 2, 2, 2, 4, 4, 6, 6,  
				2, 5, 0, 8, 4, 4, 6, 6, 2, 4, 2, 7, 4, 4, 7, 7 
			};

	}

	public MemoryManager getMem() {
		return mem;
	}

	
	public void NMI() {
		PC -= 1;
		stackPush((PC >> 8) & 0xFF);
		stackPush(PC & 0xFF);
        stackPush(getStatusRegister());
        PC = mem.read(0xFFFA) + (mem.read(0xFFFB) << 8);
	}
}
