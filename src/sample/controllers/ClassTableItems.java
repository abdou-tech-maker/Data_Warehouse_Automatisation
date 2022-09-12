package sample.controllers;

public class ClassTableItems {
    private String className ;
    private String attributeName ;
    private String type ;
    private  String datatype ;


    public ClassTableItems(String className, String attributeName, String type, String datatype) {
        this.className = className;
        this.attributeName = attributeName;
        this.type = type;
        this.datatype = datatype;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getClassName() {
        return className;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getType() {
        return type;
    }

    public String getDatatype() {
        return datatype;
    }
}
