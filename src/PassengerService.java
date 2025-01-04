import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassengerService {

    // 添加Passenger
    public void addPassenger(Passenger passenger) {
        String sql = "INSERT INTO Passenger (id_card, name, paper_type, self_phone, emergency_contacter, emergency_contacter_phone) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, passenger.getIdCard());
            pstmt.setString(2, passenger.getName());
            pstmt.setString(3, passenger.getPaperType());
            pstmt.setString(4, passenger.getSelfPhone());
            pstmt.setString(5, passenger.getEmergencyContacter());
            pstmt.setString(6, passenger.getEmergencyContacterPhone());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 获取Passenger
    public Passenger getPassenger(String idCard) {
        String sql = "SELECT * FROM Passenger WHERE id_card = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idCard);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String paperType = rs.getString("paper_type");
                    String selfPhone = rs.getString("self_phone");
                    String emergencyContacter = rs.getString("emergency_contacter");
                    String emergencyContacterPhone = rs.getString("emergency_contacter_phone");

                    return new Passenger(idCard, name, paperType, selfPhone, emergencyContacter, emergencyContacterPhone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 更新Passenger
    public void updatePassenger(Passenger passenger) {
        String sql = "UPDATE Passenger SET name = ?, paper_type = ?, self_phone = ?, emergency_contacter = ?, emergency_contacter_phone = ? WHERE id_card = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, passenger.getName());
            pstmt.setString(2, passenger.getPaperType());
            pstmt.setString(3, passenger.getSelfPhone());
            pstmt.setString(4, passenger.getEmergencyContacter());
            pstmt.setString(5, passenger.getEmergencyContacterPhone());
            pstmt.setString(6, passenger.getIdCard());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除Passenger
    public void deletePassenger(String idCard) {
        String sql = "DELETE FROM Passenger WHERE id_card = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idCard);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Passenger> getAllPassengers() {
        ArrayList<Passenger> passengers = new ArrayList<>();
        String sql = "SELECT * FROM Passenger";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String idCard = rs.getString("id_card");
                String name = rs.getString("name");
                String paperType = rs.getString("paper_type");
                String selfPhone = rs.getString("self_phone");
                String emergencyContacter = rs.getString("emergency_contacter");
                String emergencyContacterPhone = rs.getString("emergency_contacter_phone");

                Passenger passenger = new Passenger(idCard, name, paperType, selfPhone, emergencyContacter, emergencyContacterPhone);
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengers;
    }

    public void addOrUpdatePassenger(Passenger passenger) throws SQLException {
        if (getPassenger(passenger.getIdCard()) != null) {
            updatePassenger(passenger);
        } else {
            addPassenger(passenger);
        }
    }

}
