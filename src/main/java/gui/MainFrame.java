package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private RecipeCreationPanel recipeCreationPanel;
    private RecipeCatalogPanel recipeCatalogPanel ;
    private JSplitPane splitPane;
    private ConcentrateDialog concentrateDialog;

    public MainFrame() {
        super("Liquid Calculator");

        setLayout(new BorderLayout());

        recipeCreationPanel = new RecipeCreationPanel();
        recipeCatalogPanel = new RecipeCatalogPanel();
        concentrateDialog = new ConcentrateDialog(this);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, recipeCatalogPanel, recipeCreationPanel);
        splitPane.setDividerLocation(255);

        add(splitPane, BorderLayout.CENTER);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 800);
        setMinimumSize(new Dimension(650, 750));
        setVisible(true);
    }

    public RecipeCreationPanel getRecipeCreationPanel() {
        return recipeCreationPanel;
    }

    public RecipeCatalogPanel getRecipeCatalogPanel() {
        return recipeCatalogPanel;
    }

    public ConcentrateDialog getConcentrateDialog(){
        return concentrateDialog;
    }
}
