package kr.or.ddit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * JDBC 드라이버를 로딩하고 DB에 접속하여 Connection 객체를 반환하는 메서드
 * 반환하는 메서드로 구성된 class 만들기
 * (ResourceBundle객에 이용하기)
 */
public class JDBCUtil3 {
    private static ResourceBundle bundle;

    // 정적초기화 블록
    static{
        // 상대경로 : src 하위 경로에서 dbinfo가 있는 경로까지 pakage 경로 작성하면 됨
        // 파일명 : dbinfo
        bundle = ResourceBundle.getBundle("res.config.dbinfo");
        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException e) {
            System.out.println("OracleDriver 로딩 실패");
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(bundle.getString("url"),
                    bundle.getString("user"),
                    bundle.getString("pass"));
        } catch (SQLException e) {
            System.out.println("DB Connection 실패");
            e.printStackTrace();
        }
        return conn;
    }


}
