package com.bibler.awesome.emulators.mos.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Emulator;
import com.bibler.awesome.emulators.mos.utils.StringUtils;

public class EmulatorStatusPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1610877710692668356L;
	private final static int WIDTH = 256;
	private final static int HEIGHT = 256;
	
	private JPanel statusFlagPanel;
	
	private JLabel A;
	private JLabel X;
	private JLabel Y;
	private JLabel PC;
	private JLabel SP;
	private JLabel cycles;
	private JLabel C;
	private JLabel Z;
	private JLabel N;
	private JLabel V;
	
	private JTextField aField;
	private JTextField xField;
	private JTextField yField;
	private JTextField pcField;
	private JTextField spField;
	private JTextField cyclesField;
	private Emulator emulator;
	
	SpringLayout layout = new SpringLayout();
	
	public EmulatorStatusPanel() {
		super();
		initialize();
	}
	
	public void setEmulator(Emulator emulator) {
		this.emulator = emulator;
	}
	
	private void initialize() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(layout);
		initializeLabels();
		initializeFields();
		organize();
		addComponents();
		setBackground(Color.WHITE);
	}
	
	private void addComponents() {
		add(A);
		add(X);
		add(Y);
		add(PC);
		add(SP);
		add(cycles);
		add(aField);
		add(xField);
		add(yField);
		add(pcField);
		add(spField);
		add(cyclesField);
		add(C);
		add(Z);
		add(V);
		add(N);
		add(statusFlagPanel);
	}
	
	private void initializeLabels() {
		A = new JLabel("A:");
		PC = new JLabel("PC:");
		SP = new JLabel("SP:");
		cycles = new JLabel("Cycles:");
		X = new JLabel("X:");
		Y = new JLabel("Y:");
		C = new JLabel("C");
		Z = new JLabel("Z");
		N = new JLabel("N");
		V = new JLabel("V");
	}
	
	private void initializeFields() {
		aField = new JTextField();
		aField.setPreferredSize(new Dimension(75, 25));
		aField.setBackground(Color.WHITE);
		aField.setBorder(null);
		xField = new JTextField();
		xField.setPreferredSize(new Dimension(75, 25));
		xField.setBackground(Color.WHITE);
		xField.setBorder(null);
		yField = new JTextField();
		yField.setPreferredSize(new Dimension(75, 25));
		yField.setBackground(Color.WHITE);
		yField.setBorder(null);
		pcField = new JTextField();
		pcField.setPreferredSize(new Dimension(75, 25));
		pcField.setBackground(Color.WHITE);
		pcField.setBorder(null);
		spField = new JTextField();
		spField.setPreferredSize(new Dimension(75, 25));
		spField.setBackground(Color.WHITE);
		spField.setBorder(null);
		cyclesField = new JTextField();
		cyclesField.setPreferredSize(new Dimension(75, 25));
		cyclesField.setBackground(Color.WHITE);
		cyclesField.setBorder(null);
	}
	
	private void organize() {
		statusFlagPanel = new JPanel();
		statusFlagPanel.setBackground(Color.WHITE);
		statusFlagPanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT - 5));
		statusFlagPanel.setLayout(new BorderLayout());
		layout.putConstraint(SpringLayout.WEST, A, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, A, 0, SpringLayout.VERTICAL_CENTER, aField);
		
		layout.putConstraint(SpringLayout.WEST, aField, 0, SpringLayout.WEST, pcField);
		layout.putConstraint(SpringLayout.NORTH, aField, 5, SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, X, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, X, 0, SpringLayout.VERTICAL_CENTER, xField);
		
		layout.putConstraint(SpringLayout.WEST, xField, 0, SpringLayout.WEST, pcField);
		layout.putConstraint(SpringLayout.NORTH, xField, 5, SpringLayout.SOUTH, aField);
		
		layout.putConstraint(SpringLayout.WEST, Y, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, Y, 0, SpringLayout.VERTICAL_CENTER, yField);
		
		layout.putConstraint(SpringLayout.WEST, yField, 0, SpringLayout.WEST, pcField);
		layout.putConstraint(SpringLayout.NORTH, yField, 5, SpringLayout.SOUTH, xField);
		
		layout.putConstraint(SpringLayout.WEST, PC, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, PC, 0, SpringLayout.VERTICAL_CENTER, pcField);
		
		layout.putConstraint(SpringLayout.WEST, pcField, 2, SpringLayout.EAST, PC);
		layout.putConstraint(SpringLayout.NORTH, pcField, 5, SpringLayout.SOUTH, yField);
		
		layout.putConstraint(SpringLayout.WEST, SP, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, SP, 0, SpringLayout.VERTICAL_CENTER, spField);
		
		layout.putConstraint(SpringLayout.WEST,  spField,  0,  SpringLayout.WEST,  pcField);
		layout.putConstraint(SpringLayout.NORTH, spField, 5, SpringLayout.SOUTH, pcField);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, C, 0, SpringLayout.HORIZONTAL_CENTER, statusFlagPanel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, C, 0, SpringLayout.VERTICAL_CENTER, aField);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Z, 0, SpringLayout.HORIZONTAL_CENTER, C);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, Z, 0, SpringLayout.VERTICAL_CENTER, xField);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, V, 0, SpringLayout.HORIZONTAL_CENTER, C);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, V, 0, SpringLayout.VERTICAL_CENTER, yField);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, N, 0, SpringLayout.HORIZONTAL_CENTER, C);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, N, 0, SpringLayout.VERTICAL_CENTER, pcField);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cyclesField, 0, SpringLayout.HORIZONTAL_CENTER, C);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, cyclesField, 0, SpringLayout.VERTICAL_CENTER, spField);
		
		layout.putConstraint(SpringLayout.EAST, cycles, -5, SpringLayout.WEST, cyclesField);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, cycles, 0, SpringLayout.VERTICAL_CENTER, spField);
		
		layout.putConstraint(SpringLayout.EAST, statusFlagPanel, 0, SpringLayout.EAST, this);
		
	}

	public void updatePanel() {
		CPU cpu = emulator.getCPU();
		aField.setText(StringUtils.formatNumber(cpu.accumulator, 2));
		xField.setText(StringUtils.formatNumber(cpu.X, 2));
		yField.setText(StringUtils.formatNumber(cpu.Y, 2));
		pcField.setText(StringUtils.formatNumber(cpu.PC, 4));
		spField.setText("01" + StringUtils.formatNumber(cpu.SP, 2));
		cyclesField.setText("" + emulator.getCycles());
		int c = emulator.getCPU().carry;
		int z = emulator.getCPU().zero;
		int n = emulator.getCPU().negative;
		int v = emulator.getCPU().overflow;
		if(c == 1) {
			C.setForeground(Color.RED);
		} else {
			C.setForeground(Color.BLACK);
		}
		if(z == 1) {
			Z.setForeground(Color.RED);
		} else {
			Z.setForeground(Color.BLACK);
		}
		if(n == 1) {
			N.setForeground(Color.RED);
		} else {
			N.setForeground(Color.BLACK);
		}
		if(v == 1) {
			V.setForeground(Color.RED);
		} else {
			V.setForeground(Color.BLACK);
		}
		C.repaint();
		Z.repaint();
		N.repaint();
		V.repaint();
	}
	

}
