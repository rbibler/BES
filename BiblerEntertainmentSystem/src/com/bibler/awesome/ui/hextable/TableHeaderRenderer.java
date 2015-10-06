package com.bibler.awesome.ui.hextable;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class TableHeaderRenderer extends DefaultTableCellRenderer {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3380998849063209650L;
	private final TableCellRenderer baseRenderer;

    public TableHeaderRenderer(TableCellRenderer baseRenderer) {
        this.baseRenderer = baseRenderer;
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JComponent c = (JComponent)baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setBorder(new EmptyBorder(2,2,2,2));
        
        return c;
    }
}