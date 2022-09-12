package dao;

import module.ClassTable;
import sample.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassTableDao {

    private static final String INSERT_CLASS_TABLE_SQL = "INSERT INTO class_table (name, table_type, cube_id) VALUES  (?, ?, ?);";
    private static final String SELECT_All_CLASS_TABLES_SQL = "SELECT * FROM class_table";
    private static final String SELECT_FAIT_TABLES_SQL = "SELECT * FROM class_table WHERE table_type = 'fait'";
    private static final String SELECT_CLASS_TABLE_BY_ID_SQL = "SELECT * FROM class_table WHERE id = ";
    private static final String UPDATE_CLASS_TABLE_BY_ID_SQL = "UPDATE class_table WHERE id =? SET name= ? AND table_type = ? ";
    private static final String DELETE_CLASS_TABLE_SQL = "DELETE FROM class_table WHERE id =?";

    public int createClassTable(String className, String type, int cubeId){
        int classTableId = -1;
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLASS_TABLE_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, className);
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, cubeId);
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows == 0){
                throw new SQLException("Create new class_table failed");
            }
            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    System.out.println(generatedKeys);
                     classTableId = generatedKeys.getInt(1);
                    return classTableId;
                }else{
                    throw  new SQLException("Creating class_table failed");
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return classTableId;
    }

    public void updateClassTAble(int id, String className, String type){
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLASS_TABLE_BY_ID_SQL);
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.setString(2, className);
            preparedStatement.setString(3, type);
            preparedStatement.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteClassTable(int id){
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLASS_TABLE_SQL);
            preparedStatement.setString(1, String.valueOf(id));

            preparedStatement.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    // The bas method to select statement for class_table table
    public ArrayList<ClassTable> selectTables(String sql_statement){
        ArrayList<ClassTable> tables = new ArrayList<>();

        // Step 1: Establishing a Connection
        try{
            Connection connection = ConnectionFactory.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(sql_statement);
            //System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String table_type = rs.getString("table_type");
                int cube_id = rs.getInt("cube_id");
                tables.add(new ClassTable(id, name, table_type, cube_id));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return tables;

    }

    public ArrayList<ClassTable> getAllTables(int cube_id) throws SQLException {

        String select_statement = SELECT_All_CLASS_TABLES_SQL + " WHERE cube_id = " + cube_id;
        return selectTables(select_statement);
    }

    public ArrayList<ClassTable> getAllTables() throws SQLException {
       return selectTables(SELECT_All_CLASS_TABLES_SQL);
    }

    // Get the fait tables from a given list of ClassTable
    public ClassTable getFaitTables(ArrayList<ClassTable> tabs){

        /*
        ArrayList<ClassTable> faitTabs = new ArrayList<>();
        //System.out.println(tabs.toString());
        for(ClassTable tab: tabs){
            if(tab.isFaitTable()) faitTabs.add(tab);
        }
        return faitTabs;*/
        int max=1;
        ClassTable faitTable = tabs.get(0);

        for(ClassTable tab:tabs){
            int countAssociations = tab.getRelatedAssociations().size();
            if(countAssociations > max){
                max = countAssociations;
                faitTable = tab;
            }
        }
        return faitTable;
    }


    // Search for a class by id from a given list of ClassTable
    public static ClassTable getTableById(int id, ArrayList<ClassTable> tabs){
        for(ClassTable tab:tabs){
            if(tab.getId() == id) return tab;
        }
        return null;
    }

    public ClassTable getTableById(int id){
        ArrayList<ClassTable> result = selectTables(SELECT_CLASS_TABLE_BY_ID_SQL+id);
        return result.get(0);
    }


    // Get a hash map with (level -> levelTables)
    public HashMap<Integer, ArrayList<ClassTable>> getTablesByLevel(int cube_id) throws SQLException {
        HashMap<Integer, ArrayList<ClassTable>> tableByLevel = new HashMap<>();
        ArrayList<ClassTable> allTabs = getAllTables(cube_id);
        if(allTabs.size() == 0 ) return tableByLevel;

        int currentLevelCount = 1;
        // Set the fait tables as level 1  since they represent the deepest root for all tables
        ArrayList<ClassTable> currentLevelTabs = new ArrayList<>();
        currentLevelTabs.add(getFaitTables(allTabs));
        tableByLevel.put(currentLevelCount, currentLevelTabs);
        int tabsLeftSize = allTabs.size() - currentLevelTabs.size();

        // Delete any table has been set to a given level to prevent duplication
        allTabs.removeAll(currentLevelTabs);

        // While there are tables haven't classified to their appropriate level
        while (tabsLeftSize > 0) {
            ArrayList<ClassTable> nextLevelTabs = new ArrayList<>();

            // Loop through each table of the current level to get the his associated tables(has association with)
            for(ClassTable curLevelTab:currentLevelTabs){
                // Get the ids for all tables associated with a table in the current level
                ArrayList<Integer> relatedTableIds = curLevelTab.getRelatedTableIds();
                for(Integer id:relatedTableIds){
                    ClassTable targetTab = getTableById(id, allTabs);
                    // If the table is not already classified in his level or not exist
                    if(!nextLevelTabs.contains(targetTab) && targetTab != null){
                        //targetTab.setLevel(currentLevelCount+1);
                        nextLevelTabs.add(targetTab);
                        allTabs.remove(targetTab);
                        tabsLeftSize -= 1;
                    }
                }
            }
            // move to the next level
            currentLevelCount += 1;
            currentLevelTabs = nextLevelTabs;
            tableByLevel.put(currentLevelCount, currentLevelTabs);
        }
        return tableByLevel;
    }
}
