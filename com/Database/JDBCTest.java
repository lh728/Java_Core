package com_second.DataBase;


import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.HashMap;


public class JDBCTest {
    //JDBC驱动程序名称和数据库URL
//    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    // 数据库凭据
    static final String USER = "postgres";
    static final String PASS = "425658167";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            //步骤2: 注册 JDBC 驱动程序
//            Class.forName("com.mysql.jdbc.Driver");

            //步骤3：打开连接
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //步骤4：执行查询
            System.out.println("Creating statement...");
            String sql = "UPDATE Employees set age=? WHERE id=?";
            stmt = conn.prepareStatement(sql);

            //将值绑定到参数中。
            stmt.setInt(1, 35);  // set age
            stmt.setInt(2, 102); // set ID

            //让我们用ID=102更新记录的年龄；
            int rows = stmt.executeUpdate();
            System.out.println("Rows impacted : " + rows );

            //让我们选择所有的记录并显示它们。
            sql = "SELECT * FROM Employees";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            //步骤5：从结果集中提取数据
            while(rs.next()){
                //按列名检索
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            //第六步：清理环境
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //处理JDBC的错误
            se.printStackTrace();
        }catch(Exception e){
            //处理 Class.forName 的错误
            e.printStackTrace();
        }finally{
            //用于关闭资源的finally块
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end JDBCExample

