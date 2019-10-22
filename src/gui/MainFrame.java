package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private RecipeCreationPanel recipeCreationPanel;
    private RecipeCatalogPanel recipeCatalogPanel ;
    private JSplitPane splitPane;

    public MainFrame() {
        super("Liquid Calculator");

        setLayout(new BorderLayout());

        recipeCreationPanel = new RecipeCreationPanel();
        recipeCatalogPanel = new RecipeCatalogPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, recipeCatalogPanel, recipeCreationPanel);
        splitPane.setDividerLocation(250);

        add(splitPane, BorderLayout.CENTER);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 1000);
        setMinimumSize(new Dimension(400, 500));
        setVisible(true);
    }

    public RecipeCreationPanel getRecipeCreationPanel() {
        return recipeCreationPanel;
    }

    public RecipeCatalogPanel getRecipeCatalogPanel() {
        return recipeCatalogPanel;
    }
}
