package model;

import java.util.ArrayList;

public class Calculator {
    private double volume;
    private double desiredStrength;
    private double nicStrength;
    private double totalConcentratePercentage;
    private double desiredPgVgRatio;
    private double nicPgVgRatio;
    private int steepTime;
    private ArrayList<ConcentrateInRecipe> concentrates;

    private double realStrength;
    private double realPgVgRatio;
    private double nicAmount;
    private double concentrateVolume;
    private double glycolToAdd;
    private double glycerineToAdd;

    public Calculator() {

    }

    public Recipe createRecipe(String name) {
        return new Recipe(name, desiredStrength, desiredPgVgRatio, volume, steepTime, concentrates);
    }

    public void addConcentrate(ConcentrateInRecipe concentrate) {
        concentrates.add(concentrate);
    }

    public void removeConcentrate(int row) {
        concentrates.remove(row);
    }

    public void calculateSummary() {
        concentrateVolume = (volume * totalConcentratePercentage) / 100;
        calculateNicAmount();
        performCalculation();
    }

    private void calculateNicAmount() {
        if (nicStrength == 0) {
            nicAmount = 0;
        } else {
            nicAmount = ((volume * desiredStrength) / nicStrength);
        }
        if (nicAmount > (volume - concentrateVolume)) {
            nicAmount = (volume - concentrateVolume);
        }
    }

    private void performCalculation() {
        realStrength = (nicAmount * nicStrength) / volume; // is equal to desiredStrength if nicAmount <= (volume - concentrateVolume)
        realPgVgRatio = desiredPgVgRatio;

        double glycolFromNic = (nicAmount * nicPgVgRatio) / 100;
        double glycerineFromNic = (nicAmount * (100 - nicPgVgRatio)) / 100;

        double desiredGlycol = (volume * desiredPgVgRatio) / 100;
        double desiredGlycerine = (volume * (100 - desiredPgVgRatio)) / 100;

        glycolToAdd = desiredGlycol - glycolFromNic - concentrateVolume;
        glycerineToAdd = desiredGlycerine - glycerineFromNic;

        if (glycolToAdd < 0) {
            calculateRatioForNotEnoughGlycol(glycolFromNic);
        }
        if (glycerineToAdd < 0) {
            calculateRatioForNotEnoughGlycerine(glycolFromNic);
        }
    }

    private void calculateRatioForNotEnoughGlycol(double glycolFromNic) {
        glycolToAdd = 0;
        glycerineToAdd = volume - nicAmount - concentrateVolume;

        double totalGlycol = glycolFromNic + concentrateVolume;
        realPgVgRatio = (totalGlycol / volume) * 100;
    }

    private void calculateRatioForNotEnoughGlycerine(double glycolFromNic) {
        glycerineToAdd = 0;
        glycolToAdd = volume - nicAmount - concentrateVolume;

        double totalGlycol = glycolFromNic + concentrateVolume + glycolToAdd;
        realPgVgRatio = (totalGlycol / volume) * 100;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getDesiredStrength() {
        return desiredStrength;
    }

    public void setDesiredStrength(double desiredStrength) {
        this.desiredStrength = desiredStrength;
    }

    public double getRealStrength() {
        return realStrength;
    }

    public double getNicStrength() {
        return nicStrength;
    }

    public void setNicStrength(double nicStrength) {
        this.nicStrength = nicStrength;
    }

    public double getTotalConcentratePercentage() {
        return totalConcentratePercentage;
    }

    public void setTotalConcentratePercentage(double totalConcentratePercentage) {
        this.totalConcentratePercentage = totalConcentratePercentage;
    }

    public double getDesiredPgVgRatio() {
        return desiredPgVgRatio;
    }

    public void setDesiredPgVgRatio(double desiredPgVgRatio) {
        this.desiredPgVgRatio = desiredPgVgRatio;
    }

    public double getRealPgVgRatio() {
        return realPgVgRatio;
    }

    public double getNicPgVgRatio() {
        return nicPgVgRatio;
    }

    public void setNicPgVgRatio(double nicPgVgRatio) {
        this.nicPgVgRatio = nicPgVgRatio;
    }

    public int getSteepTime() {
        return steepTime;
    }

    public void setSteepTime(int steepTime) {
        this.steepTime = steepTime;
    }

    public double getNicAmount() {
        return nicAmount;
    }

    public double getConcentrateVolume() {
        return concentrateVolume;
    }

    public double getGlycolToAdd() {
        return glycolToAdd;
    }

    public double getGlycerineToAdd() {
        return glycerineToAdd;
    }

    public void setConcentrates(ArrayList<ConcentrateInRecipe> concentrates) {
        this.concentrates = concentrates;
    }
}
