package module;

import dao.ClassTableDao;

public class Association {
    private int id;
    private String name;
    private int class1;
    private int class2;
    ClassTableDao tDao = new ClassTableDao();

    public Association(int id,  int class1, int class2) {
        this.id = id;
        this.class1 = class1;
        this.class2 = class2;
    }

    public String getClass1Name(){
        ClassTable t = tDao.getTableById(class1);
        return t.getClassName();
    }

    public String getClass2Name(){
        ClassTable t = tDao.getTableById(class2);
        return t.getClassName();
    }


    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getClass1() {
        return class1;
    }

    public int getClass2() {
        return class2;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClass1(int class1) {
        this.class1 = class1;
    }

    public void setClass2(int class2) {
        this.class2 = class2;
    }
}
