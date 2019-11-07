package model;

public class Concentrate {

    private String name;
    private String manufacturer;
    private String flavourProfile;

    public Concentrate(String name, String manufacturer, String flavourProfile){
        this.name = name;
        this.manufacturer = manufacturer;
        this.flavourProfile = flavourProfile;
    }

    @Override
    public String toString() {
        return manufacturer + "  " + name;
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
