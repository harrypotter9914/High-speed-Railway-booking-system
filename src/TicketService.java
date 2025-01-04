import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketService {

    private HSWService hswService;

    public TicketService() {
        this.hswService = new HSWService();
    }

    // 添加Ticket
    public void addTicket(Ticket ticket) throws SQLException {
        if (!isSeatAvailable(ticket.getCarID(), ticket.getDate(), ticket.getSeatNo(), ticket.getSeatType())) {
            throw new SQLException("The seat is already booked.");
        }

        String sql = "INSERT INTO Ticket (id, date, car_id, start_station, end_station, paper_type, id_card, name, start_time, end_time, seat_type, seat_no, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticket.getId());
            pstmt.setString(2, ticket.getDate());
            pstmt.setString(3, ticket.getCarID());
            pstmt.setString(4, ticket.getStartStation());
            pstmt.setString(5, ticket.getEndStation());
            pstmt.setString(6, ticket.getPaperType());
            pstmt.setString(7, ticket.getIdCard());
            pstmt.setString(8, ticket.getName());
            pstmt.setString(9, ticket.getStartTime());
            pstmt.setString(10, ticket.getEndTime());
            pstmt.setString(11, ticket.getSeatType());
            pstmt.setInt(12, ticket.getSeatNo());
            pstmt.setFloat(13, ticket.getPrice());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean isSeatAvailable(String hswId, String date, int seatNo, String seatType) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM Ticket WHERE car_id = ? AND date = ? AND seat_no = ? AND seat_type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hswId);
            pstmt.setString(2, date);
            pstmt.setInt(3, seatNo);
            pstmt.setString(4, seatType);
            ResultSet rs = pstmt.executeQuery();
             if (rs.next()) {
                 return rs.getInt("count") == 0; // 如果计数为0，则座位未被预订
             }
             return false;
        }
    }

    // 获取Ticket
    public Ticket getTicket(String id) {
        String sql = "SELECT * FROM Ticket WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getString("id"),
                        rs.getString("date"),
                        rs.getString("car_id"),
                        rs.getString("start_station"),
                        rs.getString("end_station"),
                        rs.getString("paper_type"),
                        rs.getString("id_card"),
                        rs.getString("name"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("seat_type"),
                        rs.getInt("seat_no"),
                        rs.getFloat("price")
                );
                return ticket;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 更新Ticket
    public void updateTicket(Ticket ticket) {
        String sql = "UPDATE Ticket SET date = ?, car_id = ?, start_station = ?, end_station = ?, paper_type = ?, id_card = ?, name = ?, start_time = ?, end_time = ?, seat_type = ?, seat_no = ?, price = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticket.getDate());
            pstmt.setString(2, ticket.getCarID());
            pstmt.setString(3, ticket.getStartStation());
            pstmt.setString(4, ticket.getEndStation());
            pstmt.setString(5, ticket.getPaperType());
            pstmt.setString(6, ticket.getIdCard());
            pstmt.setString(7, ticket.getName());
            pstmt.setString(8, ticket.getStartTime());
            pstmt.setString(9, ticket.getEndTime());
            pstmt.setString(10, ticket.getSeatType());
            pstmt.setInt(11, ticket.getSeatNo());
            pstmt.setFloat(12, ticket.getPrice());
            pstmt.setString(13, ticket.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除Ticket
    public void deleteTicket(String id) {
        String sql = "DELETE FROM Ticket WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Ticket> getAllTickets() throws SQLException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Ticket";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getString("id"),
                        rs.getString("date"),
                        rs.getString("car_id"),
                        rs.getString("start_station"),
                        rs.getString("end_station"),
                        rs.getString("paper_type"),
                        rs.getString("id_card"),
                        rs.getString("name"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("seat_type"),
                        rs.getInt("seat_no"),
                        rs.getFloat("price")
                );
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public int getRemainingTickets(String hswId, String seatType) throws SQLException {
        // 获取指定 HSW 的座位信息
        HSW hsw = hswService.getHSW(hswId);
        if (hsw == null) {
            throw new SQLException("No HSW found with ID: " + hswId);
        }

        int totalSeats = seatType.equals("first") ? hsw.getFirstSeats() : hsw.getSecondSeats();
        String sqlSoldTickets = "SELECT count(*) as soldTickets FROM Ticket WHERE car_id = ? AND seat_type = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlSoldTickets)) {

            pstmt.setString(1, hswId);
            pstmt.setString(2, seatType);
            ResultSet rs = pstmt.executeQuery();

            int soldTickets = 0;
            if (rs.next()) {
                soldTickets = rs.getInt("soldTickets");
            }

            return totalSeats - soldTickets;
        }
    }

}
