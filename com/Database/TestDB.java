package com_second.DataBase;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

/**
 * Want to test this program needs to start database (whatever MtSQL postgreSQL)
 * then run : java -classpath .;driverJAR test.TestDB
 */

public class TestDB {
    public static void main(String[] args) throws IOException {
        try{
            runTest();
        }catch (SQLException e){
            for (Throwable t:e)
                t.printStackTrace();
        }
    }


    public static void runTest() throws SQLException, IOException {
        try (Connection conn = getConnection();
        Statement stat = conn.createStatement()){
            stat.executeUpdate("CREATE TABLE  GREETING (MESSAGE CHAR(20))");
            stat.executeUpdate("INSERT INTO GREETING VALUES ('hHELLO,WORLD!')");

            try (ResultSet result = stat.executeQuery("SELECT * FROM GREETINGS")){
                if (result.next()){
                    System.out.println(result.getString(1));
                }
            }
            stat.executeUpdate("DROP TABLE GREETINGS");
        }
    }

    public static Connection getConnection() throws IOException, SQLException {
        var props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        }
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) System.setProperty("jdbc.drivers",drivers);
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url,username,password);
    }

}
