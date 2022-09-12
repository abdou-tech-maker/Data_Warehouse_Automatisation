package module;

import dao.CubeDao;

public class Cube {
    private int id;
    private String name;
    CubeDao cDao;

    public Cube(int id, String name) {
        this.id = id;
        this.name = name;
        cDao = new CubeDao();
    }

    public void updateName(String name){
        this.cDao.updateCube(this.id, name);
        this.setName(name);
    }

    public String toString(){
        return this.name;
    }

    public int getTableCount(){
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
