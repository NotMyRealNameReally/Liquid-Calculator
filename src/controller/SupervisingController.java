package controller;

import gui.MainFrame;
import model.Concentrate;
import model.Database;
import model.Recipe;

public class SupervisingController implements RecipeCreationControllerInterface, CatalogControllerInterface {
    private MainFrame mainFrame;
    private RecipeCatalogController recipeCatalogController;
    private RecipeCreationController recipeCreationController;
    private Database database = new Database();

    public SupervisingController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        recipeCatalogController = new RecipeCatalogController(mainFrame.getRecipeCatalogPanel(), database.getRecipes(),
                database.getConcentrates(), database.getflavourProfiles(), database.getManufacturers(), mainFrame.getConcentrateDialog());
        recipeCreationController = new RecipeCreationController(mainFrame.getRecipeCreationPanel(), this);

        recipeCatalogController.setListener(this);
    }
    ////////Recipe creation controller methods

    @Override
    public void saveRecipe(Recipe recipe) {
        database.addRecipe(recipe);
        recipeCatalogController.refreshRecipeTable();
    }

    @Override
    public void requestConcentrateDialog() {
        recipeCatalogController.showConcentrateDialog();
    }

    ////////Catalog controller methods

    @Override
    public void concentrateCreated(Concentrate concentrate) {
        database.addConcentrate(concentrate);
        recipeCatalogController.refreshConcentrateTable();
    }

    @Override
    public void concentrateChosen(int row) {
        recipeCreationController.addConcentrateInRecipe(database.getConcentrates().get(row));
    }

    @Override
    public void loadRecipe(int row) {
        Recipe recipe = database.getRecipes().get(row);
        recipeCreationController.loadRecipe(recipe);
    }
}
