package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private RecipeCreationPanel recipeCreationPanel;
    private RecipeCatalogPanel recipeCatalogPanel;
    private JSplitPane splitPane;
    private ConcentrateDialog concentrateDialog;

    public MainFrame() {
        super("Liquid Calculator");

        setupComponents();

        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 800);
        setMinimumSize(new Dimension(650, 750));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showDatabaseConnectionErrorDialog() {
        JOptionPane.showMessageDialog(this, "Problem z połączeniem z bazą danych", "Błąd", JOptionPane.ERROR_MESSAGE);
    }

    private void setupComponents() {
        recipeCreationPanel = new RecipeCreationPanel();
        recipeCatalogPanel = new RecipeCatalogPanel();
        concentrateDialog = new ConcentrateDialog(this);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, recipeCatalogPanel, recipeCreationPanel);
        splitPane.setDividerLocation(355);
    }

    public RecipeCreationPanel getRecipeCreationPanel() {
        return recipeCreationPanel;
    }

    public RecipeCatalogPanel getRecipeCatalogPanel() {
        return recipeCatalogPanel;
    }

    public ConcentrateDialog getConcentrateDialog() {
        return concentrateDialog;
    }
}
