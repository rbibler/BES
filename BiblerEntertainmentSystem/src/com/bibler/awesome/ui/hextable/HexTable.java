package com.bibler.awesome.ui.hextable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.bibler.awesome.utils.ByteUtils;

public class HexTable extends JTable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1591115493121167305L;
	private HexTableModel model = new HexTableModel(256);
	private static int highlightedByte = 0;
	private int currentAddress;
	final static int HIGHLIGHT_UP_TO = 0x03;
	final static int HIGHLIGHT_SELECTED_ONLY = 0x04;
	private int highlightMode = HIGHLIGHT_UP_TO;
	private InfoPanel infoPanel;
	
	public HexTable() {
		this.setModel(model);
		initialize();
	}
	
	public void setInfoPanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}
	
	public void setData(int[] data) {
		model.setNewDataArray(data);
	}
	
	public void update() {
		model.fireTableDataChanged();
	}
	
	public void initialize() {
		setDefaultRenderer(String.class, new CellRenderer());
		setShowGrid(false);
		setIntercellSpacing(new Dimension(0, 0));
		TableCellRenderer baseRenderer = getTableHeader().getDefaultRenderer();
		getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));
		TableColumn column;
		for(int i = 0; i < 16; i++) {
			column = getColumnModel().getColumn(i);
			column.setPreferredWidth(30);
			column.setCellEditor(new DefaultCellEditor(new JTextField()));
			column.getCellEditor().addCellEditorListener(new CellEditorListener() {

				@Override
				public void editingCanceled(ChangeEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void editingStopped(ChangeEvent arg0) {
					DefaultCellEditor editor = (DefaultCellEditor) arg0.getSource();
					JTextField field = (JTextField) editor.getComponent();
					String text = field.getText();
					if(text.length() == 1) {
						text = "0" + text;
					}
					byte b = ByteUtils.getValueFromString(text);
					if(b != -1) {
						updateCurrentValue(b);
					}
				}
				
			});
			
		}
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setColumnSelectionAllowed(true);
		
	}
	
	private void updateCurrentValue(byte b) {
		model.setByte(b, currentAddress);
	}
	
	public void addByte(byte toAdd) {
		model.addByte(toAdd);
		repaint();
	}
	
	public void clearAll() {
		model.clearAll();
		repaint();
		gotoAddress(0);
	}
	
	public void changeByte(int address, int data) {
		model.setByte((byte) data, address);
	}
	
	public void setHighlightedByte(int byteToSet) {
		highlightedByte = byteToSet;
	}
	
	public void incrementHighlight() {
		highlightedByte++;
		repaint();
	}
	
	public int getDataSize() {
		return model.getDataSize();
	}
	
	public int getHighlightMode() {
		return highlightMode;
	}

	public void gotoAddress(int address) {
		highlightedByte = address;
		int row = highlightedByte / 16;
		int rowHeight = getRowHeight();
		Rectangle rect = new Rectangle(0, row * rowHeight, 200, 200);
		this.scrollRectToVisible(rect);
	}
	
	public void updateData(int[] newData) {
		
		model.updateData(newData);
	}

	
	private class CellRenderer extends DefaultTableCellRenderer {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = -2217390881555777552L;

		public CellRenderer() {
			setHorizontalAlignment(JLabel.CENTER);
		}

		public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			setForeground(Color.BLACK);
			if(isSelected) {
				setForeground(Color.BLUE);
				currentAddress = (row * 16) + column;
				infoPanel.updateAddress(currentAddress);
			} else { 
				checkHighlightStatus(row, column);
			}
			checkChanged(row, column);
			setText((value == null) ? "" : value.toString());
			setBorder(new EmptyBorder(2,2,2,2));
			return this;
		}
		
		private void checkChanged(int row, int column) {
			int address = row * 16 + column;
			if(model.checkAddressForChange(address)) {
				setForeground(Color.RED);
			}
		}
		
		private void checkHighlightStatus(int row, int column) {
			int index = (row * 16) + column;
			Color foreground = Color.BLACK;
			switch(HexTable.this.getHighlightMode()) {
				case HexTable.HIGHLIGHT_SELECTED_ONLY:
					if(index == highlightedByte) {
						foreground = Color.GREEN;
					}
					break;
				case HexTable.HIGHLIGHT_UP_TO:
					if(index <= highlightedByte) {
						foreground = Color.GREEN;
					}
					break;
			}
			setForeground(foreground);
		}
	}
	
	@Override
	public void changeSelection(
			int row, int column, boolean toggle, boolean extend) {
		super.changeSelection(row, column, toggle, extend);
		currentAddress = (row * 16) + column;
	    if (editCellAt(row, column)) {
	    	JTextField editor = (JTextField) getEditorComponent();
	        editor.requestFocusInWindow();
	        editor.setBorder(new EmptyBorder(2, 2, 2, 2));
	        editor.setCaretPosition(0);
	        editor.setHorizontalAlignment(SwingConstants.CENTER);
	    }
	}

	public int getHighlightedByte() {
		return highlightedByte;
	}
}
