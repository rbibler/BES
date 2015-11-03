package com.bibler.awesome.emulators.mos.ui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Painter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import com.bibler.awesome.emulators.mos.systems.CPU6502;
import com.bibler.awesome.emulators.mos.systems.Emulator;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.utils.OpcodeTables;
import com.bibler.awesome.emulators.mos.utils.StringUtils;

public class DisassemblyPanel extends JScrollPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9222812443040674542L;
	private JTextArea disassemblyArea;
	private Map<Integer, Integer> pc2Line = new HashMap<Integer, Integer>();
	private HighlightPainter painter;
	private Emulator emulator;
	private DefaultHighlightPainter greenPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
	private DefaultHighlightPainter bluePainter = new DefaultHighlighter.DefaultHighlightPainter(Color.BLUE);
	
	public DisassemblyPanel() {
		disassemblyArea = new JTextArea();
		disassemblyArea.setAutoscrolls(false);
		disassemblyArea.setEditable(false);
		disassemblyArea.setSelectionColor(new Color(0, 0, 0, 0));
		DefaultCaret caret = (DefaultCaret)disassemblyArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		setupMouseListener();
		this.setViewportView(disassemblyArea);
	}
	
	public void setEmulator(Emulator emulator) {
		this.emulator = emulator;
	}
	
	private void setupMouseListener() {
		disassemblyArea.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(arg0.getClickCount() == 2) {
					int offset = disassemblyArea.viewToModel(arg0.getPoint());
					int line = 0;
					try {
						line = disassemblyArea.getLineOfOffset(offset);
						highlightLine(findPC(line), greenPainter);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					emulator.addBreakPoint(findPC(line));
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
	}
	
	private int findPC(int line) {
		int ret = 0;
		Set<Entry<Integer, Integer>> set = pc2Line.entrySet();
		for(Entry<Integer, Integer> entry : set) {
			if(entry.getValue() == line) {
				ret = entry.getKey();
			}
		}
		return ret;
	}
	
	public void processFile(CPU6502 cpu) {
<<<<<<< HEAD
=======
		int[] program = cpu.mem.consolidateMemory();
>>>>>>> parent of dc45d53... Screwing around with the disassembler
		StringBuilder builder = new StringBuilder();
		int opCode;
		int length;
<<<<<<< HEAD
		int arg1 = 0;
		int arg2 = 0;
		for(int i = 0x8000; i < 0xFFFF; i += length) {
			pc2Line.put(i, lineCount++);
			opCode = (cpu.read(i));
			builder.append(StringUtils.formatNumber(i, 4));
			builder.append("    ");
			length = OpcodeTables.length[opCode];
			//length = length > 0 ? length : 1;
			if(length == 2) {
				arg1 = cpu.read(i + 1);
			} else if(length == 3) {
				arg1 = cpu.read(i + 1);
				arg2 = cpu.read(i + 2);
			}
			String s = String.format(OpcodeTables.formattedOpCodes[opCode], arg1, arg2);
			builder.append(s);
=======
		int lineCount = 0;
		String name;
		String operands;
		for(int i = 0; i < program.length; i++) {
			pc2Line.put(i, lineCount++);
			operands = " ";
			opCode = (program[i] & 0xFF);
			builder.append(StringUtils.formatNumber(i, 4));
			builder.append("    ");
			if(opCode == 0) {
				name = ".db";
				operands = " $00";
			} else {
				name = OpcodeTables.opcodes[opCode];
				length = OpcodeTables.length[opCode] - 1;
				for(int j = 0; j < length; j++) {
					try {
					operands += StringUtils.formatNumber(program[i + 1 + j], 2);
					} catch(Exception e) {}
				}
				i += length;
			}
			builder.append(name + operands);
>>>>>>> parent of dc45d53... Screwing around with the disassembler
			builder.append("\n");
		}
		disassemblyArea.setText(builder.toString());
		this.scrollRectToVisible(new Rectangle(0, 0, 0, 0));
	}
	
	public void highlightLine(int line2Highlight, HighlightPainter painter) {
		Point p = getStartEnd(line2Highlight);
		if(p.x < 0 || p.y < 0)
			return;
		try {
			disassemblyArea.getHighlighter().addHighlight(p.x, p.y, painter);
			scrollTo(p.x);
		} catch (BadLocationException e) {}
	}
	
	public void dehighlightLine(int line2Highlight, HighlightPainter painter) {
		Point p = getStartEnd(line2Highlight);
		if(p.x < 0 || p.y < 0) 
			return;
		Highlight h = findHighlight(p.x, p.y);
		if(h == null)
			return;
		disassemblyArea.getHighlighter().removeHighlight(h);
	}
	
	private Point getStartEnd(int line) {
		line = pc2Line.get(line);
		int start = -1;
		int end = -1;
		try {
			start = disassemblyArea.getLineStartOffset(line);
			end = disassemblyArea.getLineEndOffset(line);
		} catch(BadLocationException e) {}
		return new Point(start, end);
	}
	
	private Highlight findHighlight(int start, int end) {
		Highlight[] highlights = disassemblyArea.getHighlighter().getHighlights();
		for(Highlight highlight : highlights) {
			if(highlight.getStartOffset() == start && highlight.getEndOffset() == end) {
				return highlight;
			}
		}
		return null;
	}

	public void scrollTo(int start) throws BadLocationException {
		Rectangle rect = disassemblyArea.modelToView(start);
		rect.y -= this.getHeight() / 2;
		rect.height= this.getHeight();
		disassemblyArea.scrollRectToVisible(rect);
		repaint();
	}
	
	public void currentLine(int pc) {
		Highlighter highlights = disassemblyArea.getHighlighter();
		for(Highlight highlight : highlights.getHighlights()) {
			if(highlight.getPainter() == bluePainter) {
				highlights.removeHighlight(highlight);
			}
		}
		highlightLine(pc, bluePainter);
	}

}
