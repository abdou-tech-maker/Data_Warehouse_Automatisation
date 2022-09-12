package module;

import dao.AssociationDao;
import dao.AttributeDao;
import sample.Main;
//import sun.reflect.generics.scope.ClassScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassTable {

    private int id;
    private String className;
    private String classType;
    private ArrayList<Attribute> attributes;
    private int cubeId;
    private int level;
    public int row;
    public int col;

    private AttributeDao attrDao;

    public ClassTable(int id,String className, String classType, int cubeId) {
        this.className = className;
        this.classType = classType;
        this.id = id;
        this.cubeId = cubeId;


        if(classType == "fait"){
            this.level = 1;
        }

        attrDao = new AttributeDao();
        attributes = new ArrayList<>();
    }

    public String toString(){
        return this.className;
    }

    public ClassTable(int id,String className, String classType, ArrayList<Attribute> attrs) {
        this.className = className;
        this.classType = classType;
        this.id = id;
        this.attributes = attrs;

        if(classType == "fait"){
            this.level = 1;
        }
        attrDao = new AttributeDao();
    }



    public void addAttribute(String attributeName, String attributeType ){
        this.attrDao.createAttribute(attributeName, attributeType, this.id);
        this.getRelatedAttributes();
    }

    public ArrayList<Attribute> getRelatedAttributes(){
        return attrDao.getTableAttributes(this.id);
    }

    public boolean isFaitTable(){
        return this.classType.equals("fait");
    }
    // Get all associations of a given table
    public ArrayList<Association> getRelatedAssociations (){
        AssociationDao aDao = new AssociationDao();
        return aDao.getRelatedAssociations(this.id);
    }

    // Get the ids for all tables that associated with a given table
    public ArrayList<Integer> getRelatedTableIds(){
        ArrayList<Association> relatedAssociations = this.getRelatedAssociations();
        ArrayList<Integer> relatedTableIds = new ArrayList<>();

        for(Association a: relatedAssociations){
            if(a.getClass1() == this.getId()){
                relatedTableIds.add(a.getClass2());
            }else{
                relatedTableIds.add(a.getClass1());
            }
        }
        return relatedTableIds;
    }

    // Get the parent table(the low level table it's associated with) for a given table

    public ClassTable getParentTable(ArrayList<ClassTable> tabs){
        ArrayList<Integer> relatedTableIds = this.getRelatedTableIds();
        for(ClassTable tab: tabs){
            if(relatedTableIds.contains(tab.getId())){
                return tab;
            }
        }
        return null;
    }

    public ArrayList<Attribute> getAttrsByType(String attrType){
        ArrayList<Attribute> measures = new ArrayList<>();
        for(Attribute attr:this.attributes){
            if(attr.getType().equals(attrType)){
                measures.add(attr);
            }
        }
        return measures;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public String getClassType() {
        return classType;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public int getLevel() {
        return level;
    }
    public int getCubeId() {
        return cubeId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCubeId(int cubeId) {
        this.cubeId = cubeId;
    }
}
