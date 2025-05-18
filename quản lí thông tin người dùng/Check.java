import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Check extends JFrame {
    private JTextField txtName, txtEmail;
    private JButton btnSubmit;
    private MainView mainView;

    public Check(MainView mainView) {
        this.mainView = mainView;
        setTitle("Check - Add User");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Name:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        btnSubmit = new JButton("Submit");
        add(btnSubmit);

        btnSubmit.addActionListener(this::handleSubmit);

        setLocationRelativeTo(null); // Vị trí giữa màn hình
    }

    private void handleSubmit(ActionEvent e) {
        String name = txtName.getText();
        String email = txtEmail.getText();
        if (!name.isEmpty() && !email.isEmpty()) {
            mainView.addUserFromCheck(name, email);
            dispose(); // Đóng cửa sổ Check
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!");
        }
    }
}
