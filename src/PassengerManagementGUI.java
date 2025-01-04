import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class PassengerManagementGUI {

    private static PassengerService passengerService = new PassengerService();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Passenger Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JButton addButton = new JButton("Add Passenger");
        JButton deleteButton = new JButton("Delete Passenger");
        JButton updateButton = new JButton("Update Passenger");
        JButton searchButton = new JButton("Search Passenger");
        JButton viewButton = new JButton("View All Passengers");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPassengerDialog();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeletePassengerDialog();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdatePassengerDialog();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchPassengerDialog();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showViewAllPassengersDialog();
            }
        });

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(searchButton);
        panel.add(viewButton);
        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }

    private static void showAddPassengerDialog() {
        // 创建对话框
        JDialog dialog = new JDialog();
        dialog.setTitle("Add Passenger");

        // 设置布局
        dialog.setLayout(new GridLayout(0, 2));

        // 添加标签和文本框
        JLabel idCardLabel = new JLabel("ID Card:");
        JTextField idCardField = new JTextField(10);
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(10);
        JLabel paperTypeLabel = new JLabel("Paper Type:");
        JTextField paperTypeField = new JTextField(10);
        JLabel selfPhoneLabel = new JLabel("Self Phone:");
        JTextField selfPhoneField = new JTextField(10);
        JLabel emergencyContacterLabel = new JLabel("Emergency Contacter:");
        JTextField emergencyContacterField = new JTextField(10);
        JLabel emergencyContacterPhoneLabel = new JLabel("Emergency Contacter Phone:");
        JTextField emergencyContacterPhoneField = new JTextField(10);

        // 添加提交按钮
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        // 在后台线程执行耗时的数据库操作
                        String idCard = idCardField.getText();
                        String name = nameField.getText();
                        String paperType = paperTypeField.getText();
                        String selfPhone = selfPhoneField.getText();
                        String emergencyContacter = emergencyContacterField.getText();
                        String emergencyContacterPhone = emergencyContacterPhoneField.getText();

                        Passenger passenger = new Passenger(idCard, name, paperType, selfPhone, emergencyContacter, emergencyContacterPhone);
                        passengerService.addPassenger(passenger);
                        return null;
                    }

                    @Override
                    protected void done() {
                        // 完成后在EDT上更新GUI
                        JOptionPane.showMessageDialog(dialog, "Passenger added successfully!");
                    }
                };
                worker.execute(); // 启动SwingWorker线程
            }
        });

        // 添加组件到对话框
        dialog.add(idCardLabel);
        dialog.add(idCardField);
        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(paperTypeLabel);
        dialog.add(paperTypeField);
        dialog.add(selfPhoneLabel);
        dialog.add(selfPhoneField);
        dialog.add(emergencyContacterLabel);
        dialog.add(emergencyContacterField);
        dialog.add(emergencyContacterPhoneLabel);
        dialog.add(emergencyContacterPhoneField);

        dialog.add(submitButton);

        dialog.pack();
        dialog.setVisible(true);
    }

    private static void showDeletePassengerDialog() {
        // 创建删除乘客的对话框
        JDialog dialog = new JDialog();
        dialog.setTitle("Delete Passenger");
        dialog.setLayout(new FlowLayout());
        dialog.setSize(300, 120);

        // 创建标签和文本框用于输入身份证号
        JLabel idCardLabel = new JLabel("ID Card:");
        JTextField idCardField = new JTextField(15);
        dialog.add(idCardLabel);
        dialog.add(idCardField);

        // 创建删除按钮并添加动作监听器
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idCard = idCardField.getText();
                if (!idCard.isEmpty()) {
                    passengerService.deletePassenger(idCard);
                    JOptionPane.showMessageDialog(dialog, "Passenger deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please enter an ID card number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        dialog.add(deleteButton);

        // 显示对话框
        dialog.setVisible(true);
    }

    private static void showUpdatePassengerDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Update Passenger");
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Select Passenger ID:"));
        JComboBox<String> idDropdown = new JComboBox<>();

        // 获取所有乘客的ID并添加到下拉列表
        ArrayList<Passenger> passengers = passengerService.getAllPassengers();
        for (Passenger p : passengers) {
            idDropdown.addItem(p.getIdCard());
        }

        panel.add(idDropdown);

        // 添加其它表单字段
        JTextField nameField = new JTextField(10);
        JTextField paperTypeField = new JTextField(10);
        JTextField selfPhoneField = new JTextField(10);
        JTextField emergencyContacterField = new JTextField(10);
        JTextField emergencyContacterPhoneField = new JTextField(10);

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Paper Type:"));
        panel.add(paperTypeField);
        panel.add(new JLabel("Self Phone:"));
        panel.add(selfPhoneField);
        panel.add(new JLabel("Emergency Contacter:"));
        panel.add(emergencyContacterField);
        panel.add(new JLabel("Emergency Contacter Phone:"));
        panel.add(emergencyContacterPhoneField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idCard = (String) idDropdown.getSelectedItem();
                String name = nameField.getText();
                String paperType = paperTypeField.getText();
                String selfPhone = selfPhoneField.getText();
                String emergencyContacter = emergencyContacterField.getText();
                String emergencyContacterPhone = emergencyContacterPhoneField.getText();

                Passenger passenger = new Passenger(idCard, name, paperType, selfPhone, emergencyContacter, emergencyContacterPhone);
                passengerService.updatePassenger(passenger);

                dialog.dispose();
            }
        });

        idDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedId = (String) idDropdown.getSelectedItem();
                Passenger selectedPassenger = passengerService.getPassenger(selectedId);
                nameField.setText(selectedPassenger.getName());
                paperTypeField.setText(selectedPassenger.getPaperType());
                selfPhoneField.setText(selectedPassenger.getSelfPhone());
                emergencyContacterField.setText(selectedPassenger.getEmergencyContacter());
                emergencyContacterPhoneField.setText(selectedPassenger.getEmergencyContacterPhone());
            }
        });

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(submitButton, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setVisible(true);
    }


    private static void showSearchPassengerDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Search Passenger");
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        JLabel idCardLabel = new JLabel("ID Card:");
        JTextField idCardField = new JTextField(10);
        inputPanel.add(idCardLabel);
        inputPanel.add(idCardField);

        JButton searchButton = new JButton("Search");
        inputPanel.add(searchButton);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(10);
        nameField.setEditable(false);
        JLabel paperTypeLabel = new JLabel("Paper Type:");
        JTextField paperTypeField = new JTextField(10);
        paperTypeField.setEditable(false);
        JLabel selfPhoneLabel = new JLabel("Self Phone:");
        JTextField selfPhoneField = new JTextField(10);
        selfPhoneField.setEditable(false);
        JLabel emergencyContacterLabel = new JLabel("Emergency Contacter:");
        JTextField emergencyContacterField = new JTextField(10);
        emergencyContacterField.setEditable(false);
        JLabel emergencyContacterPhoneLabel = new JLabel("Emergency Contacter Phone:");
        JTextField emergencyContacterPhoneField = new JTextField(10);
        emergencyContacterPhoneField.setEditable(false);

        JPanel displayPanel = new JPanel(new GridLayout(0, 2));
        displayPanel.add(nameLabel);
        displayPanel.add(nameField);
        displayPanel.add(paperTypeLabel);
        displayPanel.add(paperTypeField);
        displayPanel.add(selfPhoneLabel);
        displayPanel.add(selfPhoneField);
        displayPanel.add(emergencyContacterLabel);
        displayPanel.add(emergencyContacterField);
        displayPanel.add(emergencyContacterPhoneLabel);
        displayPanel.add(emergencyContacterPhoneField);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idCard = idCardField.getText();
                Passenger passenger = passengerService.getPassenger(idCard);
                if (passenger != null) {
                    nameField.setText(passenger.getName());
                    paperTypeField.setText(passenger.getPaperType());
                    selfPhoneField.setText(passenger.getSelfPhone());
                    emergencyContacterField.setText(passenger.getEmergencyContacter());
                    emergencyContacterPhoneField.setText(passenger.getEmergencyContacterPhone());
                } else {
                    JOptionPane.showMessageDialog(dialog, "No passenger found with ID Card: " + idCard);
                    nameField.setText("");
                    paperTypeField.setText("");
                    selfPhoneField.setText("");
                    emergencyContacterField.setText("");
                    emergencyContacterPhoneField.setText("");
                }
            }
        });

        dialog.add(inputPanel, BorderLayout.NORTH);
        dialog.add(displayPanel, BorderLayout.CENTER);

        dialog.pack();
        dialog.setVisible(true);
    }

    private static void showViewAllPassengersDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("View All Passengers");
        dialog.setSize(800, 300); // 调整尺寸以容纳更多列

        // 添加所有相关的列名
        String[] columnNames = {
                "ID Card", "Name", "Paper Type", "Self Phone", "Emergency Contacter", "Emergency Contacter Phone"
        };

        Object[][] data;

        try {
            data = fetchPassengerData(); // 调用方法获取乘客数据
        } catch (SQLException e) {
            showErrorDialog(e);
            return;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
    }

    // 辅助方法，从数据库获取乘客数据
    private static Object[][] fetchPassengerData() throws SQLException {
        ArrayList<Passenger> passengersList = passengerService.getAllPassengers(); // 使用PassengerService获取所有乘客
        Object[][] data = new Object[passengersList.size()][6]; // 创建一个二维数组来存储表格数据

        int i = 0;
        for (Passenger passenger : passengersList) {
            data[i][0] = passenger.getIdCard();
            data[i][1] = passenger.getName();
            data[i][2] = passenger.getPaperType();
            data[i][3] = passenger.getSelfPhone();
            data[i][4] = passenger.getEmergencyContacter();
            data[i][5] = passenger.getEmergencyContacterPhone();
            i++;
        }
        return data;
    }

    private static void showErrorDialog(SQLException e) {
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

}
