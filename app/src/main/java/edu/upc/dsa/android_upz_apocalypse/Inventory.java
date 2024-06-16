package edu.upc.dsa.android_upz_apocalypse;

public class Inventory {
    String ID;
    public String idItem;
    public String emailUser;
    public Inventory() {}
    public Inventory(String ID,String idItem, String emailUser)
    {
        this.ID = ID;
        this.idItem = idItem;
        this.emailUser = emailUser;
    }

    public String getID(){return ID;}
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getIdItem()
    {
        return idItem;
    }
    public String getEmailUser()
    {
        return emailUser;
    }
    public void setidItem(String idItem){
        this.idItem = idItem;
    }
    public void setidUser(String idUser)
    {
        this.emailUser = idUser;
    }
}
