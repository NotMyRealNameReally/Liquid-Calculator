package gui;

import model.ConcentrateInRecipe;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

class ConcentrateTablePanel extends JPanel {
    private JTable table;
    private ConcentrateTableModel tableModel;
    private ButtonPanel btnPanel;

    ConcentrateTablePanel() {
        btnPanel = new ButtonPanel();
        setupTable();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JScrollPane(table));
        add(btnPanel);
        Dimension dim = new Dimension(500, 100);
        this.setMinimumSize(dim);


    }

    void refresh() {
        tableModel.fireTableDataChanged();
    }

    private void setupTable() {
        tableModel = new ConcentrateTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(30);

        TableColumnModel tcm = table.getColumnModel();
        TableColumn column = tcm.getColumn(1);
        SpinnerCellEditor percentEditor = new SpinnerCellEditor();
        percentEditor.setPercentModel();

        column.setCellEditor(percentEditor);
        column.setCellRenderer(new SpinnerCellRenderer());
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

    void setTableListener(ConcentrateTableListener listener) {
        tableModel.setListener(listener);
    }

    void requestConcentrates() {
        tableModel.requestConcentrates();
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
