package com.bibler.awesome.ui.hextable;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.bibler.awesome.utils.StringUtils;

public class InfoPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 719969217830573877L;
	JLabel currentAddressLabel;
	JLabel insertStatusLabel;
	int height = 25;
	
	public InfoPanel() {
		super();
		initialize();
	}
	
	private void initialize() {
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		currentAddressLabel = new JLabel("Address: ");
		insertStatusLabel = new JLabel("Overwrite");
		add(currentAddressLabel);
		add(insertStatusLabel);
		layout.putConstraint(SpringLayout.WEST, currentAddressLabel, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, currentAddressLabel, 0, SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, insertStatusLabel, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, insertStatusLabel, 0, SpringLayout.VERTICAL_CENTER, this);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(super.getPreferredSize().width, height);
	}

	public void updateAddress(int address) {
		byte[] bytes = new byte[4];
		byte zero = (byte) ((address >> 24) & 0xFF);
		byte one = (byte) ((address >> 16) & 0xFF);
		byte two = (byte) ((address >> 8) & 0xFF);
		byte three = (byte) (address & 0xFF);
		if(zero == 0) {
			if(one == 0) {
				if(two == 0) {
					bytes = new byte[] {three};
				} else {
					bytes = new byte[] {two, three};
				}
			} else {
				bytes = new byte[] {one, two, three};
			}
		} else {
			bytes = new byte[] {zero, one, two, three};
		}
		String addressString = StringUtils.bytesToHex(bytes, bytes.length);
		currentAddressLabel.setText("Address: " + addressString);
		currentAddressLabel.repaint();
	}

}
