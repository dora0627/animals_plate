package models;

public class functionListModel {
    private String Name;
    private int logo;

    public functionListModel(String name,int logo){
        Name = name;
        this.logo = logo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
