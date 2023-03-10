package com_second.DataBase;

import java.sql.Connection;
import java.sql.*;

public class JDBCTest2 {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    //  数据库凭证
    static final String USER = "postgres";
    static final String PASS = "425658167";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //步骤2：注册JDBC驱动程序
//            Class.forName("com.mysql.jdbc.Driver");

            //步骤3：建立连接
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //步骤4：执行查询以创建陈述
            // RS示例的必需参数。
            System.out.println("Creating statement...");
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String sql;
            sql = "SELECT id, first, last, age FROM Employees";
            ResultSet rs = stmt.executeQuery(sql);

            // 将光标移到最后一行。
            System.out.println("Moving cursor to the last...");
            rs.last();

            //步骤5：从结果集中提取数据
            System.out.println("Displaying record...");
            //按列名检索
            int id  = rs.getInt("id");
            int age = rs.getInt("age");
            String first = rs.getString("first");
            String last = rs.getString("last");

            //显示值
            System.out.print("ID: " + id);
            System.out.print(", Age: " + age);
            System.out.print(", First: " + first);
            System.out.println(", Last: " + last);

            // 将光标移到第一行。
            System.out.println("Moving cursor to the first row...");
            rs.first();

            //步骤6：从结果集中提取数据
            System.out.println("Displaying record...");
            //按列名检索
            id  = rs.getInt("id");
            age = rs.getInt("age");
            first = rs.getString("first");
            last = rs.getString("last");

            //显示值
            System.out.print("ID: " + id);
            System.out.print(", Age: " + age);
            System.out.print(", First: " + first);
            System.out.println(", Last: " + last);
            // 将光标移到下一行。

            System.out.println("Moving cursor to the next row...");
            rs.next();

            //步骤7：从结果集中提取数据
            System.out.println("Displaying record...");
            id  = rs.getInt("id");
            age = rs.getInt("age");
            first = rs.getString("first");
            last = rs.getString("last");

            //显示值
            System.out.print("ID: " + id);
            System.out.print(", Age: " + age);
            System.out.print(", First: " + first);
            System.out.println(", Last: " + last);

            //步骤8：清理环境
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //处理JDBC错误
            se.printStackTrace();
        }catch(Exception e){
            //处理Class.forName的错误
            e.printStackTrace();
        }finally{
            //用于关闭资源
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){

            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}//结束JDBCExample

