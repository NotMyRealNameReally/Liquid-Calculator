package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Database {
    private ArrayList<Recipe> recipes;
    private ArrayList<Concentrate> concentrates;
    private ArrayList<String> manufacturers;
    private ArrayList<String> flavourProfiles;
    private Connection connection;
    private String userName = "Konon";
    private Type concentrateInRecipeListType;

    public Database() {
        recipes = new ArrayList<>();
        concentrates = new ArrayList<>();
        manufacturers = new ArrayList<>();
        flavourProfiles = new ArrayList<>();
        setConcentrateInRecipeListType();
    }

    private void setConcentrateInRecipeListType(){
        concentrateInRecipeListType = new TypeToken<ArrayList<ConcentrateInRecipe>>(){}.getType();
    }

    public void connect() throws SQLException {
        if (connection != null) return;
        String host = "jdbc:mysql://remotemysql.com:3306/21fy2jgBuZ";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(host, "21fy2jgBuZ", "KAjeAfw2T6");
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void addConcentrate(Concentrate concentrate) {
        concentrates.add(concentrate);
        checkForNewFlavourProfile(concentrate);
        checkForNewManufacturer(concentrate);
    }

    public void addManufacturer(String manufacturer) {
        manufacturers.add(manufacturer);
    }

    public void addFlavourProfile(String flavourProfile) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private void checkForNewFlavourProfile(Concentrate concentrate) {
        String flavourProfile = concentrate.getFlavourProfile();
        boolean isOnList = false;

        for (String flavourProfileOnList : flavourProfiles) {
            if (flavourProfile.equals(flavourProfileOnList)) {
                isOnList = true;
                break;
            }
        }
        if (!isOnList) {
            flavourProfiles.add(flavourProfile);
        }
    }

    private void checkForNewManufacturer(Concentrate concentrate) {
        String manufacturer = concentrate.getManufacturer();
        boolean isOnList = false;

        for (String manufacturerOnList : manufacturers) {
            if (manufacturer.equals(manufacturerOnList)) {
                isOnList = true;
                break;
            }
        }
        if (!isOnList) {
            manufacturers.add(manufacturer);
        }
    }

    public void insertConcentrateToDatabase(Concentrate concentrate) throws SQLException {
        String insertSql = "INSERT INTO concentrates(name, manufacturer, flavour_profile) VALUES (?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);

        String name = concentrate.getName();
        String manufacturer = concentrate.getManufacturer();
        String flavourProfile = concentrate.getFlavourProfile();

        insertStatement.setString(1, name);
        insertStatement.setString(2, manufacturer);
        insertStatement.setString(3, flavourProfile);
        insertStatement.executeUpdate();
        insertStatement.close();
    }

    public boolean isConcentrateInDatabase(Concentrate concentrate) throws SQLException {
        String checkSql = "SELECT id FROM concentrates WHERE name = ? AND manufacturer = ?";

        PreparedStatement checkStatement = connection.prepareStatement(checkSql);
        checkStatement.setString(1, concentrate.getName());
        checkStatement.setString(2, concentrate.getManufacturer());
        ResultSet checkResult = checkStatement.executeQuery();

        boolean isIn = checkResult.next();
        checkResult.close();
        checkStatement.close();
        return isIn;
    }

    public void getConcentratesFromServer() throws SQLException {
        concentrates.clear();

        String selectSql = "SELECT name, manufacturer, flavour_profile FROM concentrates ORDER BY manufacturer";
        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        ResultSet results = selectStatement.executeQuery();

        while (results.next()) {
            String name = results.getString("name");
            String manufacturer = results.getString("manufacturer");
            String flavourProfile = results.getString("flavour_profile");
            Concentrate concentrate = new Concentrate(name, manufacturer, flavourProfile);
            concentrates.add(concentrate);
        }
        results.close();
        selectStatement.close();
    }

    public void getManufacturersFromServer() throws SQLException {
        manufacturers.clear();

        String selectSql = "SELECT DISTINCT manufacturer FROM concentrates";
        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        ResultSet results = selectStatement.executeQuery();

        while (results.next()) {
            String manufacturer = results.getString("manufacturer");
            manufacturers.add(manufacturer);
        }
        results.close();
        selectStatement.close();
    }

    public void getFlavourProfilesFromServer() throws SQLException {
        flavourProfiles.clear();

        String selectSql = "SELECT DISTINCT flavour_profile FROM concentrates";
        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        ResultSet results = selectStatement.executeQuery();

        while (results.next()) {
            String flavourProfile = results.getString("flavour_profile");
            flavourProfiles.add(flavourProfile);
        }
        results.close();
        selectStatement.close();
    }

    public boolean isRecipeInDatabase(Recipe recipe) throws SQLException {
        String checkSql = "SELECT id FROM recipes WHERE name = ? AND author = ?";

        PreparedStatement checkStatement = connection.prepareStatement(checkSql);
        checkStatement.setString(1, recipe.getName());
        checkStatement.setString(2, recipe.getAuthor());
        ResultSet checkResult = checkStatement.executeQuery();

        boolean isIn = checkResult.next();
        checkResult.close();
        checkStatement.close();
        return isIn;
    }

    public void insertRecipeToDatabase(Recipe recipe)throws SQLException{
        String insertSql = "INSERT INTO recipes (name, author, strength, pg_vg_ratio, volume, steep_time, concentrates) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);

        String name = recipe.getName();
        String author = recipe.getAuthor();
        double strength = recipe.getStrength();
        double pgVgRatio = recipe.getPgVgRatio();
        double volume = recipe.getVolume();
        int steepTime = recipe.getSteepTime();
        String concentratesJson = new Gson().toJson(recipe.getConcentrates(), concentrateInRecipeListType);

        insertStatement.setString(1, name);
        insertStatement.setString(2, author);
        insertStatement.setDouble(3, strength);
        insertStatement.setDouble(4, pgVgRatio);
        insertStatement.setDouble(5, volume);
        insertStatement.setInt(6, steepTime);
        insertStatement.setString(7, concentratesJson);

        insertStatement.executeUpdate();
        insertStatement.close();

    }

    public void getRecipesFromDatabase() throws SQLException{
        recipes.clear();

        String selectSql = "SELECT name, author, strength, pg_vg_ratio, volume, steep_time, concentrates FROM recipes ORDER BY author";
        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        ResultSet results = selectStatement.executeQuery();

        while (results.next()){
            String name = results.getString("name");
            String author = results.getString("author");
            double strength = results.getDouble("strength");
            double pgVgRatio = results.getDouble("pg_vg_ratio");
            double volume = results.getDouble("volume");
            int steepTime = results.getInt("steep_time");
            String concentratesJson = results.getString("concentrates");
            ArrayList<ConcentrateInRecipe> concentrates = new Gson().fromJson(concentratesJson, concentrateInRecipeListType);

            Recipe recipe = new Recipe(name, strength, pgVgRatio, volume, steepTime, concentrates);
            recipe.setAuthor(author);
            recipes.add(recipe);
        }
        results.close();
        selectStatement.close();
    }

    public void updateRecipeInDatabase(Recipe recipe) throws SQLException{
        String updateSql = "UPDATE recipes set strength = ?, pg_vg_ratio = ?, volume = ?, steep_time = ?, concentrates = ? WHERE name = ? AND author = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateSql);
        double strength = recipe.getStrength();
        double pgVGRatio = recipe.getPgVgRatio();
        double volume = recipe.getVolume();
        int steepTime = recipe.getSteepTime();
        String concentratesJson = new Gson().toJson(recipe.getConcentrates(), concentrateInRecipeListType);

        updateStatement.setDouble(1, strength);
        updateStatement.setDouble(2, pgVGRatio);
        updateStatement.setDouble(3, volume);
        updateStatement.setInt(4, steepTime);
        updateStatement.setString(5, concentratesJson);

        updateStatement.executeUpdate();
        updateStatement.close();
    }
}
