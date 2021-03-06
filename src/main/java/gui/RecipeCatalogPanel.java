package gui;

import model.Recipe;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class RecipeCatalogPanel extends JPanel {
    private JTable table;
    private RecipeTableModel tableModel;
    private CatalogListener listener;
    private JPopupMenu popupMenu;
    private JMenuItem removeItem;

    RecipeCatalogPanel() {
        setupComponents();
        layoutComponents();
        setTableFont();

        setTableMouseListener();
    }

    public void refresh() {
        tableModel.fireTableDataChanged();
    }

    public void showRemoveForbiddenMessage() {
        JOptionPane.showMessageDialog(this, "Nie możesz usuwać czyichś przepisów.", "Błąd", JOptionPane.ERROR_MESSAGE);
    }

    private void setupComponents() {
        tableModel = new RecipeTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(30);
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(150);
        tcm.getColumn(1).setPreferredWidth(60);
        tcm.getColumn(2).setPreferredWidth(5);

        popupMenu = new JPopupMenu();
        removeItem = new JMenuItem("Usuń");
        popupMenu.add(removeItem);
    }

    private void setTableFont() {
        Font oldFont = table.getFont();
        Font newFont = oldFont.deriveFont(Font.BOLD, 15);
        table.setFont(newFont);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void setTableMouseListener() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if (e.getClickCount() == 2 && row != -1 && listener != null) {
                    listener.recipeChosen(row);
                }
                if (e.getButton() == 3) {
                    popupMenu.show(table, e.getX(), e.getY());
                    removeItem.addActionListener(actionEvent -> listener.removeRecipe(row));
                }
            }
        });
    }

    public void setRecipes(List<Recipe> recipes) {
        tableModel.setRecipes(recipes);
    }

    public void setListener(CatalogListener listener) {
        this.listener = listener;
    }
}
