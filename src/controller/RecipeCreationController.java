package controller;

import gui.ConcentrateTableListener;
import gui.RecipeCreationPanel;
import gui.SpinnerType;
import model.ConcentrateInRecipe;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.util.ArrayList;
import java.util.List;

public class RecipeCreationController {
    private RecipeCreationPanel recipeCreationPanel;

    private double volume;
    private double strength;
    private double nicStrength;
    private double totalConcentrateVolume;
    private double pgVgRatio;
    private double nicPgVgRatio;
    private ArrayList<ConcentrateInRecipe> concentrates;

    public RecipeCreationController(RecipeCreationPanel recipeCreationPanel) {
        this.recipeCreationPanel = recipeCreationPanel;
        setRecipePanelListener();
        setConcentrateTableListener();
    }

    private void setRecipePanelListener() {
        recipeCreationPanel.setListener(e -> {
            SpinnerType spinnerType = e.getSpinnerType();
            JSpinner sourceSpinner = (JSpinner) e.getSource();

            switch (spinnerType) {
                case volume:
                    volumeChanged(sourceSpinner);
                    break;
                case desiredStrength:
                    desiredStrengthChanged(sourceSpinner);
                    break;
                case desiredGlycol:
                    int glycol = (int) sourceSpinner.getValue();
                    ratioChanged(glycol);
                    break;
                case desiredGlycerine:
                    int glycerine = (int) sourceSpinner.getValue();
                    ratioChanged(100 - glycerine);
                    break;
                case nicStrength:
                    nicStrengthChanged(sourceSpinner);
                    break;
                case nicGlycol:
                    int nicGlycol = (int) sourceSpinner.getValue();
                    nicRatioChanged(nicGlycol);
                    break;
                case nicGlycerine:
                    int nicGlycerine = (int) sourceSpinner.getValue();
                    nicRatioChanged(100 - nicGlycerine);
                    break;
                case steepTime:
                    steepTimeChanged(sourceSpinner);
            }
        });
    }

    private void setConcentrateTableListener() {
        recipeCreationPanel.setConcentrateTableListener(percentage -> {
            totalConcentrateVolume = percentage;
            System.out.println(percentage);
        });
    }

    private void volumeChanged(JSpinner spinner) {
        recipeCreationPanel.updateVolume((int) spinner.getValue());
    }

    private void desiredStrengthChanged(JSpinner sourceSpinner) {
        this.strength = (int) sourceSpinner.getValue();
    }

    private void ratioChanged(int glycol) {
        this.pgVgRatio = glycol;
        recipeCreationPanel.setRatioSpinners(glycol);
    }

    private void nicStrengthChanged(JSpinner sourceSpinner) {
        this.nicStrength = (int) sourceSpinner.getValue();
    }

    private void nicRatioChanged(int glycol) {
        this.nicPgVgRatio = glycol;
        recipeCreationPanel.setNicRatioSpinners(glycol);

    }

    private void steepTimeChanged(JSpinner sourceSpinner) {
    }


}
