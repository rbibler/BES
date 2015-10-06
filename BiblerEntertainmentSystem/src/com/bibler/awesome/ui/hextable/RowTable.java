package com.bibler.awesome.ui.hextable;
import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.table.*;

import com.bibler.awesome.utils.StringUtils;


/*
 *	Use a JTable as a renderer for row numbers of a given main table.
 *  This table must be added to the row header of the scrollpane that
 *  contains the main table.
 */
public class RowTable extends JTable
	implements ChangeListener, PropertyChangeListener, TableModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8923185187962116393L;
	private JTable main;

	public RowTable(JTable table) {
		main = table;
		main.addPropertyChangeListener( this );
		main.getModel().addTableModelListener( this );
		setShowGrid(false);
		setIntercellSpacing(new Dimension(0, 0));
		setFocusable( false );
		setAutoCreateColumnsFromModel( false );
		setSelectionModel( main.getSelectionModel() );


		TableColumn column = new TableColumn();
		column.setHeaderValue(" ");
		addColumn( column );
		column.setCellRenderer(new RowNumberRenderer());

		getColumnModel().getColumn(0).setPreferredWidth(75);
		setPreferredScrollableViewportSize(getPreferredSize());
	}

	@Override
	public void addNotify() {
		super.addNotify();

		Component c = getParent();

		//  Keep scrolling of the row table in sync with the main table.

		if (c instanceof JViewport) {
			JViewport viewport = (JViewport)c;
			viewport.addChangeListener( this );
		}
	}

	/*
	 *  Delegate method to main table
	 */
	@Override
	public int getRowCount() {
		return main.getRowCount();
	}

	@Override
	public int getRowHeight(int row) {
		int rowHeight = main.getRowHeight(row);

		if (rowHeight != super.getRowHeight(row)) {
			super.setRowHeight(row, rowHeight);
		}

		return rowHeight;
	}

	/*
	 *  No model is being used for this table so just use the row number
	 *  as the value of the cell.
	 */
	@Override
	public Object getValueAt(int row, int column) {
		int rowToReturn = row * 16;
		byte[] bytes = new byte[4];
		bytes[0] = (byte) ((rowToReturn >> 24) & 0xFF);
		bytes[1] = (byte) ((rowToReturn >> 16) & 0xFF);
		bytes[2] = (byte) ((rowToReturn >> 8) & 0xFF);
		bytes[3] = (byte) (rowToReturn & 0xFF);
		return StringUtils.bytesToHex(bytes, bytes.length);
	}

	/*
	 *  Don't edit data in the main TableModel by mistake
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	/*
	 *  Do nothing since the table ignores the model
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {}
//
//  Implement the ChangeListener
//
	public void stateChanged(ChangeEvent e) {
		//  Keep the scrolling of the row table in sync with main table

		JViewport viewport = (JViewport) e.getSource();
		JScrollPane scrollPane = (JScrollPane)viewport.getParent();
		scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
	}
//
//  Implement the PropertyChangeListener
//
	public void propertyChange(PropertyChangeEvent e) {
		//  Keep the row table in sync with the main table

		if ("selectionModel".equals(e.getPropertyName())) {
			setSelectionModel( main.getSelectionModel() );
		}

		if ("rowHeight".equals(e.getPropertyName())) {
			repaint();
		}

		if ("model".equals(e.getPropertyName())) {
			main.getModel().addTableModelListener( this );
			revalidate();
		}
	}

//
//  Implement the TableModelListener
//
	@Override
	public void tableChanged(TableModelEvent e) {
		revalidate();
	}

	/*
	 *  Attempt to mimic the table header renderer
	 */
	private static class RowNumberRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3896600531931568911L;

		public RowNumberRenderer() {
			setHorizontalAlignment(JLabel.CENTER);
		}

		public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (table != null) {
				JTableHeader header = table.getTableHeader();

				if (header != null) {
					setForeground(header.getForeground());
					setBackground(header.getBackground());
					setFont(header.getFont());
				}
			}


			setText((value == null) ? "" : value.toString());
			setBorder(new EmptyBorder(2,2,2,2));

			return this;
		}
	}
}