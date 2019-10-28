package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Database{
    private ArrayList<Recipe> recipes;
    private ArrayList<Concentrate> concentrates;
    private Connection connection;

    public Database(){
        recipes = new ArrayList<>();
    }
    public void addRecipe(Recipe recipe){
        recipes.add(recipe);
    }
    public void removeRecipe(int index){
        recipes.remove(index);
    }
    public List<Recipe> getRecipes(){
        return Collections.unmodifiableList(recipes);
    }
}
