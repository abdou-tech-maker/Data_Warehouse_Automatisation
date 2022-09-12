package dao;

import module.Attribute;
import module.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    public User getUser(String username) {

        String GET_USER_SQL = "SELECT * FROM user WHERE username = ?;";

        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_SQL);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("id");
                String password = rs.getString("password");
                User user = new User(id, username, password);
                return user;
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
