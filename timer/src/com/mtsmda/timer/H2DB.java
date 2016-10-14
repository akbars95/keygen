package com.mtsmda.timer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by MTSMDA on 05.10.2016.
 */
public class H2DB {



    public static void main(String[] args) {
        /*try {
            Class.forName("org.h2.Driver");
            System.out.println(H2DB.class.getResource(ICON_NAME).getPath().replace(ICON_NAME, ""));
            Connection con = DriverManager.getConnection("jdbc:h2:" + H2DB.class.getResource(ICON_NAME).getPath().replace(ICON_NAME, "")
                    + "simpleDataBases", "test", "");
            Statement stmt = con.createStatement();
//            stmt.executeUpdate( "DROP TABLE table1" );
            stmt.executeUpdate("CREATE TABLE if not EXISTS table1 ( user varchar(50) )");
            stmt.executeUpdate("INSERT INTO table1 ( user ) VALUES ( 'Claudio' )");
            stmt.executeUpdate("INSERT INTO table1 ( user ) VALUES ( 'Bernasconi' )");

            ResultSet rs = stmt.executeQuery("SELECT * FROM table1");
            while (rs.next()) {
                String name = rs.getString("user");
                System.out.println(name);
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
    }

}