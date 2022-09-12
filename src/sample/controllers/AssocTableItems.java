package sample.controllers;

public class AssocTableItems {


    private int associationId ;
    private String class1 ;
    private String class2;



   public  AssocTableItems(int associationId, String class1 , String class2) {
        this.associationId = associationId;
        this.class1 = class1;
        this.class2 = class2;

    }
    public void setAssociationId(int associationId) {
        this.associationId = associationId;
    }

    public int getAssociationId() {
        return associationId;
    }
    public String getClass1() {
        return class1;
    }
    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public void setClass2(String class2) {
        this.class2 = class2;
    }


    public String getClass2() {
        return class2;
    }

}
