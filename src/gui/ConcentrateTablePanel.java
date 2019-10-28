package gui;

import model.ConcentrateInRecipe;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

class ConcentrateTablePanel extends JPanel {
    private JTable table;
    private ConcentrateTableModel tableModel;
    private ButtonPanel btnPanel;

    ConcentrateTablePanel() {

        setupTable();
        setupButtons();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JScrollPane(table));
        add(btnPanel);

        Dimension dimPref = new Dimension(400, 200);
        this.setPreferredSize(dimPref);
        Dimension dimMin = new Dimension(400, 150);
        this.setMinimumSize(dimMin);
    }

    void refresh() {
        tableModel.fireTableDataChanged();
    }

    private void setupTable() {
        tableModel = new ConcentrateTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

        TableColumnModel tcm = table.getColumnModel();
        TableColumn column = tcm.getColumn(1);

        tcm.getColumn(0).setPreferredWidth(200);
        column.setPreferredWidth(30);
        tcm.getColumn(2).setPreferredWidth(20);
        tcm.getColumn(3).setPreferredWidth(20);


        SpinnerCellEditor percentEditor = new SpinnerCellEditor();
        percentEditor.setPercentModel();

        column.setCellEditor(percentEditor);
        column.setCellRenderer(new SpinnerCellRenderer());
    }

    private void setupButtons() {
        btnPanel = new ButtonPanel();
        btnPanel.removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0){
                getConcentrates().remove(row);
                refresh();
            }
        });
        //btnPanel.addBtn.addActionListener(e -> );
    }

    void updateVolume(double volume) {
        tableModel.setVolume(volume);
    }

    void setConcentrates(ArrayList<ConcentrateInRecipe> concentrates) {
        tableModel.setConcentrates(concentrates);
    }

    ArrayList<ConcentrateInRecipe> getConcentrates() {
        return tableModel.getConcentrates();
    }

    public double getConcentrateTotal() {
        return tableModel.getConcentrateTotal();
    }

    void setTableListener(TableModelListener listener) {
        tableModel.addTableModelListener(listener);
    }
}

class ButtonPanel extends JPanel {
    JButton addBtn = new JButton("Dodaj");
    JButton removeBtn = new JButton("Usuń");

    ButtonPanel() {
        setLayout(new FlowLayout());
        add(addBtn);
        add(removeBtn);
    }
}
