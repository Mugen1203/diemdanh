import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class MainView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtName, txtEmail;
    private JButton btnAdd, btnDelete, btnUpdate, btnOpenCheck;

    private UserDAO userDAO = new UserDAO(); // Tương tác với cơ sở dữ liệu

    public MainView() {
        setTitle("Quản lý Người Dùng");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // **Panel hiển thị bảng dữ liệu**
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // **Panel nhập liệu và nút chức năng**
        JPanel panelInput = new JPanel(new GridLayout(5, 2, 10, 10));
        panelInput.add(new JLabel("Name:"));
        txtName = new JTextField();
        panelInput.add(txtName);

        panelInput.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelInput.add(txtEmail);

        btnAdd = new JButton("Add");
        btnDelete = new JButton("Delete");
        btnUpdate = new JButton("Update");
        btnOpenCheck = new JButton("Open Check");

        panelInput.add(btnAdd);
        panelInput.add(btnDelete);
        panelInput.add(btnUpdate);
        panelInput.add(btnOpenCheck);

        add(panelInput, BorderLayout.SOUTH);

        // **Thêm sự kiện cho các nút**
        btnAdd.addActionListener(e -> addUser());
        btnDelete.addActionListener(e -> deleteUser());
        btnUpdate.addActionListener(e -> updateUser());
        btnOpenCheck.addActionListener(e -> new Check(this).setVisible(true));

        // **Load dữ liệu ban đầu từ cơ sở dữ liệu**
        loadTableData();
    }

    // **Tải dữ liệu từ cơ sở dữ liệu vào bảng**
    private void loadTableData() {
        try {
            List<User> users = userDAO.getAllUsers();
            tableModel.setRowCount(0); // Xóa dữ liệu cũ
            for (User user : users) {
                tableModel.addRow(new Object[]{user.getId(), user.getName(), user.getEmail()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }

    // **Thêm người dùng**
    private void addUser() {
        String name = txtName.getText();
        String email = txtEmail.getText();

        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!");
            return;
        }

        try {
            User user = new User(0, name, email);
            userDAO.addUser(user);
            loadTableData(); // Cập nhật lại bảng
            JOptionPane.showMessageDialog(this, "User added successfully!");
            clearInputFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding user: " + ex.getMessage());
        }
    }

    // **Xóa người dùng**
    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            userDAO.deleteUser(userId);
            loadTableData(); // Cập nhật lại bảng
            JOptionPane.showMessageDialog(this, "User deleted successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting user: " + ex.getMessage());
        }
    }

    // **Cập nhật người dùng**
    private void updateUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to update.");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String name = txtName.getText();
        String email = txtEmail.getText();

        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!");
            return;
        }

        try {
            User user = new User(userId, name, email);
            userDAO.updateUser(user);
            loadTableData(); // Cập nhật lại bảng
            JOptionPane.showMessageDialog(this, "User updated successfully!");
            clearInputFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating user: " + ex.getMessage());
        }
    }

    // **Nhận dữ liệu từ lớp Check**
    public void addUserFromCheck(String name, String email) {
        try {
            User user = new User(0, name, email);
            userDAO.addUser(user);
            loadTableData(); // Cập nhật lại bảng
            JOptionPane.showMessageDialog(this, "User added successfully from Check!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding user: " + ex.getMessage());
        }
    }

    // **Xóa các trường nhập liệu**
    private void clearInputFields() {
        txtName.setText("");
        txtEmail.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
