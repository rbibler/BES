package com.bibler.awesome.emulators.mos.nes.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.bibler.awesome.emulators.mos.systems.Controller;
import com.bibler.awesome.emulators.mos.ui.MainFrame;

public class NESPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3032424593726245049L;
	private BufferedImage image;
	private boolean grid = false;
	private boolean attrGrid = false;
	private Controller controller;

	
	private MainFrame mainFrame;
	
	public NESPanel(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		image = new BufferedImage(256, 240, BufferedImage.TYPE_4BYTE_ABGR);
		setPreferredSize(new Dimension(256, 240));
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2) {
					mainFrame.popOutNESPanel();
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
		addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				setBorder(BorderFactory.createLineBorder(Color.GREEN));
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				setBorder(BorderFactory.createLineBorder(Color.RED));
				
			}
			
		});
	}
	
	public void renderFrame(int[] bitmap) {
		int x;
		int y;
		for(int i = 0; i < bitmap.length; i++) {
			x = i % 256;
			y = i / 256;
			try {
			image.setRGB(x, y, NESPalette.grabValue(bitmap[i]));
			} catch(Exception e) {}
		}
		repaint();
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
		addKeyListener(controller);
	}
	
	public Controller getController() {
		return controller;
	}
	
	public void toggleGrid() {
		grid = !grid;
		repaint();
	}
	
	public void toggleAttrGrid() {
		attrGrid = !attrGrid;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = this.getHeight();
		int width = (int) (height * (256.0f/240.0f));
		g.drawImage(image, 0, 0, width, height, null);
		if(grid) {
			drawGrid(g);
		}
		if(attrGrid) {
			drawAttrGrid(g);
		}
	}
	
	private void drawGrid(Graphics g) {
		int height = this.getHeight();
		int width = (int) (height * (256.0f/240.0f));
		int gridX = 8 * (width / 240);
		g.setColor(Color.RED);
		for(int y = 0; y < height; y += gridX) {
			g.drawLine(0, y, width, y);
		}
		for(int x = 0; x < width; x += gridX) {
			g.drawLine(x, 0, x, height);
		}
	}
	
	private void drawAttrGrid(Graphics g) {
		int height = this.getHeight();
		int width = (int) (height * (256.0f/240.0f));
		int gridX = 16 * (width / 240);
		g.setColor(Color.GREEN);
		for(int y = 0; y < height; y += gridX) {
			g.drawLine(0, y, width, y);
		}
		for(int x = 0; x < width; x += gridX) {
			g.drawLine(x, 0, x, height);
		}
		gridX = 32 * (width / 240);
		g.setColor(Color.RED);
		for(int y = 0; y < height; y += gridX) {
			g.drawLine(0, y, width, y);
		}
		for(int x = 0; x < width; x += gridX) {
			g.drawLine(x, 0, x, height);
		}
	}
}
