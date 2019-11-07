package model;

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

    public Database() {
        recipes = new ArrayList<>();
        concentrates = new ArrayList<>();
        manufacturers = new ArrayList<>();
        flavourProfiles = new ArrayList<>();
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

    public void pushConcentrateToServer(Concentrate concentrate) throws SQLException {
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

    public void getConcentratesFromServer() throws SQLException{
        concentrates.clear();

        String selectSql = "SELECT name, manufacturer, flavour_profile FROM concentrates ORDER BY manufacturer";
        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        ResultSet results = selectStatement.executeQuery();

        while(results.next()){
            String name = results.getString("name");
            String manufacturer = results.getString("manufacturer");
            String flavourProfile = results.getString("flavour_profile");
            Concentrate concentrate = new Concentrate(name, manufacturer, flavourProfile);
            concentrates.add(concentrate);
        }
        results.close();
        selectStatement.close();
    }

    public void getManufacturersFromServer() throws SQLException{
        manufacturers.clear();

        String selectSql = "SELECT DISTINCT manufacturer FROM concentrates";
        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        ResultSet results = selectStatement.executeQuery();

        while (results.next()){
            String manufacturer = results.getString("manufacturer");
            manufacturers.add(manufacturer);
        }
        results.close();
        selectStatement.close();
    }

    public void getFlavourProfilesFromServer() throws SQLException{
        flavourProfiles.clear();

        String selectSql = "SELECT DISTINCT flavour_profile FROM concentrates";
        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        ResultSet results = selectStatement.executeQuery();

        while (results.next()){
            String flavourProfile = results.getString("flavour_profile");
            flavourProfiles.add(flavourProfile);
        }
        results.close();
        selectStatement.close();
    }
}
