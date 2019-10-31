package gui;

import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class RecipeCatalogPanel extends JPanel {
    private JTable table;
    private RecipeTableModel tableModel;
    private CatalogListener listener;

    RecipeCatalogPanel() {
        setupComponents();
        layoutComponents();

        setTableMouseListener();
    }

    private void setupComponents(){
        tableModel = new RecipeTableModel();
        table = new JTable(tableModel);
    }

    private void layoutComponents(){
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
            }
        });
    }

    public void setRecipes(List<Recipe> recipes) {
        tableModel.setRecipes(recipes);
    }

    public void setListener(CatalogListener listener) {
        this.listener = listener;
    }

    public void refresh() {
        tableModel.fireTableDataChanged();
    }
}
