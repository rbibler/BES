package com.bibler.awesome.utils;

public class ByteUtils {
	
	static final String[] byteStrings = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
	
	public static byte getValueFromString(String string) {
		byte count = 0;
		String[] raw = string.split("");
		byte high = 0;
		byte low = 0;
		boolean highSet = false;
		boolean lowSet =  false;
		for(String stringToTest : byteStrings) {
			if(raw[0].equalsIgnoreCase(stringToTest)) {
				high = count;
				highSet = true;
			}
			if(raw[1].equalsIgnoreCase(stringToTest)) {
				low = count;
				lowSet = true;
			}
			if(highSet && lowSet) {
				break;
			}
			count++;
		}
		if(highSet && lowSet) {
			return (byte) (((high << 4) & 0b11110000) | (low & 0b1111));
		} 
		return -1;
	}

}
