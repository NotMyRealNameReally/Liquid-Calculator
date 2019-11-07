package controller;

import gui.LoginDialog;
import gui.LoginDialogInterface;
import gui.MainFrame;
import model.Concentrate;
import model.Database;
import model.Recipe;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class SupervisingController implements RecipeCreationControllerInterface, CatalogControllerInterface, LoginDialogInterface {
    private MainFrame mainFrame;
    private RecipeCatalogController recipeCatalogController;
    private RecipeCreationController recipeCreationController;
    private Database database = new Database();
    private LoginDialog loginDialog;

    public SupervisingController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        recipeCatalogController = new RecipeCatalogController(mainFrame.getRecipeCatalogPanel(), database.getRecipes(),
                database.getConcentrates(), database.getflavourProfiles(), database.getManufacturers(), mainFrame.getConcentrateDialog());
        recipeCreationController = new RecipeCreationController(mainFrame.getRecipeCreationPanel(), this);

        loginDialog = new LoginDialog(mainFrame);
        loginDialog.setListener(this);
        loginDialog.setVisible(true);

        recipeCatalogController.setListener(this);
        try {
            database.connect();
            database.getConcentratesFromDatabase();
            database.getManufacturersFromDatabase();
            database.getFlavourProfilesFromDatabase();
            database.updateRecipes();
        } catch (SQLException e) {
            mainFrame.showDatabaseConnectionErrorDialog();
        }
        setOnProgramClose();
        recipeCatalogController.refreshRecipeTable();
    }

    private void setOnProgramClose() {
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                database.disconnect();
                super.windowClosing(e);
            }
        });
    }
    ////////Recipe creation controller methods

    @Override
    public void saveRecipe(Recipe recipe) {
        recipe.setAuthor(database.getUserName());
        try {
            if (database.isRecipeInDatabase(recipe)) {
                if (recipeCreationController.getRecipeOverwriteConfirmation()) {
                    database.updateRecipeInDatabase(recipe);
                    database.updateRecipes();
                }
            } else {
                database.insertRecipeToDatabase(recipe);
                database.addRecipe(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        recipeCatalogController.refreshRecipeTable();
    }

    @Override
    public void requestConcentrateDialog() {
        recipeCatalogController.showConcentrateDialog();
    }

    ////////Catalog controller methods

    @Override
    public void concentrateCreated(Concentrate concentrate) {
        try {
            if (database.isConcentrateInDatabase(concentrate)) {
                recipeCatalogController.showConcentrateAlreadyExistsMessage();
            } else {
                database.insertConcentrateToDatabase(concentrate);
                database.addConcentrate(concentrate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    /////LoginDialog methods

    @Override
    public void userNameEntered(String username) {
        if (!username.matches(" *")) {
            database.setUserName(username);
            loginDialog.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(loginDialog, "Zła nazwa użytkownika", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void loginCancelled() {
        MainFrame frame = (MainFrame) loginDialog.getParent();
        loginDialog.dispose();
        frame.dispose();
        database.disconnect();
    }
}
