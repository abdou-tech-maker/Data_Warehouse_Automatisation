package dao;

import module.Attribute;
import module.Cube;

import java.sql.*;
import java.util.ArrayList;

public class CubeDao {
    private static final String SELECT_All_CUBES_SQL = "SELECT * FROM cube";
    private  static final String INSERT_CUBE_SQL = "INSERT INTO cube (name) VALUES (?)";
    private  static final String UPDATE_CUBE_SQL = "UPDATE cube SET name=? WHERE  id=? ";
    private  static final String DELETE_CUBE_SQL = "DELETE FROM cube WHERE  id=?";

    public int createCube(String cubeName){
        int cubeId = -1 ;
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUBE_SQL,  Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cubeName);

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows == 0){
                throw new SQLException("Create new cube failed");
            }
            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    cubeId = generatedKeys.getInt(1);
                    return cubeId;
                }else{
                    throw  new SQLException("Creating cube failed");
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return cubeId;
    }


    public void updateCube(int cubeId, String cubeName){
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUBE_SQL);
            preparedStatement.setString(1, cubeName);
            preparedStatement.setInt(2, cubeId);
            preparedStatement.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCube(int cubeId){
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CUBE_SQL);
            preparedStatement.setInt(1, cubeId);
            preparedStatement.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    // The bas method to select statement for attribute table
    public ArrayList<Cube> selectCubes(String sql_statement){
        ArrayList<Cube> attrs = new ArrayList<>();

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
                attrs.add(new Cube(id, name));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return attrs;

    }

    public int countRelatedTables(String sql_statement){
        int count = 0;
        try{
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_statement);
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                count = rs.getInt("count");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return count;

    }

    public ArrayList<Cube> getAllCubes(){
        return selectCubes(SELECT_All_CUBES_SQL);
    }
    public int countCubeTables(int cube_id){
        String COUNT_CUBE_TABLES = "SELECT COUNT(*) as count FROM class_table where cube_id =";
        return countRelatedTables(COUNT_CUBE_TABLES+cube_id);
    }

    public int countCubeAssociations(int cube_id){
        String COUNT_CUBE_ASSOCIATIONS = "SELECT count(*) as count FROM association AS a JOIN class_table AS t ON a.class1 = t.id AND t.cube_id =";
        return countRelatedTables(COUNT_CUBE_ASSOCIATIONS+cube_id);
    }
}
