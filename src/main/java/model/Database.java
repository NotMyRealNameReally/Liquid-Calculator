package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Database {
    private ArrayList<Recipe> recipes;
    private ArrayList<Concentrate> concentrates;
    private ArrayList<String> manufacturers;
    private ArrayList<String> flavourProfiles;
    private Connection connection;

    public Database() {
        recipes = new ArrayList<>();
        concentrates = new ArrayList<>();
        manufacturers = new ArrayList<>();
        flavourProfiles = new ArrayList<>();
    }

    public void connect() throws Exception {
        if (connection != null) return;
        String host = "jdbc:mysql://remotemysql.com:3306/21fy2jgBuZ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("Driver not found");
        }
        try {
            connection = DriverManager.getConnection(host, "21fy2jgBuZ", "SrIhNt0kfM");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void addConcentrate(Concentrate concentrate){
        concentrates.add(concentrate);
        checkForNewFlavourProfile(concentrate);
        checkForNewManufacturer(concentrate);
    }

    public void addManufacturer(String manufacturer){
        manufacturers.add(manufacturer);
    }

    public void addFlavourProfile(String flavourProfile){
        flavourProfiles.add(flavourProfile);
    }

    public void removeRecipe(int index) {
        recipes.remove(index);
    }

    public List<Recipe> getRecipes() {
        return Collections.unmodifiableList(recipes);
        //return recipes;
    }

    public List<String> getflavourProfiles() {
        return Collections.unmodifiableList(flavourProfiles);
    }

    public List<String> getManufacturers() {
        return Collections.unmodifiableList(manufacturers);
    }


    public List<Concentrate> getConcentrates() {
        return Collections.unmodifiableList(concentrates);
    }

    private void checkForNewFlavourProfile(Concentrate concentrate){
        String flavourProfile = concentrate.getFlavourProfile();
        boolean isOnList = false;

        for (String flavourProfileOnList: flavourProfiles){
            if (flavourProfile.equals(flavourProfileOnList)){
                isOnList = true;
                break;
            }
        }
        if (!isOnList){
            flavourProfiles.add(flavourProfile);
        }
    }

    private void checkForNewManufacturer(Concentrate concentrate){
        String manufacturer = concentrate.getManufacturer();
        boolean isOnList = false;

        for (String manufacturerOnList: manufacturers){
            if (manufacturer.equals(manufacturerOnList)){
                isOnList = true;
                break;
            }
        }
        if (!isOnList){
            manufacturers.add(manufacturer);
        }
    }
}
