package dao;

import module.Association;
import module.ClassTable;

import java.sql.*;
import java.util.ArrayList;

public class AssociationDao {

    private static final String INSERT_ASSOCIATION_SQL = "INSERT INTO association (class1, class2) VALUES  (?, ?);";
    private static final String SELECT_All_ASSOCIATION_SQL = "SELECT * FROM association";
    private static final String UPDATE_ASSOCIATION_SQL = "UPDATE association WHERE id=? SET";
    private static final String DELETE_ASSOCIATION_SQL = "DELETE FROM association WHERE id=";

    public int createAssociation(int class1, int class2){
        int associationId = -1;
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ASSOCIATION_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, class1);
            preparedStatement.setInt(2, class2);
            preparedStatement.executeUpdate();
            //System.out.println(preparedStatement);

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows == 0){
                throw new SQLException("Create new association failed");
            }
            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    associationId = generatedKeys.getInt(1);
                    return associationId;
                }else{
                    throw  new SQLException("Creating association failed");
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return associationId;

    }

    public void deleteAssociation(int id){
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ASSOCIATION_SQL+id);
            preparedStatement.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Association> selectAssociations(String sql_statement){
        ArrayList<Association> associations = new ArrayList<>();

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
                int class1 = Integer.parseInt(rs.getString("class1"));
                int class2 = Integer.parseInt(rs.getString("class2"));
                associations.add(new Association(id, class1, class2));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return associations;

    }

    public ArrayList<Association> getAllAssociations (){
        return selectAssociations(SELECT_All_ASSOCIATION_SQL);
    }

    public ArrayList<Association> getRelatedAssociations(int tab_id){
        String select_related_associations = "SELECT * FROM association WHERE class1 ="+tab_id+" OR class2="+tab_id;

        return selectAssociations(select_related_associations);
    }

    public ArrayList<Association> getAssociationsByCube(int cubeId){
        String select_associations_by_cube = "SELECT DISTINCT a.id, a.class1, a.class2 FROM association AS a JOIN class_table AS t ON a.class1 = t.id OR a.class2 = t.id JOIN cube c ON c.id = t.cube_id WHERE c.id ="+cubeId;
        return selectAssociations(select_associations_by_cube);

    }



}
