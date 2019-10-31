package controller;

import model.Concentrate;

public interface CatalogControllerInterface {

    void concentrateCreated(Concentrate concentrate);
    void concentrateChosen(int row);
    void loadRecipe(int row);
}
