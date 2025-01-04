import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class HSWManagementGUI {

    private static HSWService hswService = new HSWService();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HSWManagementGUI::createAndShowGUI);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("HSW Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JButton addButton = new JButton("Add HSW");
        JButton stopButton = new JButton("Stop HSW");
        JButton modifyButton = new JButton("Modify HSW");
        JButton viewButton = new JButton("View HSWs");
        JButton searchButton = new JButton("Search HSWs");

        addButton.addActionListener(e -> addHSWDialog());
        stopButton.addActionListener(e -> stopHSWDialog());
        modifyButton.addActionListener(e -> modifyHSWDialog());
        viewButton.addActionListener(e -> viewHSWsDialog());
        searchButton.addActionListener(e -> searchHSWsDialog());


        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(stopButton);
        panel.add(searchButton);
        panel.add(modifyButton);
        panel.add(viewButton);
        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }

    private static void addHSWDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add HSW");
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("ID:"));
        JTextField idField = new JTextField(10);
        panel.add(idField);

        panel.add(new JLabel("Start Station:"));
        JTextField startStationField = new JTextField(10);
        panel.add(startStationField);

        panel.add(new JLabel("End Station:"));
        JTextField endStationField = new JTextField(10);
        panel.add(endStationField);

        panel.add(new JLabel("Mileages:"));
        JTextField mileagesField = new JTextField(10);
        panel.add(mileagesField);

        panel.add(new JLabel("Status:"));
        JTextField statusField = new JTextField(10);
        panel.add(statusField);

        panel.add(new JLabel("Start Time:"));
        JTextField startTimeField = new JTextField(10);
        panel.add(startTimeField);

        panel.add(new JLabel("End Time:"));
        JTextField endTimeField = new JTextField(10);
        panel.add(endTimeField);

        panel.add(new JLabel("Platforms:"));
        JTextField platformsField = new JTextField(10);
        panel.add(platformsField);

        panel.add(new JLabel("Business Blocks:"));
        JTextField businessBlocksField = new JTextField(10);
        panel.add(businessBlocksField);

        panel.add(new JLabel("First Seats:"));
        JTextField firstSeatsField = new JTextField(10);
        panel.add(firstSeatsField);

        panel.add(new JLabel("Second Seats:"));
        JTextField secondSeatsField = new JTextField(10);
        panel.add(secondSeatsField);

        dialog.add(panel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // 在后台线程执行耗时的数据库操作
                    String id = idField.getText();
                    String startStation = startStationField.getText();
                    String endStation = endStationField.getText();
                    float mileages = Float.parseFloat(mileagesField.getText());
                    String status = statusField.getText();
                    String startTime = startTimeField.getText();
                    String endTime = endTimeField.getText();
                    int platforms = Integer.parseInt(platformsField.getText());
                    int businessBlocks = Integer.parseInt(businessBlocksField.getText());
                    int firstSeats = Integer.parseInt(firstSeatsField.getText());
                    int secondSeats = Integer.parseInt(secondSeatsField.getText());

                    HSW hsw = new HSW(id, startStation, endStation, mileages, status, startTime, endTime, platforms, businessBlocks, firstSeats, secondSeats, null);
                    hswService.addHSW(hsw);
                    return null;
                }

                @Override
                protected void done() {
                    // 完成后在EDT上更新GUI
                    JOptionPane.showMessageDialog(dialog, "HSW added successfully!");
                }
            };
            worker.execute(); // 启动 SwingWorker 线程
        });
        dialog.add(submitButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private static void stopHSWDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Stop HSW");
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 200);

        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Select HSW ID to Stop:"));
        JComboBox<String> hswIdComboBox = new JComboBox<>();
        try {
            ArrayList<HSW> hsList = hswService.getAllHSWs();  // Assuming this method exists in HSWService
            for (HSW hsw : hsList) {
                hswIdComboBox.addItem(hsw.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        panel.add(hswIdComboBox);

        dialog.add(panel, BorderLayout.CENTER);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            String selectedId = (String) hswIdComboBox.getSelectedItem();
            if (selectedId != null) {
                HSW hsw = hswService.getHSW(selectedId);
                if (hsw != null) {
                    hsw.setStatus("Stopped");
                    hswService.updateHSW(hsw);
                }
            }

            dialog.dispose();
        });
        dialog.add(stopButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private static void modifyHSWDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Modify HSW");
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);

        // 创建面板用于表单
        JPanel panel = new JPanel(new GridLayout(0, 2));

        // 下拉列表选择要修改的HSW
        panel.add(new JLabel("Select HSW ID to Modify:"));
        JComboBox<String> hswIdComboBox = new JComboBox<>();
        try {
            ArrayList<HSW> hswList = hswService.getAllHSWs();  // 假设这个方法存在于 HSWService
            for (HSW hsw : hswList) {
                hswIdComboBox.addItem(hsw.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        panel.add(hswIdComboBox);

        // 其他属性输入字段
        panel.add(new JLabel("Start Station:"));
        JTextField startStationField = new JTextField(10);
        panel.add(startStationField);

        panel.add(new JLabel("End Station:"));
        JTextField endStationField = new JTextField(10);
        panel.add(endStationField);

        panel.add(new JLabel("Mileages:"));
        JTextField mileagesField = new JTextField(10);
        panel.add(mileagesField);

        panel.add(new JLabel("Status:"));
        JTextField statusField = new JTextField(10);
        panel.add(statusField);

        panel.add(new JLabel("Start Time:"));
        JTextField startTimeField = new JTextField(10);
        panel.add(startTimeField);

        panel.add(new JLabel("End Time:"));
        JTextField endTimeField = new JTextField(10);
        panel.add(endTimeField);

        panel.add(new JLabel("Platforms:"));
        JTextField platformsField = new JTextField(10);
        panel.add(platformsField);

        panel.add(new JLabel("Business Blocks:"));
        JTextField businessBlocksField = new JTextField(10);
        panel.add(businessBlocksField);

        panel.add(new JLabel("First Seats:"));
        JTextField firstSeatsField = new JTextField(10);
        panel.add(firstSeatsField);

        panel.add(new JLabel("Second Seats:"));
        JTextField secondSeatsField = new JTextField(10);
        panel.add(secondSeatsField);

        dialog.add(panel, BorderLayout.CENTER);

        // 修改按钮逻辑
        JButton modifyButton = new JButton("Modify");
        modifyButton.addActionListener(e -> {
            try {
                String selectedId = (String) hswIdComboBox.getSelectedItem();
                if (selectedId != null) {
                    HSW hsw = hswService.getHSW(selectedId);
                    if (hsw != null) {
                        // 更新 hsw 对象的属性
                        hsw.setStartStation(startStationField.getText());
                        hsw.setEndStation(endStationField.getText());
                        hsw.setMileages(Float.parseFloat(mileagesField.getText()));
                        hsw.setStatus(statusField.getText());
                        hsw.setStartTime(startTimeField.getText());
                        hsw.setEndTime(endTimeField.getText());
                        hsw.setPlatforms(Integer.parseInt(platformsField.getText()));
                        hsw.setBusinessBlocks(Integer.parseInt(businessBlocksField.getText()));
                        hsw.setFirstSeats(Integer.parseInt(firstSeatsField.getText()));
                        hsw.setSecondSeats(Integer.parseInt(secondSeatsField.getText()));

                        hswService.updateHSW(hsw);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid number format", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            dialog.dispose();
        });
        dialog.add(modifyButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private static void viewHSWsDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("View HSWs");
        dialog.setSize(800, 300); // 调整尺寸以容纳更多列

        // 添加所有相关的列名
        String[] columnNames = {
                "ID", "Start Station", "End Station", "Mileages", "Status",
                "Start Time", "End Time", "Platforms", "Business Blocks",
                "First Seats", "Second Seats"
        };

        Object[][] data;

        try {
            data = fetchHSWData(); // 调用方法获取HSW数据
        } catch (SQLException e) {
            showErrorDialog(e);
            return;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
    }

    // 辅助方法，从数据库获取HSW数据
    private static Object[][] fetchHSWData() throws SQLException {
        ArrayList<HSW> hswList = hswService.getAllHSWs(); // 使用HSWService获取所有HSW
        Object[][] data = new Object[hswList.size()][11]; // 创建一个二维数组来存储表格数据

        int i = 0;
        for (HSW hsw : hswList) {
            data[i][0] = hsw.getId();
            data[i][1] = hsw.getStartStation();
            data[i][2] = hsw.getEndStation();
            data[i][3] = hsw.getMileages();
            data[i][4] = hsw.getStatus();
            data[i][5] = hsw.getStartTime();
            data[i][6] = hsw.getEndTime();
            data[i][7] = hsw.getPlatforms();
            data[i][8] = hsw.getBusinessBlocks();
            data[i][9] = hsw.getFirstSeats();
            data[i][10] = hsw.getSecondSeats();
            i++;
        }
        return data;
    }

    private static void showErrorDialog(SQLException e) {
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static void searchHSWsDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Search HSW");
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300); // 设置对话框大小

        // 输入面板
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        JLabel idLabel = new JLabel("HSW ID:");
        JTextField idField = new JTextField(10);
        inputPanel.add(idLabel);
        inputPanel.add(idField);

        JButton searchButton = new JButton("Search");
        inputPanel.add(searchButton);

        // 显示面板
        JPanel displayPanel = new JPanel(new GridLayout(0, 2));
        JLabel startStationLabel = new JLabel("Start Station:");
        JTextField startStationField = new JTextField(10);
        startStationField.setEditable(false);
        displayPanel.add(startStationLabel);
        displayPanel.add(startStationField);

        JLabel endStationLabel = new JLabel("End Station:");
        JTextField endStationField = new JTextField(10);
        endStationField.setEditable(false);
        displayPanel.add(endStationLabel);
        displayPanel.add(endStationField);

        // 更多标签和文本字段...
        JLabel statusLabel = new JLabel("Status:");
        JTextField statusField = new JTextField(10);
        statusField.setEditable(false);
        displayPanel.add(statusLabel);
        displayPanel.add(statusField);

        JLabel startTimeLabel = new JLabel("Start Time:");
        JTextField startTimeField = new JTextField(10);
        startTimeField.setEditable(false);
        displayPanel.add(startTimeLabel);
        displayPanel.add(startTimeField);

        JLabel endTimeLabel = new JLabel("End Time:");
        JTextField endTimeField = new JTextField(10);
        endTimeField.setEditable(false);
        displayPanel.add(endTimeLabel);
        displayPanel.add(endTimeField);

        JLabel platformsLabel = new JLabel("Platforms:");
        JTextField platformsField = new JTextField(10);
        platformsField.setEditable(false);
        displayPanel.add(platformsLabel);
        displayPanel.add(platformsField);

        JLabel businessBlocksLabel = new JLabel("Business Blocks:");
        JTextField businessBlocksField = new JTextField(10);
        businessBlocksField.setEditable(false);
        displayPanel.add(businessBlocksLabel);
        displayPanel.add(businessBlocksField);

        JLabel firstSeatsLabel = new JLabel("First Seats:");
        JTextField firstSeatsField = new JTextField(10);
        firstSeatsField.setEditable(false);
        displayPanel.add(firstSeatsLabel);
        displayPanel.add(firstSeatsField);

        JLabel secondSeatsLabel = new JLabel("Second Seats:");
        JTextField secondSeatsField = new JTextField(10);
        secondSeatsField.setEditable(false);
        displayPanel.add(secondSeatsLabel);
        displayPanel.add(secondSeatsField);

// 修改搜索按钮的事件处理器，以更新这些字段的显示
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hswId = idField.getText();
                HSW hsw = hswService.getHSW(hswId);
                if (hsw != null) {
                    startStationField.setText(hsw.getStartStation());
                    endStationField.setText(hsw.getEndStation());
                    statusField.setText(hsw.getStatus());
                    startTimeField.setText(hsw.getStartTime());
                    endTimeField.setText(hsw.getEndTime());
                    platformsField.setText(String.valueOf(hsw.getPlatforms()));
                    businessBlocksField.setText(String.valueOf(hsw.getBusinessBlocks()));
                    firstSeatsField.setText(String.valueOf(hsw.getFirstSeats()));
                    secondSeatsField.setText(String.valueOf(hsw.getSecondSeats()));
                    // 更新其他字段...
                } else {
                    JOptionPane.showMessageDialog(dialog, "No HSW found with ID: " + hswId);
                    startStationField.setText("");
                    endStationField.setText("");
                    statusField.setText("");
                    startTimeField.setText("");
                    endTimeField.setText("");
                    platformsField.setText("");
                    businessBlocksField.setText("");
                    firstSeatsField.setText("");
                    secondSeatsField.setText("");
                    // 清空其他字段...
                }
            }
        });

        dialog.add(inputPanel, BorderLayout.NORTH);
        dialog.add(displayPanel, BorderLayout.CENTER);

        dialog.setVisible(true);
    }

}

