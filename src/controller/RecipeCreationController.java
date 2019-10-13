package controller;

import gui.RecipeCreationPanel;
import gui.SpinnerType;
import model.ConcentrateInRecipe;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeCreationController {
    private RecipeCreationPanel recipeCreationPanel;

    private double volume;
    private double strength;
    private double nicStrength;
    private double totalConcentratePercentage;
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
            int value = (int) sourceSpinner.getValue();

            switch (spinnerType) {
                case volume:
                    volumeChanged(value);
                    break;
                case desiredStrength:
                    desiredStrengthChanged(value);
                    break;
                case desiredGlycol:
                    ratioChanged(value);
                    break;
                case desiredGlycerine:
                    ratioChanged(100 - value);
                    break;
                case nicStrength:
                    nicStrengthChanged(value);
                    break;
                case nicGlycol:
                    nicRatioChanged(value);
                    break;
                case nicGlycerine:
                    nicRatioChanged(100 - value);
                    break;
                case steepTime:
                    steepTimeChanged(value);
            }
            calculateSummary();
        });
    }

    private void setConcentrateTableListener() {
        recipeCreationPanel.setConcentrateTableListener(percentage -> {
            totalConcentratePercentage = percentage;
            calculateSummary();
        });
    }

    private void volumeChanged(int value) {
        this.volume = value;
        recipeCreationPanel.updateVolume(value);
    }

    private void desiredStrengthChanged(int value) {
        this.strength = value;
    }

    private void ratioChanged(int glycol) {
        this.pgVgRatio = glycol;
        recipeCreationPanel.setRatioSpinners(glycol);
    }

    private void nicStrengthChanged(int value) {
        this.nicStrength = value;
    }

    private void nicRatioChanged(int glycol) {
        this.nicPgVgRatio = glycol;
        recipeCreationPanel.setNicRatioSpinners(glycol);

    }

    private void steepTimeChanged(int value) {
    }

    private void calculateSummary() {
        double nicAmount = (volume * strength) / nicStrength;
        System.out.println(nicAmount);
        double glycolFromNic = (nicAmount * nicPgVgRatio) / 100;
        double concentrateVolume = (volume * totalConcentratePercentage) / 100; // concentrate treated as 100% glycol
        double desiredGlycol = (volume * pgVgRatio) / 100;
        //System.out.println(pgVgRatio);
        double glycolToAdd = desiredGlycol - glycolFromNic - concentrateVolume;
        double glycerineToAdd = volume - desiredGlycol;

        setSummaryValues(nicAmount, glycolToAdd, glycerineToAdd);
    }

    private void setSummaryValues(double nicAmount, double glycolToAdd, double glycerineToAdd) {
        String strengthSummary = strength + " mg";
        String ratio = "";
        String concentrateTotal = totalConcentratePercentage + " %";

        String nicAmountSummary = nicAmount + " ml";
        String glycolToAddSummary = glycolToAdd + " ml";
        String glycerineToAddSummary = glycerineToAdd + "ml";

        recipeCreationPanel.setSummaryValues(strengthSummary, ratio, concentrateTotal, nicAmountSummary, glycolToAddSummary, glycerineToAddSummary);
    }

}
