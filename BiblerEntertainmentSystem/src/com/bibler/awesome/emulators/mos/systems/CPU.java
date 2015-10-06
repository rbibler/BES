package com.bibler.awesome.emulators.mos.systems;

public class CPU {
	
	public int accumulator;
	public byte carry;
	public byte negative;
	public byte zero;
	public byte interrupt;
	public byte overflow;
	public byte decimal;
	public byte B;
	public int SP;
	public int SPH = 0x100;
	
	public MemoryManager mem;
	public int PC = 01;
	
	public int X;
	public int Y;
	
	private int address;
	private int operand;
	public PPU ppu;
	public int state;
	
	public CPU(PPU ppu) {
		mem = new MemoryManager();
		mem.resetAll();
		mem.ppu = ppu;
		this.ppu = ppu;
	}
	
	public void setController(Controller controller) {
		mem.setController(controller);
	}
	
	public int execute(byte opCode) {
		int cycles = 0;
		switch(opCode) {
		case (byte) 0x00:
			cycles = 7;
			break;
		case (byte) 0x01:
			cycles = 6;
			memIndirectX();
			ORA();
			break;
		case (byte) 0x04:
			NOP();
			break;
		case (byte) 0x05:
			cycles = 3;
			memZP();
			ORA();
			break;
		case (byte) 0x06:
			cycles = 5;
			getAddressZP();
			operand = mem.read(address);
			ASL();
			mem.write(address, operand);
			break;
		case (byte) 0x08:
			cycles = 3;
			PHP();
			break;
		case (byte) 0x09:
			cycles = 2;
			memImmediate();
			ORA();
			break;
		case (byte) 0x0A:
			cycles = 2;
			operand = accumulator;
			ASL();
			accumulator = operand;
			break;
		case (byte) 0x0C:
			NOP();
			break;
		case(byte) 0x0D:
			cycles = 4;
			memAbsolute();
			ORA();
			break;
		case (byte) 0x0E:
			cycles = 6;
			getAddressAbsolute();
			operand = mem.read(address);
			ASL();
			mem.write(address, operand);
		case (byte) 0x10:
			cycles = 2;
			memImmediate();
			cycles += BPL();
			break;
		case (byte) 0x11:
			cycles = 5;
			cycles += memIndirectY();
			ORA();
			break;
		case (byte) 0x12:
			KIL();
			break;
		case (byte) 0x15:
			cycles = 4;
			memZPX();
			ORA();
			break;
		case (byte) 0x16:
			cycles = 6;
			getAddressZPX();
			operand = mem.read(address);
			ASL();
			mem.write(address, operand);
			break;
		case (byte) 0x18:
			cycles = 2;
			carry = 0;
			break;
		case (byte) 0x19:
			cycles = 4;
			cycles += memAbsoluteY();
			ORA();
			break;
		case (byte) 0x1D:
			cycles = 4;
			cycles += memAbsoluteX();
			ORA();
			break;
		case (byte) 0x1E:
			cycles = 7;
			getAddressAbsoluteX();
			operand = mem.read(address);
			ASL();
			mem.write(address, operand);
			break;
		case (byte) 0x20:
			cycles = 6;
			JSR();
			break;
		case (byte) 0x21:
			cycles = 6;
			memIndirectX();
			AND();
			break;
		case (byte) 0x24:
			cycles = 3;
			memZP();
			BIT();
			break;
		case (byte) 0x25:
			cycles = 3;
			memZP();
			AND();
			break;
		case (byte) 0x26:
			cycles = 5;
			getAddressZP();
			operand = mem.read(address);
			ROL();
			mem.write(address, operand);
			break;
		case (byte) 0x28:
			cycles = 4;
			PLP();
			break;
		case (byte) 0x29:
			cycles = 2;
			memImmediate();
			AND();
			break;
		case (byte) 0x2A:
			cycles = 2;
			operand = accumulator;
			ROL();
			accumulator = operand;
			break;
		case (byte) 0x2C:
			cycles = 4;
			memAbsolute();
			BIT();
			break;
		case (byte) 0x2D:
			cycles = 4;
			memAbsolute();
			AND();
			break;
		case (byte) 0x2E:
			cycles = 6;
			getAddressAbsolute();
			operand = mem.read(address);
			ROL();
			mem.write(address, operand);
			break;
		case (byte) 0x30:
			cycles = 2;
			memImmediate();
			cycles += BMI();
			break;
		case (byte) 0x31:
			cycles = 5;
			cycles += memIndirectY();
			AND();
			break;
		case (byte) 0x35:
			cycles = 4;
			memZPX();
			AND();
			break;
		case (byte) 0x36:
			cycles = 6;
			getAddressZPX();
			operand = mem.read(address);
			ROL();
			mem.write(address, operand);
			break;
		case (byte) 0x38:
			cycles = 2;
			carry = 1;
			break;
		case (byte) 0x39:
			cycles = 4;
			cycles += memAbsoluteY();
			AND();
			break;
		case (byte) 0x3D:
			cycles = 4;
			cycles += memAbsoluteX();
			AND();
			break;
		case (byte) 0x3E:
			cycles = 7;
			getAddressAbsoluteX();
			operand = mem.read(address);
			ROL();
			mem.write(address, operand);
			break;
		case (byte) 0x40:
			cycles = 6;
			//System.out.println("RTI");
			RTI();
			break;
		case (byte) 0x41:
			cycles = 6;
			memIndirectX();
			EOR();
			break;
		case (byte) 0x45:
			cycles = 3;
			memZP();
			EOR();
			break;
		case (byte) 0x46:
			cycles = 5;
			getAddressZP();
			operand = mem.read(address);
			LSR();
			mem.write(address, operand);
			break;
		case (byte) 0x48:
			cycles = 3;
			PHA();
			break;
		case (byte) 0x49:
			cycles = 2;
			memImmediate();
			EOR();
			break;
		case (byte) 0x4A:
			cycles = 2;
			operand = accumulator;
			LSR();
			accumulator = operand;
			break;
		case (byte) 0x4C:
			cycles = 3;
			getAddressAbsolute();
			JMP();
			break;
		case (byte) 0x4D:
			cycles = 4;
			memAbsolute();
			EOR();
			break;
		case (byte) 0x4E:
			cycles = 6;
			getAddressAbsolute();
			operand = mem.read(address);
			LSR();
			mem.write(address, operand);
			break;
		case (byte) 0x50:
			cycles = 2;
			memImmediate();
			cycles += BVC();
			break;
		case (byte) 0x51:
			cycles = 5;
			cycles += memIndirectY();
			EOR();
			break;
		case (byte) 0x55:
			cycles = 4;
			memZPX();
			EOR();
			break;
		case (byte) 0x56:
			cycles = 6;
			getAddressZPX();
			operand = mem.read(address);
			LSR();
			mem.write(address, operand);
			break;
		case (byte) 0x58:
			cycles = 2;
			interrupt = 0;
			break;	
		case (byte) 0x59:
			cycles = 4;
			cycles += memAbsoluteY();
			EOR();
			break;
		case (byte) 0x5D:
			cycles = 4;
			cycles += memAbsoluteX();
			EOR();
			break;
		case (byte) 0x5E:
			cycles = 7;
			getAddressAbsoluteX();
			operand = mem.read(address);
			LSR();
			mem.write(address, operand);
			break;
		case (byte) 0x60:
			cycles = 6;
			RTS();
			break;
		case (byte) 0x61:
			memIndirectX();
			adc();
			cycles = 6;
			break;
		case (byte) 0x65:
			memZP();
			adc();
			cycles = 3;
			break;
		case (byte) 0x66:
			cycles = 5;
			getAddressZP();
			operand = mem.read(address);
			ROR();
			mem.write(address, operand);
			break;
		case (byte) 0x68:
			cycles = 4;
			PLA();
			break;
		case (byte) 0x69: 
			memImmediate(); 
			adc(); 
			cycles = 2;
			break;
		case (byte) 0x6A:
			cycles = 2;
			operand = accumulator;
			ROR();
			accumulator = operand;
			break;
		case (byte) 0x6C:
			cycles = 5;
			getAddressIndirect();
			JMP();
			break;		
		case (byte) 0x6D:
		    memAbsolute();
		    adc();
		    cycles = 4;
		    break;
		case (byte) 0x6E:
			cycles = 6;
			getAddressAbsolute();
			operand = mem.read(address);
			ROR();
			mem.write(address,  operand);
			break;
		case (byte) 0x70:
			cycles = 2;
			memImmediate();
			cycles += BVS();
			break;
		case (byte) 0x71:
			cycles = 5;
			cycles += memIndirectY();
			adc();
			break;
		case (byte) 0x75:
			memZPX();
			adc();
			cycles = 4;
			break;
		case (byte) 0x76:
			cycles = 6;
			getAddressZPX();
			operand = mem.read(address);
			ROR();
			mem.write(address, operand);
			break;
		case (byte) 0x78:
			cycles = 2;
			interrupt = 1;
			break;
		case (byte) 0x79:
			cycles = 4;
			cycles += memAbsoluteY();
			adc();
			break;
		case (byte) 0x7D:
			cycles = 4;
			cycles += memAbsoluteX();
			adc();
			break;
		case (byte) 0x7E:
			cycles = 7;
			getAddressAbsoluteX();
			operand = mem.read(address);
			ROR();
			mem.write(address,  operand);
			break;
		case (byte) 0x81:
			cycles = 6;
			getAddressIndirectX();
			STA();
			break;
		case (byte) 0x84:
			cycles = 3;
			getAddressZP();
			STY();
			break;
		case (byte) 0x85:
			cycles = 3;
			getAddressZP();
			STA();
			break;
		case (byte) 0x86:
			cycles = 3;
			getAddressZP();
			STX();
			break;
		case (byte) 0x88:
			cycles = 2;
			DEY();
			break;
		case (byte) 0x8A:
			cycles = 2;
			TXA();
			break;
		case (byte) 0x8C:
			cycles = 4;
			getAddressAbsolute();
			STY();
			break;
		case (byte) 0x8D:
			cycles = 4;
			getAddressAbsolute();
		    STA();
		    break;
		case (byte) 0x8E:
			cycles = 4;
			getAddressAbsolute();
			STX();
			break;
		case (byte) 0x90:
			cycles = 2;
			memImmediate();
			cycles += BCC();
			break;
		case (byte) 0x91:
			cycles = 6;
			getAddressIndirectY();
			STA();
			break;
		case (byte) 0x94:
			cycles = 4;
			getAddressZPX();
			STY();
			break;
		case (byte) 0x95:
			cycles = 4;
			getAddressZPX();
		    STA();
		    break;
		case (byte) 0x96:
			cycles = 4;
			getAddressZPY();
			STX();
			break;
		case (byte) 0x98:
			cycles = 2;
			TYA();
			break;
		case (byte) 0x99:
			cycles = 5;
			getAddressAbsoluteY();
			STA();
			break;
		case (byte) 0x9A:
			cycles = 2;
			TXS();
			break;
		case (byte) 0x9D:
			cycles = 5;
			getAddressAbsoluteX();
			STA();
			break;
		case (byte) 0xA0:
			cycles = 2;
			memImmediate();
			LDY();
			break;
		case (byte) 0xA1:
			cycles = 6;
			memIndirectX();
			LDA();
			break;
		case (byte) 0xA2:
			cycles = 2;
			memImmediate();
			LDX();
			break;
		case (byte) 0xA4:
			cycles = 3;
			memZP();
			LDY();
			break;
		case (byte) 0xA5:
			cycles = 3;
			memZP();
			LDA();
			break;
		case (byte) 0xA6:
			cycles = 3;
			memZP();
			LDX();
			break;
		case (byte) 0xA8:
			cycles = 2;
			TAY();
			break;
		case (byte) 0xA9:
			cycles = 2;
			memImmediate();
			LDA();
			break;
		case (byte) 0xAA:
			cycles = 2;
			TAX();
			break;
		case (byte) 0xAC:
			cycles = 4;
			memAbsolute();
			LDY();
			break;
		case (byte) 0xAD:
			cycles = 4;
			memAbsolute();
			LDA();
			break;
		case (byte) 0xAE:
			cycles = 4;
			memAbsolute();
			LDX();
			break;
		case (byte) 0xB0:
			cycles = 2;
			memImmediate();
			cycles += BCS();
			break;
		case (byte) 0xB1:
			cycles = 5;
			cycles += memIndirectY();
			LDA();
			break;
		case (byte) 0xB4:
			cycles = 4;
			memZPX();
			LDY();
			break;
		case (byte) 0xB5:
			cycles = 4;
			memZPX();
			LDA();
			break;
		case (byte) 0xB6:
			cycles = 4;
			memZPY();
			LDX();
			break;
		case (byte) 0xB8:
			cycles = 2;
			overflow = 0;
			break;
		case (byte) 0xB9:
			cycles = 4;
			cycles += memAbsoluteY();
			LDA();
			break;
		case (byte) 0xBA:
			cycles = 2;
			TSX();
			break;
		case (byte) 0xBC:
			cycles = 4;
			cycles += memAbsoluteX();
			LDY();
			break;
		case (byte) 0xBD:
			cycles = 4;
			cycles += memAbsoluteX();
			LDA();
			break;
		case (byte) 0xBE:
			cycles = 4;
			cycles += memAbsoluteY();
			LDX();
			break;
		case (byte) 0xC0:
			cycles = 2;
			memImmediate();
			CPY();
			break;
		case (byte) 0xC1:
			cycles = 6;
			memIndirectX();
			CMP();
			break;
		case (byte) 0xC4:
			cycles = 3;
			memZP();
			CPY();
			break;
		case (byte) 0xC5:
			cycles = 3;
			memZP();
			CMP();
			break;
		case (byte) 0xC6:
			cycles = 5;
			getAddressZP();
			DEC();
			break;
		
		case (byte) 0xC8:
			cycles = 2;
			INY();
			break;
		case (byte) 0xC9:
			cycles = 2;
			memImmediate();
			CMP();
			break;
		case (byte) 0xCA:
			cycles = 2;
			DEX();
			break;
		case (byte) 0xCC:
			cycles = 4;
			memAbsolute();
			CPY();
			break;
		case (byte) 0xCD:
			cycles = 4;
			memAbsolute();
			CMP();
			break;
		case (byte) 0xCE:
			cycles = 6;
			getAddressAbsolute();
			DEC();
			break;
		case (byte) 0xD0:
			cycles = 2;
			memImmediate();
			cycles += BNE();
			break;
		case (byte) 0xD1:
			cycles = 5;
			cycles += memIndirectY();
			CMP();
			break;
		case (byte) 0xD5:
			cycles = 4;
			memZPX();
			CMP();
			break;
		case (byte) 0xD6:
			cycles = 6;
			getAddressZPX();
			DEC();
			break;
		case (byte) 0xD8:
			cycles = 2;
			decimal = 0;
			break;
		case (byte) 0xD9:
			cycles = 4;
			cycles += memAbsoluteY();
			CMP();
			break;
		case (byte) 0xDD:
			cycles = 4;
			cycles += memAbsoluteX();
			CMP();
			break;
		case (byte) 0xDE:
			cycles = 7;
			getAddressAbsoluteX();
			DEC();
			break;
		case (byte) 0xE0:
			cycles = 2;
			memImmediate();
			CPX();
			break;
		case (byte) 0xE1:
			cycles = 6;
			memIndirectX();
			SBC();
			break;
		case (byte) 0xE4:
			cycles = 3;
			memZP();
			CPX();
			break;
		case (byte) 0xE5:
			cycles = 3;
			memZP();
			SBC();
			break;
		case (byte) 0xE6:
			cycles = 5;
			getAddressZP();
			INC();
			break;
		case (byte) 0xE8:
			cycles = 2;
			INX();
			break;
		case (byte) 0xE9:
			cycles = 2;
			memImmediate();
			SBC();
			break;
		case (byte) 0xEC:
			cycles = 4;
			memAbsolute();
			CPX();
			break;
		case (byte) 0xED:
			cycles = 4;
			memAbsolute();
			SBC();
			break;
		case (byte) 0xEE:
			cycles = 6;
			getAddressAbsolute();
			INC();
			break;
		case (byte) 0xF0:
			cycles = 2;
			memImmediate();
			cycles += BEQ();
			break;
		case (byte) 0xF1:
			cycles = 5;
			cycles += memIndirectY();
			SBC();
			break;
		case (byte) 0xF5:
			cycles = 4;
			memZPX();
			SBC();
			break;
		case (byte) 0xF6:
			cycles = 6;
			getAddressZPX();
			INC();
			break;	
		case (byte) 0xF8:
			cycles = 2;
			decimal = 1;
			break;
		case (byte) 0xF9:
			cycles = 4;
			cycles += memAbsoluteY();
			SBC();
			break;
		case (byte) 0xFD:
			cycles = 4;
			cycles += memAbsoluteX();
			SBC();
			break;
		case (byte) 0xFE:
			cycles = 7;
			getAddressAbsoluteX();
			INC();
			break;
			
		}
		return cycles;
	}
	

	public int fetch() {
		return mem.read(PC++);
	}
	
	private void memImmediate() {
		operand = mem.read(PC++);
	}
	
	private void memZP() {
		operand = mem.read(mem.read(PC++));
	}
	
	private void getAddressZP() {
		address = mem.read(PC++);
	}
	
	private void memZPX() {
		operand = mem.read((mem.read(PC++) + X) & 0xFF);
	}
	
	private void getAddressZPX() {
		address = mem.read(PC++) + X;
	}
	
	private void memZPY() {
		operand = mem.read((mem.read(PC++) + Y) & 0xFF);
	}
	
	private void getAddressZPY() {
		address = mem.read(PC++) + Y;
	}
	
	private void memAbsolute() {
		operand = mem.read(mem.read(PC++) | (mem.read(PC++) << 8));
	}
	
	private void getAddressAbsolute() {
		address = mem.read(PC++) | (mem.read(PC++) << 8);
	}
	
	private int memAbsoluteX() {
		operand = mem.read( (mem.read(PC++) | (mem.read(PC++) << 8)) + X);
		if( ((operand - X) >> 8) != (operand >> 8))
			return 1;
		return 0;
	}
	
	private void getAddressAbsoluteX() {
		address = (mem.read(PC++) | (mem.read(PC++) << 8)) + X;
	}
	
	private int memAbsoluteY() {
		operand = mem.read( (mem.read(PC++) | (mem.read(PC++) << 8)) + Y);
		if(operand - Y >> 8 != operand >> 8)
			return 1;
		return 0;
	}
	
	private void getAddressAbsoluteY() {
		address = (mem.read(PC++) | (mem.read(PC++) << 8)) + Y;
	}
	
	private void memIndirectX() {
		int add = mem.read((mem.read(PC++) + X) & 0xFF);
		operand = mem.read(mem.read(add) | mem.read(add + 1) << 8);
	}
	
	private void getAddressIndirect() {
		int add = mem.read(PC++);
		address = mem.read(add) | mem.read(add + 1) << 8;
	}
	
	private void getAddressIndirectX() {
		int add = (mem.read(PC++) + X) & 0xFF;
		address = mem.read(add) | mem.read(add + 1) << 8;
	}
	
	private int memIndirectY() {
		int add = mem.read(PC++);
		operand = mem.read((mem.read(add) | mem.read(add + 1) << 8) + Y);
		if(add >> 8 != operand >> 8)
			return 1;
		return 0;
	}
	
	private void getAddressIndirectY() {
		int add = mem.read(PC++);
		address = mem.read(add) | mem.read(add + 1) << 8;
		address += Y;
	}
	
	private void adc() {
		int value = accumulator + (operand + carry);
		negative = (byte) ((value >> 7) & 0x01);
		zero = (byte) (value == 0 ? 1 : 0);
		carry = (byte) (value > 255 ? 1 : 0);
		overflow = (byte) ((!(((accumulator ^ operand) & 0x80) != 0) && (((accumulator ^ value) & 0x80)) != 0) ? 1 : 0);
		accumulator = (value & 0xFF);
	}
	private void LDA() {
		accumulator = operand;
		zero = (byte) (accumulator == 0 ? 1 : 0);
		negative = (byte) ((accumulator >> 7) & 0x01);
		accumulator = (accumulator & 0xFF);
	}
	
	private void LDX() {
		X = operand;
		zero = (byte) (X == 0 ? 1 : 0);
		negative = (byte) ((X >> 7) & 0x01);
		X = (byte) (X & 0xFF);
	}
	
	private void LDY() {
		Y = operand;
		zero = (byte) (Y == 0 ? 1 : 0);
		negative = (byte) ((Y >> 7) & 0x01);
		Y = (byte) (Y & 0xFF);
	}
	
	private void STA() {
		mem.write(address, accumulator);
	}
	
	private void STX() {
		mem.write(address, X);
	}
	
	private void STY() {
		mem.write(address, Y);
	}
	
	private void INX() {
		X = (X + 1) & 0xFF;
		zero = (byte) (X == 0 ? 1 : 0);
		negative = (byte) ((X >> 7) & 0x01);
	}
	
	private void INY() {
		Y = (Y + 1) & 0xFF;
		zero = (byte) (Y == 0 ? 1 : 0);
		negative = (byte) ((Y >> 7) & 0x01);
	}
	
	private void DEX() {
		X = (X - 1) & 0xFF;
		zero = (byte) (X == 0 ? 1 : 0);
		negative = (byte) ((X >> 7) & 0x01);
	}
	
	private void DEY() {
		Y = (Y - 1) & 0xFF;
		zero = (byte) (Y == 0 ? 1 : 0);
		negative = (byte) ((Y >> 7) & 0x01);
	}
	
	private void TAX() {
		X = accumulator;
		zero = (byte) (X == 0 ? 1 : 0);
		negative = (byte) ((X >> 7) & 0x01);
	}
	
	private void TXA() {
		accumulator = X;
		zero = (byte) (accumulator == 0 ? 1 : 0);
		negative = (byte) ((accumulator >> 7) & 0x01);
	}
	
	private void TAY() {
		Y = accumulator;
		zero = (byte) (Y == 0 ? 1 : 0);
		negative = (byte) ((Y >> 7) & 0x01);
	}
	
	private void TYA() {
		accumulator = Y;
		zero = (byte) (accumulator == 0 ? 1 : 0);
		negative = (byte) ((accumulator >> 7) & 0x01);
	}
	
	private void DEC() {
		int value = mem.read(address);
		value = value - 1 >= 0 ? value - 1 : 0xFF;
		mem.write(address, value);
		zero = (byte) (value == 0 ? 1 : 0);
		negative = (byte) (value >> 7 & 0x01);
	}
	
	private void INC() {
		int value = mem.read(address);
		value = (value + 1) & 0xFF;
		mem.write(address, value);
		zero = (byte) (value == 0 ? 1 : 0);
		negative = (byte) (value >> 7 & 0x01);
	}
	
	private void JMP() {
		PC = address;
	}
	
	private void KIL() {
		state = 0;
	}
	
	private int BPL() {
		if(negative == 1)
			return 0;
		int pcStart = PC - 1;
		PC += (byte) operand;
		return 1 + ((pcStart & 0xFF00) != (PC & 0xFF00) ? 1 : 0);
	}
	
	private int BMI() {
		if(negative == 0)
			return 0;
		int pcStart = PC - 1;
		PC += (byte) operand;
		return 1 + ((pcStart & 0xFF00) != (PC & 0xFF00) ? 1 : 0);
	}
	
	private int BVC() {
		if(overflow > 0)
			return 0;
		int pcStart = PC - 1;
		PC += (byte) operand;
		return 1 + ((pcStart & 0xFF00) != (PC & 0xFF00) ? 1 : 0);
	}
	
	private int BVS() {
		if(overflow == 0)
			return 0;
		int pcStart = PC - 1;
		PC += (byte) operand;
		return 1 + ((pcStart & 0xFF00) != (PC & 0xFF00) ? 1 : 0);
	}
	
	private int BCC() {
		if(carry > 0)
			return 0;
		int pcStart = PC - 1;
		PC += (byte) operand;
		return 1 + ((pcStart & 0xFF00) != (PC & 0xFF00) ? 1 : 0);
	}
	
	private int BCS() {
		if(carry == 0)
			return 0;
		int pcStart = PC - 1;
		PC += (byte) operand;
		return 1 + ((pcStart & 0xFF00) != (PC & 0xFF00) ? 1 : 0);
	}
	
	public int BNE() {
		if(zero == 1)
			return 0;
		int pcStart = PC - 1;
		PC += (byte) operand;
		return 1 + ((pcStart & 0xFF00) != (PC & 0xFF00) ? 1 : 0);
	}
	
	public int BEQ() {
		if(zero == 0)
			return 0;
		int pcStart = PC - 1;
		PC += (byte) operand;
		return 1 + ((pcStart & 0xFF00) != (PC & 0xFF00) ? 1 : 0);
	}
	
	public void CPY() {
		if(Y == operand) {
			zero = 1;
			carry = 1;
			negative = 0;
		} else if(Y > operand) {
			zero = 0;
			carry = 1;
			negative = 0;
		} else if(Y < operand) {
			negative = 1;
			carry = 0;
			zero = 0;
		}
	}
	
	public void CPX() {
		if(X == operand) {
			zero = 1;
			carry = 1;
			negative = 0;
		} else if(X > operand) {
			zero = 0;
			carry = 1;
			negative = 0;
		} else if(X < operand) {
			negative = 1;
			carry = 0;
			zero = 0;
		}
	}
	
	public void CMP() {
		if(accumulator == operand) {
			zero = 1;
			carry = 1;
			negative = 0;
		} else if(accumulator > operand) {
			zero = 0;
			carry = 1;
			negative = 0;
		} else if(accumulator < operand) {
			negative = 1;
			carry = 0;
			zero = 0;
		}
		
	}
	
	private void AND() {
		int value = accumulator & operand;
		zero = (byte) (value == 0 ? 1 : 0);
		negative = (byte) ((value >> 7) & 0x01);
		accumulator = (value & 0xFF);
		
	}
	
	private void ASL() {
		int value = operand << 1;
		zero = (byte) ((value & 0xFF) == 0 ? 1 : 0);
		negative = (byte) ((value >> 7) & 0x01);
		carry = (byte) ((value >> 8) & 0x01);
		operand = (byte) (value & 0xFF);
	}
	
	private void BIT() {
		int value = accumulator & operand;
		zero = (byte) (value == 0 ? 1 : 0);
		negative = (byte) ((operand >> 7) & 0x01);
		overflow = (byte) ((operand >> 6) & 0x01);
		
	}
	
	private void EOR() {
		int value = accumulator ^ operand;
		zero = (byte) (value == 0 ? 1 : 0);
		negative = (byte) ((value >> 7) & 0x01);
		accumulator = (value & 0xFF);
		
	}
	
	private void JSR() {
		getAddressAbsolute();
		PC--;
		mem.write(SPH | SP, ((PC >> 8) & 0xFF));
		SP = (SP - 1) & 0xFF;
		mem.write(SPH | SP,  (PC & 0xFF));
		SP = (SP - 1) & 0xFF;
		PC = address;
	}
	
	private void LSR() {
		carry = (byte) (operand & 0x01);
		int value = operand >> 1;
		zero = (byte) (value == 0 ? 1 : 0);
		negative = 0;
		operand = (byte) (value & 0xFF);
	}
	
	private void NOP() {
		
	}
	
	private void ORA() {
		int value = accumulator | operand;
		zero = (byte) (value == 0 ? 1 : 0);
		negative = (byte) ((value >> 7) & 0x01);
		accumulator = (value & 0xFF);
	}
	
	private void ROL() {
		int value = operand << 1;
		value = value | carry;
		zero = (byte) (value == 0 ? 1 : 0);
		negative = (byte) ((value >> 7) & 0x01);
		carry = (byte) ((operand >> 7) & 0x01);
		operand = (byte) (value & 0xFF);
	}
	
	private void ROR() {
		int value = operand >> 1;
		value = value | (carry << 7);
		zero = (byte) (value == 0 ? 1 : 0);
		negative = (byte) ((value >> 7) & 0x01);
		carry = (byte) (operand & 0x01);
		operand = (byte) (value & 0xFF);
	}
	
	private void RTI() {
		SP = (SP + 1) & 0xFF;
		int statusReg = mem.read(SPH | SP);
		negative = (byte) ((statusReg >> 7) & 1);
		overflow = (byte) ((statusReg >> 6) & 1);
		B = (byte) ((statusReg >> 4) & 1);
		decimal = (byte) ((statusReg >> 3) & 1);
		interrupt = (byte) ((statusReg >> 2) & 1);
		zero = (byte) ((statusReg >> 1) & 1);
		carry = (byte) (statusReg & 1);
		SP = (SP + 1) & 0xFF;
		int PCL = mem.read(SPH | SP);
		SP = (SP + 1) & 0xFF;
		int PCH = mem.read(SPH | SP);
		PCL++;
		PC = PCH << 8 | PCL;
		
	}
	
	private void RTS() {
		SP = (SP + 1) & 0xFF;
		int PCL = mem.read(SPH | SP);
		SP = (SP + 1) & 0xFF;
		int PCH = mem.read(SPH | SP);
		PCL++;
		PC = PCH << 8 | PCL;
	}
	
	private void SBC() {
		int value = (accumulator - operand) - (1 - carry);
		zero = (byte) (value == 0 ? 1 : 0);
		negative = (byte) ((value >> 7) & 0x01);
		if(value < 0) {
			carry = 0;
		} else {
			carry = 1;
		}
		overflow = (byte) ((((accumulator ^ value) & 0x80) != 0 && ((accumulator ^ operand) & 0x80) != 0) ? 1 : 0);
		accumulator = (value & 0xFF);
		
	}
	
	private void TXS() {
		SP = X & 0xFF;
	}
	
	private void TSX() {
		X = SP;
	}
	
	private void PHA() {
		mem.write(SPH | SP, accumulator);
		SP = (SP - 1) & 0xFF;
	}
	
	private void PLA() {
		SP = (SP + 1) & 0xFF;
		accumulator = mem.read(SPH | SP);
	}
	
	private void PHP() {
		byte P = (byte) (negative << 7 | overflow << 6 | 1 << 5 | B << 4 | decimal << 3 | interrupt << 2 | zero << 1 | carry);
		mem.write(SPH | SP, P);
		SP = (SP - 1) & 0xFF;
	}
	
	private void PLP() {
		SP = (SP + 1) & 0xFF;
		byte P = (byte) mem.read(SPH | SP);
		negative = (byte) ((P >> 7) & 0x01);
		overflow = (byte) ((P >> 6) & 0x01);
		B = (byte) ((P >> 4) & 0x01);
		decimal = (byte) ((P >> 3) & 0x01);
		interrupt = (byte) ((P >> 2) & 0x01);
		zero = (byte) ((P >> 1) & 0x01);
		carry = (byte) (P & 0x01);
	}
	
	public void NMI() {
		byte P = (byte) (negative << 7 | overflow << 6 | 1 << 5 | B << 4 | decimal << 3 | interrupt << 2 | zero << 1 | carry);
		PC -= 1;
		mem.write(SPH | SP, ((PC >> 8) & 0xFF));
		SP = (SP - 1) & 0xFF;
		mem.write(SPH | SP,  (PC & 0xFF));
		SP = (SP - 1) & 0xFF;
        mem.write(SPH | SP,  P);
        SP = (SP - 1) & 0xFF;
        PC = mem.read(0xFFFA) + (mem.read(0xFFFB) << 8);
	}
	
	public void reset() {
		PC = mem.read(0xFFFD) << 8 | mem.read(0xFFFC);
		SP -= 3;
		SP &= 0xFF;
	}
	
}
