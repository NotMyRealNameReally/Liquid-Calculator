package model;

public class TestDatabase {
    public static void main(String[] args){
        Database db = new Database();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("connected");
        Concentrate concentrate = new Concentrate("Malina Koncentrat", "TPA", "Malina");
        db.addConcentrate(concentrate);
        System.out.println("dodano koncentrat");
    }
}
