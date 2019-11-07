package controller;

import gui.CatalogListener;
import gui.ConcentrateDialog;
import gui.ConcentrateDialogListener;
import gui.RecipeCatalogPanel;
import model.Concentrate;
import model.Recipe;

import java.util.List;

public class RecipeCatalogController implements ConcentrateDialogListener, CatalogListener {
    private List<Recipe> recipes;
    private List<Concentrate> concentrates;
    private List<String> flavourProfiles;
    private List<String> manufacturers;
    private RecipeCatalogPanel recipeCatalogPanel;
    private ConcentrateDialog concentrateDialog;
    private CatalogControllerInterface listener;

    public RecipeCatalogController(RecipeCatalogPanel panel, List<Recipe> recipes, List<Concentrate> concentrates,
                                   List<String> flavourProfiles, List<String> manufacturers, ConcentrateDialog concentrateDialog) {
        this.recipeCatalogPanel = panel;
        this.recipes = recipes;
        this.concentrates = concentrates;
        this.flavourProfiles = flavourProfiles;
        this.manufacturers = manufacturers;
        this.concentrateDialog = concentrateDialog;

        recipeCatalogPanel.setRecipes(recipes);
        concentrateDialog.setConcentrates(concentrates);
        concentrateDialog.setFlavourProfiles(flavourProfiles);
        concentrateDialog.setManufacturers(manufacturers);

        setConcentrateDialogListener();
        setCatalogListener();
    }

    public void refreshRecipeTable() {
        recipeCatalogPanel.refresh();
    }

    public void refreshConcentrateTable() {
        concentrateDialog.refresh();
    }

    void showConcentrateDialog() {
        concentrateDialog.refresh();
        concentrateDialog.setVisible(true);
    }

    void showConcentrateAlreadyExistsMessage() {
concentrateDialog.showConcentrateAlreadyExistsMessage();
    }

    private void setConcentrateDialogListener() {
        concentrateDialog.setListener(this);
    }

    private void setCatalogListener() {
        recipeCatalogPanel.setListener(this);
    }

    private boolean isValidConcentrate(String name, String flavourProfile, String manufacturer) {
        String regex = "\\S+.*";
        return (name.matches(regex) && flavourProfile.matches(regex) && manufacturer.matches(regex));
    }

    void setListener(CatalogControllerInterface listener) {
        this.listener = listener;
    }

//////////////ConcentrateDialogListener methods

    @Override
    public void concentrateChosen(int row) {
        listener.concentrateChosen(row);
        concentrateDialog.setVisible(false);
    }

    @Override
    public void concentrateCreated(String name, String flavourProfile, String manufacturer) {
        if (name != null && flavourProfile != null && manufacturer != null && isValidConcentrate(name, flavourProfile, manufacturer)) {
            Concentrate concentrate = new Concentrate(name, manufacturer, flavourProfile);
            listener.concentrateCreated(concentrate);
            concentrateDialog.hideConcentrateCreationDialog();
        } else {
            concentrateDialog.showConcentrateCreationError();
        }
    }


    ////Catalog listener methods
    @Override
    public void recipeChosen(int row) {
        listener.loadRecipe(row);
    }


}
