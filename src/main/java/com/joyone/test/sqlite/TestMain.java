package com.joyone.test.sqlite;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestMain {

    public static void main(String[] args) {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite://D:\\sqlite\\db\\fanyo.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }

        long start=System.currentTimeMillis();
        //createTable(c);
        //insertData(c, 10000);
        //selectOne(c,7994);
        deleteAllData(c);
        long end=System.currentTimeMillis();
        System.out.println("result=="+(end-start));

        System.out.println("Opened database successfully");
    }


    public static void createTable(Connection c) {
        try {
            Statement stmt = null;
            stmt = c.createStatement();
            String sql = "CREATE TABLE COMPANY " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " ADDRESS        CHAR(50), " +
                    " SALARY         REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void insertData(Connection c, int count) {
        try {
            Statement stmt = null;
            stmt = c.createStatement();
            for (int i = 0; i <= count; i++) {
                String sql = getInsertSql(i);
                stmt.executeUpdate(sql);
            }
            stmt.close();
            // c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void selectOne(Connection c, int index){
        try {
            Statement stmt = null;
            stmt = c.createStatement();
            String sql = "SELECT * FROM COMPANY WHERE ID = "+index+";";
            ResultSet rs = stmt.executeQuery(sql);
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                String  address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println( "ADDRESS = " + address );
                System.out.println( "SALARY = " + salary );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void deleteAllData(Connection c) {
        try {
            Statement stmt = null;
            stmt = c.createStatement();
            String sql = "DELETE from COMPANY;";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static String getInsertSql(int i) {
        String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                "VALUES (" + i + ", 'Paul" + i + "', 32, 'California', 20000.00 );";
        return sql;
    }
}
