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
    private CatalogController catalogController;
    private RecipeCreationController recipeCreationController;
    private Database database = new Database();
    private LoginDialog loginDialog;
    private Timer databaseUpdateTimer;

    public SupervisingController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        catalogController = new CatalogController(mainFrame, database);
        recipeCreationController = new RecipeCreationController(mainFrame.getRecipeCreationPanel());

        interceptMainFrameClosing();
        setupLoginDialog();

        catalogController.setListener(this);
        recipeCreationController.setListener(this);

        try {
            database.connect();
        } catch (SQLException e) {
            mainFrame.showDatabaseConnectionErrorDialog();
        }

        scheduleDatabaseUpdates();
    }

    ////////Recipe creation controller methods

    @Override
    public void saveRecipe(Recipe recipe) {
        recipe.setAuthor(database.getUserName());
        try {
            if (database.isRecipeInDatabase(recipe) && recipeCreationController.getRecipeOverwriteConfirmation()) {
                    database.updateRecipeInDatabase(recipe);
                    database.getRecipesFromDatabase();
            } else {
                database.insertRecipeToDatabase(recipe);
                database.addRecipe(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catalogController.refreshRecipeTable();
    }

    @Override
    public void requestConcentrateDialog() {
        catalogController.showConcentrateDialog();
    }

    ////////Catalog controller methods

    @Override
    public void concentrateCreated(Concentrate concentrate) {
        try {
            if (database.isConcentrateInDatabase(concentrate)) {
                catalogController.showConcentrateAlreadyExistsMessage();
            } else {
                database.insertConcentrateToDatabase(concentrate);
                database.addConcentrate(concentrate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catalogController.refreshConcentrateTable();
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

    @Override
    public void removeRecipe(int row) {
        Recipe recipe = database.getRecipes().get(row);
        if (recipe.getAuthor().equals(database.getUserName())) {
            try {
                database.removeRecipeFromDatabase(recipe);
                database.removeRecipe(row);
                catalogController.refreshRecipeTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            catalogController.showNotAllowedToRemoveRecipeMessage();
        }
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
            database.getRecipesFromDatabase();

            catalogController.refreshRecipeTable();
            catalogController.refreshConcentrateTable();
        } catch (SQLException e) {
            mainFrame.showDatabaseConnectionErrorDialog();
        }
    }

    ////////

    private void setupLoginDialog() {
        loginDialog = new LoginDialog(mainFrame);
        loginDialog.setListener(this);
        loginDialog.setVisible(true);
    }

    private void onCloseOperations() {
        if (databaseUpdateTimer != null) {
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

    private void scheduleDatabaseUpdates() {
        databaseUpdateTimer = new Timer(true);
        databaseUpdateTimer.scheduleAtFixedRate(this, 0, 600000);
    }
}
