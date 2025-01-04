import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketManagementGUI {

    private static TicketService ticketService = new TicketService();
    private static HSWService hswService = new HSWService();
    private static PassengerService passengerService = new PassengerService();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Ticket Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // 创建按钮和对应的事件处理器
        JButton bookButton = new JButton("Book Ticket");
        JButton refundButton = new JButton("Refund Ticket");
        JButton searchTicketButton = new JButton("Search Ticket");
        JButton remainingTicketButton = new JButton("Search Remaining Tickets");
        JButton viewAllTicketsButton = new JButton("View All Tickets");
        JButton printTicketButton = new JButton("Print Ticket");

        // 为按钮添加动作监听器
        bookButton.addActionListener(e -> {
            try {
                showBookTicketDialog();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        refundButton.addActionListener(e -> showRefundTicketDialog());
        searchTicketButton.addActionListener(e -> showSearchTicketDialog());
        remainingTicketButton.addActionListener(e -> showSearchRemainingTicketsDialog());
        viewAllTicketsButton.addActionListener(e -> showViewAllTicketsDialog());
        printTicketButton.addActionListener(e -> showPrintTicketDialog());

        // 添加按钮到面板
        JPanel panel = new JPanel();
        panel.add(bookButton);
        panel.add(refundButton);
        panel.add(searchTicketButton);
        panel.add(remainingTicketButton);
        panel.add(viewAllTicketsButton);
        panel.add(printTicketButton);
        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }


    private static void showBookTicketDialog() throws SQLException {
        JDialog dialog = new JDialog();
        dialog.setTitle("Book Ticket");
        dialog.setLayout(new GridLayout(0, 2));
        dialog.setSize(600, 600);

        // 初始化下拉菜单
        JComboBox<String> startStationBox = new JComboBox<>();
        JComboBox<String> endStationBox = new JComboBox<>();
        JComboBox<String> timeBox = new JComboBox<>();
        JComboBox<String> trainNumberBox = new JComboBox<>();
        JComboBox<Passenger> passengerBox = new JComboBox<>();
        JComboBox<String> seatTypeBox = new JComboBox<>(new String[] {"Business", "First", "Second"});

        // 初始化起始站点下拉菜单
        ArrayList<String> startStations = hswService.getAllStartStations();
        for (String station : startStations) {
            startStationBox.addItem(station);
        }

        startStationBox.addActionListener(e -> {
            String selectedStartStation = (String) startStationBox.getSelectedItem();
            ArrayList<String> endStations = null;
            try {
                endStations = hswService.getEndStationsForStartStation(selectedStartStation);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            endStationBox.removeAllItems();
            for (String station : endStations) {
                endStationBox.addItem(station);
            }
        });

        // 更新列车号码和时间
        endStationBox.addActionListener(e -> {
            String selectedStartStation = (String) startStationBox.getSelectedItem();
            String selectedEndStation = (String) endStationBox.getSelectedItem();
            try {
                updateTrainNumbersAndTimes(selectedStartStation, selectedEndStation, trainNumberBox, timeBox);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // 初始化乘客下拉菜单
        ArrayList<Passenger> passengers = passengerService.getAllPassengers();
        for (Passenger passenger : passengers) {
            passengerBox.addItem(passenger);
        }

        JTextField seatNoField = new JTextField(10);
        JTextField priceField = new JTextField(10);

        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(e -> {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // 在后台线程执行数据库操作
                    try {
                        String ticketId = "TKT" + System.currentTimeMillis();
                        String date = (String) timeBox.getSelectedItem();
                        String trainNumber = (String) trainNumberBox.getSelectedItem();
                        String startStation = (String) startStationBox.getSelectedItem();
                        String endStation = (String) endStationBox.getSelectedItem();
                        String seatType = (String) seatTypeBox.getSelectedItem();
                        int seatNo = Integer.parseInt(seatNoField.getText());
                        float price = Float.parseFloat(priceField.getText());
                        Passenger selectedPassenger = (Passenger) passengerBox.getSelectedItem();

                        Ticket ticket = new Ticket(
                                ticketId, date, trainNumber, startStation, endStation,
                                selectedPassenger.getPaperType(), selectedPassenger.getIdCard(),
                                selectedPassenger.getName(), "", "", seatType, seatNo, price
                        );

                        ticketService.addTicket(ticket);
                        hswService.updateHSWSeatInfo(trainNumber, seatType, 1);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (NumberFormatException ex) {
                        throw new RuntimeException("Invalid seat number or price.", ex);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    // 在EDT上更新GUI
                    JOptionPane.showMessageDialog(dialog, "Ticket booked successfully!");
                }
            };
            worker.execute();
        });

        dialog.add(new JLabel("Ticket ID:"));
        dialog.add(new JLabel("Automatically generated"));
        dialog.add(new JLabel("Start Station:"));
        dialog.add(startStationBox);
        dialog.add(new JLabel("End Station:"));
        dialog.add(endStationBox);
        dialog.add(new JLabel("Train Number:"));
        dialog.add(trainNumberBox);
        dialog.add(new JLabel("Date and Time:"));
        dialog.add(timeBox);
        dialog.add(new JLabel("Seat Type:"));
        dialog.add(seatTypeBox);
        dialog.add(new JLabel("Seat Number:"));
        dialog.add(seatNoField);
        dialog.add(new JLabel("Price:"));
        dialog.add(priceField);
        dialog.add(new JLabel("Passenger:"));
        dialog.add(passengerBox);
        dialog.add(bookButton);

        dialog.setVisible(true);
    }
    private static void updateTrainNumbersAndTimes(String startStation, String endStation, JComboBox<String> trainNumberBox, JComboBox<String> timeBox) throws SQLException {
        // 从 hswService 获取列车号码和时间信息
        ArrayList<String> trainNumbers = hswService.getTrainNumbers(startStation, endStation);
        trainNumberBox.removeAllItems();
        for (String number : trainNumbers) {
            trainNumberBox.addItem(number);
        }

        // 同样更新时间下拉菜单
        ArrayList<String> times = hswService.getAvailableTimes(startStation, endStation);
        timeBox.removeAllItems();
        for (String time : times) {
            timeBox.addItem(time);
        }
    }


    private static void showRefundTicketDialog() {
        // 创建并配置对话框
        JDialog dialog = new JDialog();
        dialog.setTitle("Refund Ticket");
        dialog.setLayout(new GridLayout(0, 2));
        dialog.setSize(300, 200);

        // 添加标签和文本框
        JTextField idField = new JTextField(10);

        JButton refundButton = new JButton("Refund");
        refundButton.addActionListener(e -> {
            String ticketId = idField.getText();
            // 调用 TicketService 的方法来删除 Ticket
            ticketService.deleteTicket(ticketId);
            JOptionPane.showMessageDialog(dialog, "Ticket refunded successfully!");
        });

        // 将组件添加到对话框
        dialog.add(new JLabel("Ticket ID:"));
        dialog.add(idField);
        dialog.add(refundButton);

        dialog.setVisible(true);
    }

    private static void showSearchTicketDialog() {
        // 创建并配置对话框
        JDialog dialog = new JDialog();
        dialog.setTitle("Search Ticket");
        dialog.setLayout(new GridLayout(0, 2));
        dialog.setSize(300, 200);

        // 添加标签和文本框
        JTextField idField = new JTextField(10);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String ticketId = idField.getText();
            Ticket ticket = ticketService.getTicket(ticketId);
            if (ticket != null) {
                // 显示查询结果
                JOptionPane.showMessageDialog(dialog, "Ticket Found: \n" + ticket.toString());
            } else {
                JOptionPane.showMessageDialog(dialog, "No ticket found with ID: " + ticketId);
            }
        });

        // 将组件添加到对话框
        dialog.add(new JLabel("Ticket ID:"));
        dialog.add(idField);
        dialog.add(searchButton);

        dialog.setVisible(true);
    }

    private static void showSearchRemainingTicketsDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Remaining Tickets");
        dialog.setLayout(new GridLayout(0, 2));
        dialog.setSize(300, 200);

        JLabel carIdLabel = new JLabel("HSW ID:");
        JTextField carIdField = new JTextField(10);
        JLabel seatTypeLabel = new JLabel("Seat Type (business/first/second):");
        JTextField seatTypeField = new JTextField(10);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carId = carIdField.getText();
                String seatType = seatTypeField.getText();

                try {
                    int remainingTickets = ticketService.getRemainingTickets(carId, seatType);
                    JOptionPane.showMessageDialog(dialog, "Remaining Tickets for " + seatType + " class: " + remainingTickets);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.add(carIdLabel);
        dialog.add(carIdField);
        dialog.add(seatTypeLabel);
        dialog.add(seatTypeField);
        dialog.add(searchButton);

        dialog.setVisible(true);
    }

    private static void showViewAllTicketsDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("All Tickets");
        dialog.setSize(800, 300);

        String[] columnNames = {"Ticket ID", "Date", "Car ID", "Start Station", "End Station", "Seat Type", "Seat No", "Price", "Name", "ID Card"};
        Object[][] data;

        try {
            data = fetchTicketData(); // 调用方法获取车票数据
        } catch (SQLException e) {
            showErrorDialog(e);
            return;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
    }

    private static Object[][] fetchTicketData() throws SQLException {
        ArrayList<Ticket> tickets = ticketService.getAllTickets(); // 使用 TicketService 获取所有车票
        Object[][] data = new Object[tickets.size()][10]; // 创建一个二维数组来存储表格数据

        int i = 0;
        for (Ticket ticket : tickets) {
            data[i][0] = ticket.getId();
            data[i][1] = ticket.getDate();
            data[i][2] = ticket.getCarID();
            data[i][3] = ticket.getStartStation();
            data[i][4] = ticket.getEndStation();
            data[i][5] = ticket.getSeatType();
            data[i][6] = ticket.getSeatNo();
            data[i][7] = ticket.getPrice();
            data[i][8] = ticket.getName();
            data[i][9] = ticket.getIdCard();
            i++;
        }
        return data;
    }

    private static void showErrorDialog(SQLException e) {
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static void showPrintTicketDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Print Ticket");
        dialog.setLayout(new GridLayout(0, 2));
        dialog.setSize(300, 200);

        JTextField ticketIdField = new JTextField(10);

        JButton printButton = new JButton("Print");
        printButton.addActionListener(e -> {
            String ticketId = ticketIdField.getText();
            Ticket ticket = ticketService.getTicket(ticketId);
            if (ticket != null) {
                // 打印车票信息，这里只是显示消息框作为示例
                JOptionPane.showMessageDialog(dialog, "Printing Ticket: \n" + ticket.toString());
            } else {
                JOptionPane.showMessageDialog(dialog, "No ticket found with ID: " + ticketId);
            }
        });

        dialog.add(new JLabel("Ticket ID:"));
        dialog.add(ticketIdField);
        dialog.add(printButton);

        dialog.setVisible(true);
    }


}
