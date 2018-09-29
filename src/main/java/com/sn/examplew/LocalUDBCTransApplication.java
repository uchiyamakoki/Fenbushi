package com.sn.examplew;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


public class LocalUDBCTransApplication {

    private static final Logger LOG= LoggerFactory.getLogger(LocalUDBCTransApplication.class);

    public static void main(String[] args) throws SQLException {
        Connection connection=getConnection();
        connection.setAutoCommit(false);

        String sql1="UPDATE t_user SET amount = amount + 100 WHERE username = ?";
        PreparedStatement ps1=connection.prepareStatement(sql1);

       // simulateERROR();

        String sql2="UPDATE t_user SET amount = amount - 100 WHERE username = ?";
        PreparedStatement ps2=connection.prepareStatement(sql2);

        ps1.setString(1,"SuperMan");
        ps1.executeUpdate();

        ps2.setString(1,"BatMan");
        ps2.executeUpdate();

        connection.commit();
        ps1.close();
        ps2.close();
        connection.close();
    }
    private static void simulateERROR() throws SQLException {
        throw new SQLException("Some error");
    }

    private static Connection getConnection() throws SQLException {
        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/dist_tran_course";
        String username="root";
        String password="1234";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return DriverManager.getConnection(url,username,password);
    }
}
