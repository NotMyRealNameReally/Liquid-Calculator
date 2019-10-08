package model;

import java.util.ArrayList;

public class Recipe {
    private static int count = 1;
    private int id;
    private String author;
    private String name;
    private String strength;
    private String glycolContent;
    private String glycerineContent;
    private ArrayList<ConcentrateInRecipe> concentrates;

    public Recipe(String author, String name, String strength, String glycolContent, String glycerineContent){
        this.author = author;
        this.name = name;
        this.strength = strength;
        this. glycolContent = glycolContent;
        this.glycerineContent = glycerineContent;
        this.id = count++;
    }
}
