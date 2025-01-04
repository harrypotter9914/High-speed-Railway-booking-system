import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HSWService {

    // 添加 HSW
    public void addHSW(HSW hsw) {
        String sql = "INSERT INTO HSW (id, start_station, end_station, mileages, status, start_time, end_time, platforms, business_blocks, first_seats, second_seats) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hsw.getId());
            pstmt.setString(2, hsw.getStartStation());
            pstmt.setString(3, hsw.getEndStation());
            pstmt.setFloat(4, hsw.getMileages());
            pstmt.setString(5, hsw.getStatus());
            pstmt.setString(6, hsw.getStartTime());
            pstmt.setString(7, hsw.getEndTime());
            pstmt.setInt(8, hsw.getPlatforms());
            pstmt.setInt(9, hsw.getBusinessBlocks());
            pstmt.setInt(10, hsw.getFirstSeats());
            pstmt.setInt(11, hsw.getSecondSeats());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTrainNumbers(String startStation, String endStation) throws SQLException {
        ArrayList<String> trainNumbers = new ArrayList<>();
        String sql = "SELECT id FROM HSW WHERE start_station = ? AND end_station = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startStation);
            pstmt.setString(2, endStation);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    trainNumbers.add(rs.getString("id"));
                }
            }
        }

        return trainNumbers;
    }


    // 获取 HSW
    public HSW getHSW(String id) {
        String sql = "SELECT * FROM HSW WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                HSW hsw = new HSW(
                        rs.getString("id"),
                        rs.getString("start_station"),
                        rs.getString("end_station"),
                        rs.getFloat("mileages"),
                        rs.getString("status"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getInt("platforms"),
                        rs.getInt("business_blocks"),
                        rs.getInt("first_seats"),
                        rs.getInt("second_seats"),
                        null // 如果 HSW 包含 Platform 对象数组，您需要另外处理
                );
                return hsw;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateHSW(HSW hsw) {
        String sql = "UPDATE HSW SET start_station = ?, end_station = ?, mileages = ?, status = ?, start_time = ?, end_time = ?, platforms = ?, business_blocks = ?, first_seats = ?, second_seats = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hsw.getStartStation());
            pstmt.setString(2, hsw.getEndStation());
            pstmt.setFloat(3, hsw.getMileages());
            pstmt.setString(4, hsw.getStatus());
            pstmt.setString(5, hsw.getStartTime());
            pstmt.setString(6, hsw.getEndTime());
            pstmt.setInt(7, hsw.getPlatforms());
            pstmt.setInt(8, hsw.getBusinessBlocks());
            pstmt.setInt(9, hsw.getFirstSeats());
            pstmt.setInt(10, hsw.getSecondSeats());
            pstmt.setString(11, hsw.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新 HSW
    public void updateHSWSeatInfo(String hswId, String seatType, int seatsToDeduct) throws SQLException {
        // 确定座位类型并准备相应的 SQL 更新语句
        String seatColumn = getSeatColumnName(seatType);

        String sqlCheckSeats = "SELECT " + seatColumn + " FROM HSW WHERE id = ?";
        String sqlUpdateSeats = "UPDATE HSW SET " + seatColumn + " = " + seatColumn + " - ? WHERE id = ? AND " + seatColumn + " >= ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheckSeats)) {

            // 首先检查是否有足够的座位可供减少
            checkStmt.setString(1, hswId);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next() || rs.getInt(seatColumn) < seatsToDeduct) {
                throw new SQLException("Insufficient seats available or HSW ID not found.");
            }

            // 如果有足够的座位，则更新座位数量
            try (PreparedStatement updateStmt = conn.prepareStatement(sqlUpdateSeats)) {
                updateStmt.setInt(1, seatsToDeduct);
                updateStmt.setString(2, hswId);
                updateStmt.setInt(3, seatsToDeduct);
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Unable to update seats, possibly due to concurrency issues.");
                }
            }
        }
    }

    private String getSeatColumnName(String seatType) {
        switch (seatType.toLowerCase()) {
            case "business":
                return "business_blocks";
            case "first":
                return "first_seats";
            case "second":
                return "second_seats";
            default:
                throw new IllegalArgumentException("Unknown seat type: " + seatType);
        }
    }


    // 删除 HSW
    public void deleteHSW(String id) {
        String sql = "DELETE FROM HSW WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HSW> getAllHSWs() throws SQLException {
        ArrayList<HSW> hswList = new ArrayList<>();
        String sql = "SELECT * FROM HSW";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                HSW hsw = new HSW(
                        rs.getString("id"),
                        rs.getString("start_station"),
                        rs.getString("end_station"),
                        rs.getFloat("mileages"),
                        rs.getString("status"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getInt("platforms"),
                        rs.getInt("business_blocks"),
                        rs.getInt("first_seats"),
                        rs.getInt("second_seats"),
                        null // 如果 HSW 包含 Platform 对象数组，需要另外处理
                );
                hswList.add(hsw);
            }
        }
        return hswList;
    }


    // 检查特定HSW和座位类型的可用座位数量
    public boolean checkAvailability(String hswId, String seatType) throws SQLException {
        // 确定座位类型并准备相应的 SQL 查询语句
        String seatColumn = "";
        switch (seatType.toLowerCase()) {
            case "business":
                seatColumn = "business_blocks";
                break;
            case "first":
                seatColumn = "first_seats";
                break;
            case "second":
                seatColumn = "second_seats";
                break;
            default:
                throw new IllegalArgumentException("Unknown seat type: " + seatType);
        }

        String sql = "SELECT " + seatColumn + " FROM HSW WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hswId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int availableSeats = rs.getInt(seatColumn);
                    return availableSeats > 0; // 如果有可用座位，返回 true
                } else {
                    throw new SQLException("HSW ID not found.");
                }
            }
        }
    }

    public ArrayList<String> getAvailableTimes(String startStation, String endStation) throws SQLException {
        ArrayList<String> times = new ArrayList<>();
        String sql = "SELECT DISTINCT start_time FROM HSW WHERE start_station = ? AND end_station = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startStation);
            pstmt.setString(2, endStation);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                times.add(rs.getString("start_time"));
            }
        }
        return times;
    }

    public ArrayList<String> getEndStationsForStartStation(String startStation) throws SQLException {
        ArrayList<String> endStations = new ArrayList<>();
        String sql = "SELECT DISTINCT end_station FROM HSW WHERE start_station = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startStation);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                endStations.add(rs.getString("end_station"));
            }
        }
        return endStations;
    }

    public ArrayList<String> getAllStartStations() throws SQLException {
        ArrayList<String> startStations = new ArrayList<>();
        String sql = "SELECT DISTINCT start_station FROM HSW";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                startStations.add(rs.getString("start_station"));
            }
        }
        return startStations;
    }

}
