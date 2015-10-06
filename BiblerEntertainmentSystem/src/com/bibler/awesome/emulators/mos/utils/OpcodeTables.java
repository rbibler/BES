package com.bibler.awesome.emulators.mos.utils;

public class OpcodeTables {
	
	public static String[] opcodes = new String[] {
				"BRK", "ORA", "NOP", "NOP", "NOP", "ORA", "ASL", "NOP", 
				"PHP", "ORA", "ASL", "NOP", "NOP", "ORA", "ASL", "NOP", 
				"BPL", "ORA", "KIL", "NOP", "NOP", "ORA", "ASL", "NOP", 
				"CLC", "ORA", "NOP", "NOP", "NOP", "ORA", "ASL", "NOP", 
				"JSR", "AND", "NOP", "NOP", "BIT", "AND", "ROL", "NOP", 
				"PLP", "AND", "ROL", "NOP", "BIT", "AND", "ROL", "NOP", 
				"BMI", "AND", "NOP", "NOP", "NOP", "AND", "ROL", "NOP", 
				"SEC", "AND", "NOP", "NOP", "NOP", "AND", "ROL", "NOP", 
				"RTI", "EOR", "NOP", "NOP", "NOP", "EOR", "LSR", "NOP", 
				"PHA", "EOR", "LSR", "NOP", "JMP", "EOR", "LSR", "NOP", 
				"BVC", "EOR", "NOP", "NOP", "NOP", "EOR", "LSR", "NOP", 
				"CLI", "EOR", "NOP", "NOP", "NOP", "EOR", "LSR", "NOP", 
				"RTS", "ADC", "NOP", "NOP", "NOP", "ADC", "ROR", "NOP", 
				"PLA", "ADC", "ROR", "NOP", "JMP", "ADC", "ROR", "NOP", 
				"BVS", "ADC", "NOP", "NOP", "NOP", "ADC", "ROR", "NOP", 
				"SEI", "ADC", "NOP", "NOP", "NOP", "ADC", "ROR", "NOP", 
				"NOP", "STA", "NOP", "NOP", "STY", "STA", "STX", "NOP", 
				"DEY", "NOP", "TXA", "NOP", "STY", "STA", "STX", "NOP", 
				"BCC", "STA", "NOP", "NOP", "STY", "STA", "STX", "NOP", 
				"TYA", "STA", "TXS", "NOP", "NOP", "STA", "NOP", "NOP", 
				"LDY", "LDA", "LDX", "NOP", "LDY", "LDA", "LDX", "NOP", 
				"TAY", "LDA", "TAX", "NOP", "LDY", "LDA", "LDX", "NOP", 
				"BCS", "LDA", "NOP", "NOP", "LDY", "LDA", "LDX", "NOP", 
				"CLV", "LDA", "TSX", "NOP", "LDY", "LDA", "LDX", "NOP", 
				"CPY", "CMP", "NOP", "NOP", "CPY", "CMP", "DEC", "NOP", 
				"INY", "CMP", "DEX", "NOP", "CPY", "CMP", "DEC", "NOP", 
				"BNE", "CMP", "NOP", "NOP", "NOP", "CMP", "DEC", "NOP", 
				"CLD", "CMP", "NOP", "NOP", "NOP", "CMP", "DEC", "NOP", 
				"CPX", "SBC", "NOP", "NOP", "CPX", "SBC", "INC", "NOP", 
				"INX", "SBC", "NOP", "NOP", "CPX", "SBC", "INC", "NOP", 
				"BEQ", "SBC", "NOP", "NOP", "NOP", "SBC", "INC", "NOP", 
				"SED", "SBC", "NOP", "NOP", "NOP", "SBC", "INC", "NOP"
		};
	
	public static int[] length = new int[] {
			1, 2, 1, 2, 2, 2, 2, 2, 
			1, 2, 1, 2, 3, 3, 3, 3, 
			2, 2, 1, 2, 2, 2, 2, 2, 
			1, 3, 1, 3, 3, 3, 3, 3, 
			3, 2, 1, 2, 2, 2, 2, 2, 
			1, 2, 1, 2, 3, 3, 3, 3, 
			2, 2, 1, 2, 2, 2, 2, 2, 
			1, 3, 1, 3, 3, 3, 3, 3, 
			3, 2, 1, 2, 2, 2, 2, 2, 
			1, 2, 1, 2, 3, 3, 3, 3, 
			2, 2, 1, 2, 2, 2, 2, 2, 
			1, 3, 1, 3, 3, 3, 3, 3, 
			3, 2, 1, 2, 2, 2, 2, 2, 
			1, 2, 1, 2, 3, 3, 3, 3, 
			2, 2, 1, 2, 2, 2, 2, 2, 
			1, 3, 1, 3, 3, 3, 3, 3, 
			2, 2, 2, 2, 2, 2, 2, 2, 
			1, 2, 1, 2, 3, 3, 3, 3, 
			2, 2, 1, 2, 2, 2, 2, 2, 
			1, 3, 1, 1, 3, 3, 3, 3, 
			2, 2, 2, 2, 2, 2, 2, 2, 
			1, 2, 1, 2, 3, 3, 3, 3, 
			2, 2, 1, 2, 2, 2, 2, 2, 
			1, 3, 1, 3, 3, 3, 3, 3, 
			2, 2, 2, 2, 2, 2, 2, 2, 
			1, 2, 1, 2, 3, 3, 3, 3, 
			2, 2, 1, 2, 2, 2, 2, 2, 
			1, 3, 1, 3, 3, 3, 3, 3, 
			2, 2, 2, 2, 2, 2, 2, 2, 
			1, 2, 1, 2, 3, 3, 3, 3, 
			2, 2, 1, 2, 2, 2, 2, 2, 
			1, 3, 1, 3, 3, 3, 3, 3
	};
	
	public static int[] cycles = new int[] {
			0, 6, 1, 8, 3, 3, 5, 5, 
			3, 2, 2, 2, 4, 4, 6, 6, 
			2, 5, 1, 8, 4, 4, 6, 6, 
			2, 4, 2, 7, 4, 4, 7, 7, 
			6, 6, 1, 8, 3, 3, 5, 5, 
			4, 2, 2, 2, 4, 4, 6, 6, 
			2, 5, 1, 8, 4, 4, 6, 6, 
			2, 4, 2, 7, 4, 4, 7, 7, 
			6, 6, 1, 8, 3, 3, 5, 5, 
			3, 2, 2, 2, 3, 4, 6, 6, 
			2, 5, 1, 8, 4, 4, 6, 6, 
			2, 4, 2, 7, 4, 4, 7, 7, 
			6, 6, 1, 8, 3, 3, 5, 5, 
			4, 2, 2, 2, 5, 4, 6, 6, 
			2, 5, 1, 8, 4, 4, 6, 6, 
			2, 4, 2, 7, 4, 4, 7, 7, 
			2, 6, 2, 6, 3, 3, 3, 3, 
			2, 2, 2, 4, 4, 4, 4, 4, 
			2, 6, 1, 6, 4, 4, 4, 4, 
			2, 5, 2, 5, 5, 5, 5, 5, 
			2, 6, 2, 6, 3, 3, 3, 3, 
			2, 2, 2, 2, 4, 4, 4, 4, 
			2, 5, 1, 5, 4, 4, 4, 4, 
			2, 4, 2, 4, 4, 4, 4, 4, 
			2, 6, 2, 8, 3, 3, 5, 5, 
			2, 2, 2, 2, 4, 4, 6, 6, 
			2, 5, 1, 8, 4, 4, 6, 6, 
			2, 4, 2, 7, 4, 4, 7, 7, 
			2, 6, 2, 8, 3, 3, 5, 5, 
			2, 2, 2, 2, 4, 4, 6, 6, 
			2, 5, 1, 8, 4, 4, 6, 6, 
			2, 4, 2, 7, 4, 4, 7, 7
	};
}