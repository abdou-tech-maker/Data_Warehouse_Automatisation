package dao;

import module.Attribute;
import module.ClassTable;

import java.sql.*;
import java.util.ArrayList;

public class AttributeDao {
    private static final String INSERT_ATTRIBUTE_SQL = "INSERT INTO attribute (name, type, table_id) VALUES  (?, ?, ?);";
    private static final String SELECT_All_ATTRIBUTES_SQL = "SELECT * FROM attribute";
    private static final String DELETE_ATTRIBUTE_SQL = "DELETE FROM attribute WHERE id =?";




    // The bas method to select statement for attribute table
    public ArrayList<Attribute> selectAttributes(String sql_statement){
        ArrayList<Attribute> attrs = new ArrayList<>();

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
                String type = rs.getString("type");
                int table_id = Integer.parseInt(rs.getString("table_id"));
                attrs.add(new Attribute(id, name, type, table_id));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return attrs;

    }

    public ArrayList<Attribute> getAllAttributes(){
        return this.selectAttributes(SELECT_All_ATTRIBUTES_SQL);
    }

    public ArrayList<Attribute> getTableAttributes(int table_id){
        return this.selectAttributes(SELECT_All_ATTRIBUTES_SQL+" WHERE table_id = "+table_id);
    }

    public Attribute getAttributeById(int id){
        ArrayList<Attribute> l =  this.selectAttributes(SELECT_All_ATTRIBUTES_SQL+" WHERE id = "+id);
        if (l.size()>0) return l.get(0);
        return null;
    }

    public int createAttribute(String name, String type, int table_id){
        int attributeId = -1;
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ATTRIBUTE_SQL,  Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, table_id);

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows == 0){
                throw new SQLException("Create new class_table failed");
            }
            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    System.out.println(generatedKeys);
                    attributeId = generatedKeys.getInt(1);
                    return attributeId;
                }else{
                    throw  new SQLException("Creating class_table failed");
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return attributeId;
    }

    public void deleteAttribute(int id){
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ATTRIBUTE_SQL);
            preparedStatement.setString(1, String.valueOf(id));

            preparedStatement.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
