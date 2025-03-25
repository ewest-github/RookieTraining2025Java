package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import Bean.WorkingHoursBean;

public class AttendtimeDao {

    private static final String URL = "jdbc:postgresql://localhost:5432/attendance_system";
    private static final String USER = "postgres";
    private static final String PASSWORD = "24309692";

    private Connection connection;

    // DB接続用メソッド（内部使用）
    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) 
        try{
        	Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("データベースに接続しました。");
        }catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBCドライバーが見つかりません！");
            e.printStackTrace();
         }catch(Exception e) {
        	 System.out.println("データベース接続に失敗しました: " + e.getMessage());
        }
    }

    // DB切断用メソッド（内部使用）
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("データベース接続を切断しました。");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // SELECTメソッド: 勤怠データを取得
    public WorkingHoursBean select() {
        String sql = "SELECT * FROM attendtime";
        WorkingHoursBean workingHours = new WorkingHoursBean();

        try {
            connect(); // 接続
            try (PreparedStatement psmt = connection.prepareStatement(sql);
                 ResultSet rs = psmt.executeQuery()) {

                if (rs.next()) {
                    workingHours.setTotalHours(rs.getDouble("totalHours"));
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect(); // 切断
        }

        return workingHours;
    }

    // INSERTメソッド: 勤怠データを挿入
    public void insert(Date attendDate, Time startTime, Time endTime) {
        String query = "INSERT INTO attendtime (attendDate, startTime, endTime) VALUES (?, ?, ?)";

        try {
            connect(); // 接続
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setDate(1, attendDate);
                stmt.setTime(2, startTime);
                stmt.setTime(3, endTime);
                stmt.executeUpdate();
                System.out.println("データが正常に挿入されました。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect(); // 切断
        }
    }

    // UPDATEメソッド: 勤怠データを更新
    public void update(Date attendDate, Time startTime, Time endTime) {
        String query = "UPDATE attendtime SET startTime = ?, endTime = ? WHERE attendDate = ?";

        try {
            connect(); // 接続
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setTime(1, startTime);
                stmt.setTime(2, endTime);
                stmt.setDate(3, attendDate);
                stmt.executeUpdate();
                System.out.println("データが正常に更新されました。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect(); // 切断
        }
    }

    // DELETEメソッド: 勤怠データを削除
    public void delete(Date attendDate) {
        String query = "DELETE FROM attendtime WHERE attendDate = ?";

        try {
            connect(); // 接続
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setDate(1, attendDate);
                stmt.executeUpdate();
                System.out.println("データが正常に削除されました。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect(); // 切断
        }
    }
}
