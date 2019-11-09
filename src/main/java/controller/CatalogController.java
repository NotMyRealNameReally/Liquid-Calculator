package controller;

import gui.*;
import model.Concentrate;
import model.Database;

public class CatalogController implements ConcentrateDialogListener, CatalogListener {
    private RecipeCatalogPanel recipeCatalogPanel;
    private ConcentrateDialog concentrateDialog;
    private CatalogControllerInterface listener;

    CatalogController(MainFrame mainFrame, Database database) {
        this.recipeCatalogPanel = mainFrame.getRecipeCatalogPanel();
        this.concentrateDialog = mainFrame.getConcentrateDialog();

        recipeCatalogPanel.setRecipes(database.getRecipes());
        concentrateDialog.setConcentrates(database.getConcentrates());
        concentrateDialog.setFlavourProfiles(database.getflavourProfiles());
        concentrateDialog.setManufacturers(database.getManufacturers());

        setConcentrateDialogListener();
        setCatalogListener();
    }

    void refreshRecipeTable() {
        recipeCatalogPanel.refresh();
    }

    void refreshConcentrateTable() {
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
