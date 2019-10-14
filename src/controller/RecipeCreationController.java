package controller;

import gui.RecipeCreationPanel;
import gui.SpinnerType;
import model.ConcentrateInRecipe;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecipeCreationController {
    private RecipeCreationPanel recipeCreationPanel;

    private double volume = 10;
    private double desiredStrength = 0;
    private double realStrength;
    private double nicStrength = 18;
    private double totalConcentratePercentage = 0;
    private double desiredPgVgRatio = 50;
    private double realPgVgRatio;
    private double nicPgVgRatio = 50;
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
            double value = (double) sourceSpinner.getValue();

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

    private void volumeChanged(double value) {
        this.volume = value;
        recipeCreationPanel.updateVolume(value);
    }

    private void desiredStrengthChanged(double value) {
        this.desiredStrength = value;
    }

    private void ratioChanged(double glycol) {
        this.desiredPgVgRatio = glycol;
        recipeCreationPanel.setRatioSpinners(glycol);
    }

    private void nicStrengthChanged(double value) {
        this.nicStrength = value;
    }

    private void nicRatioChanged(double glycol) {
        this.nicPgVgRatio = glycol;
        recipeCreationPanel.setNicRatioSpinners(glycol);

    }

    private void steepTimeChanged(double value) {
    }

    private void calculateSummary() {
        realStrength = desiredStrength;
        realPgVgRatio = desiredPgVgRatio;
        double concentrateVolume = (volume * totalConcentratePercentage) / 100; // concentrate treated as 100% glycol
        double nicAmount = (volume * desiredStrength) / nicStrength;

        if (nicAmount >= (volume - concentrateVolume)){
            nicAmount = volume - concentrateVolume;
            realStrength = (nicAmount * nicStrength) / volume;
            double glycolFromNic = (nicAmount * nicPgVgRatio) / 100;
            double totalGlycol = glycolFromNic + concentrateVolume;
            realPgVgRatio = (totalGlycol / volume) * 100;
            setSummaryValues(nicAmount, 0, 0);
        }else {
            double glycolFromNic = (nicAmount * nicPgVgRatio) / 100;
            double glycerineFromNic = (nicAmount * (100 - nicPgVgRatio)) / 100;

            double desiredGlycol = (volume * desiredPgVgRatio) / 100;
            double desiredGlycerine = (volume * (100 - desiredPgVgRatio)) / 100;

            double glycolToAdd = desiredGlycol - glycolFromNic - concentrateVolume;
            double glycerineToAdd = desiredGlycerine - glycerineFromNic;

            if (glycolToAdd < 0){
                glycerineToAdd = volume - nicAmount - concentrateVolume;
                glycolToAdd = 0;
                double totalGlycol = glycolFromNic + concentrateVolume;
                realPgVgRatio = (totalGlycol/volume) * 100;
            }
            if (glycerineToAdd < 0){
                glycolToAdd = volume - nicAmount - concentrateVolume;
                glycerineToAdd = 0;
                double totalGlycol = glycolFromNic + concentrateVolume + glycolToAdd;
                realPgVgRatio = (totalGlycol/volume) * 100;
            }
            setSummaryValues(nicAmount, glycolToAdd, glycerineToAdd);
        }
    }

    private void setSummaryValues(double nicAmount, double glycolToAdd, double glycerineToAdd) {
        DecimalFormat df = new DecimalFormat("###0.0");
        String strengthSummary = df.format(realStrength) + " mg";
        String ratio = df.format(realPgVgRatio) + "/" + df.format(100 - realPgVgRatio);
        String concentrateTotal = df.format(totalConcentratePercentage) + " %";

        String nicAmountSummary = df.format(nicAmount) + " ml";
        String glycolToAddSummary = df.format(glycolToAdd) + " ml";
        String glycerineToAddSummary = df.format(glycerineToAdd) + "ml";

        recipeCreationPanel.setSummaryValues(strengthSummary, ratio, concentrateTotal, nicAmountSummary, glycolToAddSummary, glycerineToAddSummary);
    }

}
