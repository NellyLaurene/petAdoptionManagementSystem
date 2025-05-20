package org.pet_adoption_system.view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TableComponents extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;

    public TableComponents(String[] columnNames) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Table model setup
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells non-editable by default
            }
        };

        // JTable setup
        table = new JTable(tableModel);
        table.setRowHeight(36);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setGridColor(new Color(220, 220, 220));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));

        // Scroll pane styling
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
