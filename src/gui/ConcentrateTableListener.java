package gui;

import model.ConcentrateInRecipe;

import java.util.ArrayList;

public interface ConcentrateTableListener {
    void percentageChanged(double percentage);
    void concentratesRequested(ArrayList<ConcentrateInRecipe> concentrates);
}
