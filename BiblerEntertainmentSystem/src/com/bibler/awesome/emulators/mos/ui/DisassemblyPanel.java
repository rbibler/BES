package com.bibler.awesome.emulators.mos.ui;

import java.awt.Color;
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
		int[] program = cpu.mem.consolidateMemory();
		StringBuilder builder = new StringBuilder();
		int opCode;
		int length;
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
			builder.append("\n");
		}
		disassemblyArea.setText(builder.toString());
		this.scrollRectToVisible(new Rectangle(0, 0, 0, 0));
	}
	
	public void highlightLine(int line2Highlight, HighlightPainter painter) {
		int line = 0;
		try {
		line = pc2Line.get(line2Highlight);
		} catch(NullPointerException e) {
			line = 0;
		}
		int start;
		int end;
		try {
			start = disassemblyArea.getLineStartOffset(line);
			end = disassemblyArea.getLineEndOffset(line);
			disassemblyArea.getHighlighter().addHighlight(start, end, painter);
			scrollTo(start);
		} catch (BadLocationException e) {}
		
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
