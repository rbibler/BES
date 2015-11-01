package com.bibler.awesome.ui.hextable;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import com.bibler.awesome.utils.StringUtils;


public class HexTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4008524552089993619L;
	
	final static String[] columnNames = new String[] {
			"00", "01", "02", "03", "04", "05", "06", "07", 
			"08", "09", "0A", "0B", "0C", "0D", "0E", "0F"
	};
	
	private int[] data;
	private Vector<Integer> changedValues;
	private int currentIndex;
	
	public HexTableModel(int size) {
		data = new int[size];
		changedValues = new Vector<Integer>();
	}
	
	public HexTableModel(int[] data) {
		this.data = data;
		changedValues = new Vector<Integer>();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.length / 16;
	}

	@Override
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	@Override
	public Class<String> getColumnClass(int c) {
        return String.class;
    }
	@Override
	public Object getValueAt(int y, int x) {
		int index = (y * 16) + x;
		if(index >= data.length) {
			return "00";
		}
		String ret = Integer.toHexString(data[index] & 0xFF);
		ret = ret.trim();
		if(ret.length() < 2) {
			ret = "0" + ret;
		} else if(ret.length() > 2) {
			ret = ret.substring(ret.length() - 2);
		}
		return ret.toUpperCase();
	}
	
	public void setNewDataArray(int[] data) {
		this.data = data;
		fireTableDataChanged();
	}
	
	public void addByte(byte toAdd) {
		data[currentIndex++] = toAdd;
		fireTableDataChanged();
	}
	
	public void updateCell(int address, int dataToUpdate) {
		if(address >= data.length) {
			return;
		}
		data[address] = dataToUpdate;
		fireTableDataChanged();
	}
	
	public void clearAll() {
		for(int i = 0; i < data.length; i++) {
			data[i] = 0;
		};
		fireTableDataChanged();
	}
	
	public int getDataSize() {
		return data.length;
	}
	
	@Override
	 public boolean isCellEditable(int row, int col)
    { return true; }

	public void setByte(byte b, int currentAddress) {
		data[currentAddress & 0xFF] = b & 0xFF;
		fireTableDataChanged();
	}

	public boolean checkAddressForChange(int address) {
		return changedValues.contains(address);
	}
	
	public void updateData(int[] data) {
		for(int i = 0; i < data.length; i++) {
			this.data[i] = data[i];
		}
		fireTableDataChanged();
	}

}
