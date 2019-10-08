package model;

public class ConcentrateInRecipe{
   private Concentrate concentrate;

   private double percentage;

   public ConcentrateInRecipe(Concentrate concentrate, double percentage){
       this.concentrate = concentrate;
       this.percentage = percentage;
   }
    public ConcentrateInRecipe(Concentrate concentrate){
       this.concentrate = concentrate;
    }

    public Concentrate getConcentrate() {
        return concentrate;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage){
       this.percentage = percentage;
    }
}
