package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.text.ParseException;

public class SpinnerCellEditor extends AbstractCellEditor implements TableCellEditor, ChangeListener {
   private SpinnerCellRenderer spinner;

    SpinnerCellEditor(){
        spinner = new SpinnerCellRenderer();
        spinner.addChangeListener(this);
    }

    @Override
    public Object getCellEditorValue() {
       double value = (double)spinner.getValue();
           try {
               spinner.commitEdit();
           } catch (ParseException e) {
               fireEditingCanceled();
       }
        return spinner.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        spinner.setValue(value);
        return spinner;
    }
    void setPercentModel(){
        spinner.setModel(new SpinnerNumberModel(0, 0, 100, 0.1));
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.fireEditingStopped();
    }
}
