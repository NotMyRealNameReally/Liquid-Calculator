package gui;

import model.ConcentrateInRecipe;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class ConcentrateTablePanel extends JPanel {
    private JTable table;
    private ConcentrateTableModel tableModel;
    private JButton addConcentrateBtn;
    private JButton removeConcentrateBtn;
    private ConcentrateTableListener tableListener;

    public void setTableListener(ConcentrateTableListener tableListener) {
        this.tableListener = tableListener;
    }

    public ConcentrateTablePanel() {
        tableModel = new ConcentrateTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(30);
        setupEditors();

        addConcentrateBtn = new JButton("Dodaj aromat");
        removeConcentrateBtn = new JButton("Usuń aromat");

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(addConcentrateBtn, BorderLayout.LINE_START);

        addConcentrateBtn.addActionListener(e -> refresh());
    }

    public void setDatabase(List<ConcentrateInRecipe> database) {
        tableModel.setDatabase(database);
    }

    public void refresh() {
        tableModel.fireTableDataChanged();
    }

    private void setupEditors() {
        TableColumnModel tcm = table.getColumnModel();
        TableColumn column = tcm.getColumn(1);
        SpinnerCellEditor percentEditor = new SpinnerCellEditor();
        percentEditor.setPercentModel();

        column.setCellEditor(percentEditor);
        percentEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {

                    double percentage = (double)percentEditor.spinner.getValue();
                    tableModel.setConcentratePercentage(table.getSelectedRow(), percentage);
                    refresh();
            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });
    }
}
