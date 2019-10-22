package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    private static int count = 1;
    private int id;
    private String author;
    private String name;
    private double strength;
    private double pgVgRatio;
    private double volume;
    private int steepTime;
    private ArrayList<ConcentrateInRecipe> concentrates;

    public Recipe(String name, double strength, double pgVgRatio, double volume, int steepTime) {
        this.name = name;
        this.strength = strength;
        this.pgVgRatio = pgVgRatio;
        this.steepTime = steepTime;
        this.volume = volume;
        this.id = count++;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Recipe.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getPgVgRatio() {
        return pgVgRatio;
    }

    public void setPgVgRatio(double pgVgRatio) {
        this.pgVgRatio = pgVgRatio;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getSteepTime() {
        return steepTime;
    }

    public void setSteepTime(int steepTime) {
        this.steepTime = steepTime;
    }

    public ArrayList<ConcentrateInRecipe> getConcentrates() {
        return concentrates;
    }

    public void setConcentrates(ArrayList<ConcentrateInRecipe> concentrates) {
        this.concentrates = concentrates;
    }
}
