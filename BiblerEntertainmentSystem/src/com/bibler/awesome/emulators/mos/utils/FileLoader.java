package com.bibler.awesome.emulators.mos.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

import com.bibler.awesome.emulators.mos.systems.CPU6502;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;
import com.bibler.awesome.emulators.mos.systems.PPU;
import com.bibler.awesome.emulators.mos.systems.PPUMemoryManager;
import com.bibler.awesome.emulators.mos.ui.MainFrame;

public class FileLoader {
	
	static JFileChooser chooser = new JFileChooser();
	
	public static CPU6502 loadFile(File f, MainFrame frame) {
		if(f == null) {
			f = getFile(frame);
			if(f == null)
				return null;
		}
		if(f.getName().toLowerCase().contains(".nes")) {
			return loadNES(getInput(f));
		} else if(f.getName().toLowerCase().contains(".zip")) {
			return loadZip(f);
		}
		return null;
		
	}
	
	private static CPU6502 loadZip(File f) {
		ZipFile zip = null;
		try {
			zip = new ZipFile(f);
		} catch(IOException e) {}
		Enumeration<? extends ZipEntry> entries = zip.entries();
		while(entries.hasMoreElements()) {
			ZipEntry e = entries.nextElement();
			if(e.getName().toLowerCase().contains(".nes")) {
				try {
					return loadNES(zip.getInputStream(e));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
	
	private static CPU6502 loadNES(InputStream input) {
		CPU6502 cpu = setupCPU();
		byte[] header = getHeader(input);
		int prg = header[4];
		int chr = header[5];
		boolean horiz = (((header[6] & 1) == 0) && ((header[6] >> 3) & 1) == 0);
		boolean vert = ((header[6] >> 1 & 1) == 1) && ((header[6] >> 3 & 1) == 0);
		int mapper = header[7] & 0xF0 | header[6] >> 4;
		if(mapper != 0) {
			System.out.println("FAILURE NO MAPPER BADNESS, Visible: " + mapper);
		}
		loadPrg(input, prg, cpu);
		loadChr(input, chr, cpu);
		int add = ((prg * 0x4000) + 0x8000) - 4;
		int pcL = cpu.read(add);
		int pcH = cpu.read(add + 1);
		cpu.setPC(pcH << 8 | pcL);
		cpu.getPPU().setMirroring(horiz, vert);
		return cpu;
	}
	
	private static void loadPrg(InputStream input, int prg, CPU6502 cpu) {
		int address = 0x8000;
		int bytesToLoad = (prg * 16384) - 1;
		int loadenBytes = 0;
		int result = 0;
		byte[] read = new byte[1];
		MemoryManager mem = cpu.getMem();
		try {
			while(result >= 0 && loadenBytes < bytesToLoad) {
				result = input.read(read);
				mem.write(address++, read[0] & 0xFF);
				loadenBytes++;
			}
			input.read();
		} catch(IOException e) {}
		if(prg == 1) {
			mem.copyPRG();
		}
	}
	
	private static void loadChr(InputStream input, int chr, CPU6502 cpu) {
		int bytesToLoad = (chr * 8192) - 1;
		int loadenBytes = 0;
		int result = 0;
		byte[] read = new byte[1];
		int address = 0;
		PPUMemoryManager mem = cpu.getPPU().manager;
		try {
			while(result >= 0 && loadenBytes < bytesToLoad) {
				result = input.read(read);
				try {
					mem.write(address++, read[0] & 0xFF);
				} catch(ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();}
				loadenBytes++;
			}
		} catch(IOException e) {}
		finally {
			if(input != null) {
				try {
					input.close();
				} catch(IOException e) {}
			} 
		}
	}
	
	private static byte[] getHeader(InputStream stream) {
		byte[] header = new byte[16];
		try {
			stream.read(header);
		} catch(IOException e) {}
		return header;
	}
	
	private static CPU6502 setupCPU() {
		return CPU6502.getInstance(new PPU());
	}
	
	private static BufferedInputStream getInput(File f) {
		BufferedInputStream input = null;
		try {
			input = new BufferedInputStream(new FileInputStream(f));
		} catch(IOException e) {};
		return input;
	}
	
	private static File getFile(MainFrame frame) {
		chooser.setCurrentDirectory(new File("C:/users/ryan/desktop/upload/nes stuff/roms"));
		int val = chooser.showOpenDialog(frame);
		if(val == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}

}
