package com.bibler.awesome.emulators.mos.utils;

public class StringUtils {
	
	public static String formatNumber(int number, int length) {
		String temp = Integer.toHexString(number);
		int leadingZeros = length - temp.length();
		if(leadingZeros > 0) {
			String zeros = "";
			for(int i = 0; i < leadingZeros; i++) {
				zeros += "0";
			}
			temp = zeros + temp;
		} else if(leadingZeros < 0) {
			temp = temp.substring(temp.length() - 2);
		}
		return temp.toUpperCase();
	}

}
