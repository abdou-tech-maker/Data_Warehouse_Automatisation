package module;

public class Attribute {
    private int id;
    private String name;
    private String type;
    private int tableId;

    public Attribute(int id, String name, String type, int tableId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.tableId = tableId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }





    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }



    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
}
