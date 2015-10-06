package com.bibler.awesome.emulators.mos.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	
	BufferedWriter writer;
	StringBuilder builder;
	
	public Logger() {
		builder = new StringBuilder();
		try {
			writer = new BufferedWriter(new FileWriter(new File("C:/users/ryan/desktop/log.txt")));
		} catch(IOException e) {}
	}
	
	public void log(String toLog, boolean nl) {
		builder.append(toLog);
		if(nl) {
			builder.append("\n");
		}
		if(builder.capacity() - builder.length() < 100) {
			writeBuilder();
		}
	}
	
	private void writeBuilder() {
		try {
			writer.write(builder.toString());
		} catch(IOException e) {}
		builder.delete(0, builder.length());
	}
	
	public void close() {
		writeBuilder();
		try {
			writer.close();
		} catch(IOException e) {}
	}

}
