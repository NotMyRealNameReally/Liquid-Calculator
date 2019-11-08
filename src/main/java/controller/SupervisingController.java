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
import java.util.Timer;
import java.util.TimerTask;

public class SupervisingController extends TimerTask implements RecipeCreationControllerInterface, CatalogControllerInterface, LoginDialogInterface {
    private MainFrame mainFrame;
    private RecipeCatalogController recipeCatalogController;
    private RecipeCreationController recipeCreationController;
    private Database database = new Database();
    private LoginDialog loginDialog;
    private Timer databaseUpdateTimer;

    public SupervisingController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        recipeCatalogController = new RecipeCatalogController(mainFrame.getRecipeCatalogPanel(), database.getRecipes(),
                database.getConcentrates(), database.getflavourProfiles(), database.getManufacturers(), mainFrame.getConcentrateDialog());
        recipeCreationController = new RecipeCreationController(mainFrame.getRecipeCreationPanel(), this);

        interceptMainFrameClosing();
        loginDialog = new LoginDialog(mainFrame);
        loginDialog.setListener(this);
        loginDialog.setVisible(true);

        recipeCatalogController.setListener(this);
        try {
            database.connect();
        } catch (SQLException e) {
            mainFrame.showDatabaseConnectionErrorDialog();
        }
        scheduleDatabaseUpdates();
        recipeCatalogController.refreshRecipeTable();
    }

    private void onCloseOperations(){
        if (databaseUpdateTimer != null){
            databaseUpdateTimer.cancel();
        }
        database.disconnect();
    }

    private void interceptMainFrameClosing() {
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                onCloseOperations();
                super.windowClosing(e);
            }
        });
    }

    private void scheduleDatabaseUpdates(){
        databaseUpdateTimer = new Timer();
        databaseUpdateTimer.scheduleAtFixedRate(this, 0, 600000);
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
        mainFrame.dispose();
    }

    //////TimerTask

    @Override
    public void run() {
        try {
            database.getConcentratesFromDatabase();
            database.getManufacturersFromDatabase();
            database.getFlavourProfilesFromDatabase();
            database.updateRecipes();

            recipeCatalogController.refreshRecipeTable();
            recipeCatalogController.refreshConcentrateTable();
        } catch (SQLException e) {
            mainFrame.showDatabaseConnectionErrorDialog();
        }
    }
}
