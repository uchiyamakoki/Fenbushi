package com.sn.examplew;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


public class LocalUDBCTransApplication2 {

    private static final Logger LOG= LoggerFactory.getLogger(LocalUDBCTransApplication2.class);

    public static void main(String[] args) throws SQLException {
        Connection connection=getConnection();
        //connection.setAutoCommit(false);

        String query="SELECT * FROM t_user WHERE id=1 FOR UPDATE";
        PreparedStatement ps=connection.prepareStatement(query);
        ResultSet rs=ps.executeQuery();
        Long superManAmount=0L;
        while (rs.next()){
            String username=rs.getString(1);
            Long amount=rs.getLong(2);
            LOG.info("{} has amount:{}",username,amount);
            if(username.equals("SuperMan")){
                superManAmount=amount;
            }
        }

        String sql1="UPDATE t_user SET amount = amount ? WHERE username = ?";
        PreparedStatement ps1=connection.prepareStatement(sql1);

        ps1.setLong(1,superManAmount+100L);
        ps1.setString(2,"SuperMan");
        ps1.executeUpdate();

        //connection.commit();
        ps1.close();
        connection.close();
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
