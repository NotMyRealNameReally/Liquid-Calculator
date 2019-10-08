package gui;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class SpinnerCellEditor extends AbstractCellEditor implements TableCellEditor {
   JSpinner spinner;

    public SpinnerCellEditor(){
        spinner = new JSpinner();
    }


    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        spinner.setValue(value);
        return spinner;
    }
    public void setDropsModel(){
        spinner.setModel(new SpinnerNumberModel(0, 0, 999, 1));
    }
    public void setPercentModel(){
        spinner.setModel(new SpinnerNumberModel(0, 0, 100, 0.1));
    }
    public void setMlModel(){
        spinner.setModel(new SpinnerNumberModel(0, 0, 999, 0.1));
    }
}
