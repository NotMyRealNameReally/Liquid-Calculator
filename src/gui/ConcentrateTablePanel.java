package gui;

import model.ConcentrateInRecipe;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

 class ConcentrateTablePanel extends JPanel {
    private JTable table;
    private ConcentrateTableModel tableModel;
    private JButton addConcentrateBtn;

     ConcentrateTablePanel() {

        setupTable();

        addConcentrateBtn = new JButton("Dodaj aromat");

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(addConcentrateBtn, BorderLayout.LINE_START);

        addConcentrateBtn.addActionListener(e -> refresh());
    }

    void setDatabase(List<ConcentrateInRecipe> database) {
        tableModel.setDatabase(database);
    }

    void refresh() {
        tableModel.fireTableDataChanged();
    }

    private void setupTable() {
        tableModel = new ConcentrateTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.putClientProperty("terminateEditOnFocusLost", true);

        TableColumnModel tcm = table.getColumnModel();
        TableColumn column = tcm.getColumn(1);
        SpinnerCellEditor percentEditor = new SpinnerCellEditor();
        percentEditor.setPercentModel();

        column.setCellEditor(percentEditor);
        column.setCellRenderer(new SpinnerCellRenderer());
    }
    void updateVolume(int volume){
        tableModel.setVolume(volume);
    }
}
