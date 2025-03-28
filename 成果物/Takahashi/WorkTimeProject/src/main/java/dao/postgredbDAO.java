package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.WorkTimeBean;

public class postgredbDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgredb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0501";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // SELECT
    public List<WorkTimeBean> select(String attenddate) {
        List<WorkTimeBean> list = new ArrayList<>();
        String sql = "SELECT * FROM attendtime WHERE attenddate = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(attenddate));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new WorkTimeBean(rs.getDate("attenddate"), rs.getTime("starttime"), rs.getTime("endtime")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // INSERT
    public boolean insert(WorkTimeBean attendtime) {
        String sql = "INSERT INTO attendtime (attenddate, starttime, endtime) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, attendtime.getAttenddate());
            pstmt.setTime(2, attendtime.getStarttime());
            pstmt.setTime(3, attendtime.getEndtime());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // UPDATE
    public boolean update(WorkTimeBean attendtime) {
        String sql = "UPDATE attendtime SET starttime = ?, endtime = ? WHERE attenddate = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTime(1, attendtime.getStarttime());
            pstmt.setTime(2, attendtime.getEndtime());
            pstmt.setDate(3, attendtime.getAttenddate());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // DELETE
    public boolean delete(String attenddate) {
        String sql = "DELETE FROM attendtime WHERE attenddate = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(attenddate));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
