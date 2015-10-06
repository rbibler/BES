package com.bibler.awesome.emulators.mos.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.bibler.awesome.emulators.mos.interfaces.Notifiable;
import com.bibler.awesome.emulators.mos.listeners.MenuListener;
import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Controller;
import com.bibler.awesome.emulators.mos.systems.Emulator;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.PPU;
import com.bibler.awesome.emulators.mos.utils.FileLoader;
import com.bibler.awesome.ui.hextable.HexTable;
import com.bibler.awesome.ui.hextable.HexTablePanel;

public class MainFrame extends JFrame implements Notifiable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2428105998995291290L;

	private HexTable memTable;
	private JPanel mainPanel;
	private GridBagConstraints gbc;
	private DisassemblyPanel disassemblyPanel;
	private HexTablePanel memTablePanel;
	private EmulatorStatusPanel statusPanel;
	private Emulator emulator;
	private NESPanel nesPanel;
	private MemoryFrame memFrame = new MemoryFrame();
	private Controller controller = new Controller();
	private PatternTableFrame ptFrame;
	private NameTableFrame ntFrame;
	
	public MainFrame() {
		super();
		setupFrame();
	}
	
	private void setupFrame() {
		initializeLookAndFeelAndExitBehavior();
		setLayout(new BorderLayout());
		
		setupMainPanel();
		setupNESPanel();
		setupStatusPanel();
		setupDisassemblyPanel();
		setupPatternTableFrame();
		setupNameTableFrame();
		add(mainPanel);
		setupMenu();
		pack();
		
	}
	
	private void setupMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
	}
	
	private void setupNESPanel() {
		nesPanel = new NESPanel(this);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(nesPanel, gbc);
	}
	
	private void setupStatusPanel() {
		statusPanel = new EmulatorStatusPanel();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(statusPanel, gbc);
	}
	
	private void setupPatternTableFrame() {
		ptFrame = new PatternTableFrame();
	}
	
	private void setupNameTableFrame() {
		ntFrame = new NameTableFrame();
	}
	
	private void setupDisassemblyPanel() {
		disassemblyPanel = new DisassemblyPanel();
		disassemblyPanel.setPreferredSize(new Dimension(256, 480));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		mainPanel.add(disassemblyPanel, gbc);
	}
	
	private void initializeLookAndFeelAndExitBehavior() {
		try {
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void open() {
		CPU cpu = FileLoader.loadFile(null, this);
		updateDisassemblyPanel(cpu);
		emulator = new Emulator();
		emulator.setCPU(cpu);
		emulator.setNotifiable(this);
		statusPanel.setEmulator(emulator);
		disassemblyPanel.setEmulator(emulator);
		emulator.getCPU().ppu.setMainFrame(this);
		disassemblyPanel.currentLine(emulator.getCPU().PC);
		memFrame.setPPUManager(emulator.getPPU().getManager());
		memFrame.setCPUManager(cpu.mem);
		cpu.setController(controller);
		nesPanel.setController(controller);
		nesPanel.requestFocus();
		ptFrame.setPPU(cpu.ppu);
		ntFrame.setPPU(cpu.ppu);
	}
	
	private void updateDisassemblyPanel(CPU cpu) {
		disassemblyPanel.processFile(cpu);
	}
	
	public void debug() {
		emulator.debug();
	}
	
	public void run() {
		emulator.startEmulation();
	}
	
	public void stepInto() {
		emulator.stepInto();
	}
	
	public void stepOut() {
		emulator.stepOutOf();
	}
	
	public void showMemFrame() {
		memFrame.showTheFrame();
	}
	
	public void switchMemTables() {
		memFrame.switchTables();
	}
	
	public void showPatternTableFrame() {
		ptFrame.showThisFrame();
	}
	
	public void showNameTableFrame() {
		ntFrame.showThisFrame();
	}
	
	public void nextFrame() {
		emulator.nextFrame();
	}
	
	public void showGrid() {
		nesPanel.toggleGrid();
	}
	
	public void showAttr() {
		nesPanel.toggleAttrGrid();
	}
	
	public void setupMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu runMenu = new JMenu("Run");
		JMenu viewMenu = new JMenu("View");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem runItem = new JMenuItem("Run");
		JMenuItem debugItem = new JMenuItem("Debug");
		JMenuItem stepInto = new JMenuItem("Step Into");
		JMenuItem stepOut = new JMenuItem("Step Out");
		JMenuItem reset = new JMenuItem("Reset");
		JMenuItem showMem = new JMenuItem("Show Memory");
		JMenuItem switchMem = new JMenuItem("Switch Memory");
		JMenuItem showPTFrame = new JMenuItem("Pattern Tables");
		JMenuItem showNTFrame = new JMenuItem("Name Tables");
		JMenuItem nextFrame = new JMenuItem("Next Frame");
		JMenuItem showGrid = new JMenuItem("Show Grid");
		JMenuItem showAttrGrid = new JMenuItem("Show Attribute Grid");
		MenuListener listener = new MenuListener(this);
		open.setActionCommand("OPEN");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		open.addActionListener(listener);
		runItem.setActionCommand("RUN");
		runItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, KeyEvent.CTRL_DOWN_MASK));
		runItem.addActionListener(listener);
		debugItem.setActionCommand("DEBUG");
		debugItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
		debugItem.addActionListener(listener);
		stepInto.setActionCommand("STEP_INTO");
		stepInto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		stepInto.addActionListener(listener);
		stepOut.setActionCommand("STEP_OUT");
		stepOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
		stepOut.addActionListener(listener);
		reset.setActionCommand("RESET");
		reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		reset.addActionListener(listener);
		
		showMem.setActionCommand("SHOW_MEM");
		showMem.addActionListener(listener);
		
		switchMem.setActionCommand("SWITCH_MEM");
		switchMem.addActionListener(listener);
		
		showPTFrame.setActionCommand("SHOW_PT");
		showPTFrame.addActionListener(listener);
		
		showNTFrame.setActionCommand("SHOW_NT");
		showNTFrame.addActionListener(listener);
		
		nextFrame.setActionCommand("NEXT_FRAME");
		nextFrame.addActionListener(listener);
		nextFrame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, KeyEvent.CTRL_DOWN_MASK));
		
		showGrid.setActionCommand("SHOW_GRID");
		showGrid.addActionListener(listener);
		showGrid.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
		
		showAttrGrid.setActionCommand("SHOW_ATTR_GRID");
		showAttrGrid.addActionListener(listener);
		showAttrGrid.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
		
		file.add(open);
		runMenu.add(runItem);
		runMenu.add(debugItem);
		runMenu.add(stepInto);
		runMenu.add(stepOut);
		runMenu.add(reset);
		runMenu.add(showMem);
		runMenu.add(switchMem);
		runMenu.add(nextFrame);
		viewMenu.add(showPTFrame);
		viewMenu.add(showNTFrame);
		viewMenu.add(showGrid);
		viewMenu.add(showAttrGrid);
		menuBar.add(file);
		menuBar.add(runMenu);
		menuBar.add(viewMenu);
		setJMenuBar(menuBar);
	}
	
	public void onNotification(Object notifier) {
		if(!(notifier instanceof Emulator)) {
			return;
		}
		statusPanel.updatePanel();
		disassemblyPanel.currentLine(emulator.getCPU().PC);
	}
	
	public void renderFrame(int[] bitmap) {
		nesPanel.renderFrame(bitmap);
	}
	
	public void popOutNESPanel() {
		mainPanel.remove(nesPanel);
		mainPanel.repaint();
		Controller controller = nesPanel.getController();
		nesPanel = new NESPanel(this);
		nesPanel.setController(controller);
		nesPanel.setPreferredSize(new Dimension(256 * 3, 240 * 3));
		JFrame frame = new JFrame();
		frame.add(nesPanel);
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				nesPanel.requestFocusInWindow();
			}

			@Override
			public void windowClosed(WindowEvent arg0) {}

			@Override
			public void windowClosing(WindowEvent arg0) {
				Controller controller = nesPanel.getController();
				nesPanel = new NESPanel(MainFrame.this);
				nesPanel.setController(controller);
				nesPanel.requestFocus();
				mainPanel.add(nesPanel, 0);
				nesPanel.repaint();
				pack();
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {}

			@Override
			public void windowDeiconified(WindowEvent arg0) {}

			@Override
			public void windowIconified(WindowEvent arg0) {}

			@Override
			public void windowOpened(WindowEvent arg0) {}
			
		});
	}

	public void reset() {
		emulator.reset();
	}

	public void shutdown() {
		try {
			emulator.getCPU().ppu.getLogger().close();
		} catch(NullPointerException e) {}
	}
}
