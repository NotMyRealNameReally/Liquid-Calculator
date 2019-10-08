package model;

public class Concentrate {
    private static int count = 1;
    private int id;
    private String name;
    private String manufacturer;
    private String flavourProfile;

    public Concentrate(String name, String manufacturer, String flavourProfile){
        this.name = name;
        this.manufacturer = manufacturer;
        this.flavourProfile = flavourProfile;
        this.id = count++;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getFlavourProfile() {
        return flavourProfile;
    }
}
