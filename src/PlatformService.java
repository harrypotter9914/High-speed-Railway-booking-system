import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlatformService {

    // 添加 Platform
    public void addPlatform(Platform platform) throws SQLException {
        String sql = "INSERT INTO Platform (name, start_time, end_time, mileages, hsw_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, platform.getName());
            pstmt.setString(2, platform.getStartTime());
            pstmt.setString(3, platform.getEndTime());
            pstmt.setFloat(4, platform.getMileages());
            pstmt.setString(5, "hsw_id"); // 假设是关联的HSW的ID

            pstmt.executeUpdate();
        }
    }

    // 获取 Platform
    public Platform getPlatform(int id) throws SQLException {
        String sql = "SELECT * FROM Platform WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                float mileages = rs.getFloat("mileages");
                String hswId = rs.getString("hsw_id");

                return new Platform(name, startTime, endTime, mileages);
            }
        }
        return null;
    }

    // 更新 Platform
    public void updatePlatform(Platform platform, int id) throws SQLException {
        String sql = "UPDATE Platform SET name = ?, start_time = ?, end_time = ?, mileages = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, platform.getName());
            pstmt.setString(2, platform.getStartTime());
            pstmt.setString(3, platform.getEndTime());
            pstmt.setFloat(4, platform.getMileages());
            pstmt.setInt(5, id);

            pstmt.executeUpdate();
        }
    }

    // 删除 Platform
    public void deletePlatform(int id) throws SQLException {
        String sql = "DELETE FROM Platform WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
