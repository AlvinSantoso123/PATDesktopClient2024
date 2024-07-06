package nodejs.server_implementation;
import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DesktopClient2 extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField adminUsernameField;
    private JPasswordField adminPasswordField;
    private JTextField courierNameField;
    private JTextField licensePlateField;
    private JTextField orderIdField;
    private JTextField assignOrderIdField;
    private JTextField statusOrderIdField;
    private JTextField getOrderIdField;
    private JTextField courierIdField;
    private JTextField pickupStatusField;
    private JTextField deliverStatusField;
    private JTextArea orderInfoArea;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public DesktopClient2() {
        setTitle("PAT Desktop Client");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panels for different functionalities
        JPanel loginPanel = createLoginPanel();
        JPanel adminPanel = createAdminPanel();
        JPanel assignCourierPanel = createAssignCourierPanel();
        JPanel updateStatusPanel = createUpdateStatusPanel();
        JPanel getOrderPanel = createGetOrderPanel();

        // Add panels to card layout
        mainPanel.add(loginPanel, "login");
        mainPanel.add(adminPanel, "admin");
        mainPanel.add(assignCourierPanel, "assignCourier");
        mainPanel.add(updateStatusPanel, "updateStatus");
        mainPanel.add(getOrderPanel, "getOrder");

        add(mainPanel);

        cardLayout.show(mainPanel, "login");
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(0, 1));
        loginPanel.add(new JLabel("Login"));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(usernameField.getText(), new String(passwordField.getPassword()));
            }
        });
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        return loginPanel;
    }

    private JPanel createAdminPanel() {
        JPanel adminPanel = new JPanel(new GridLayout(0, 1));
        JButton createAdminButton = new JButton("Create Account");
        createAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAdminAccount(adminUsernameField.getText(), new String(adminPasswordField.getPassword()));
            }
        });
        adminPanel.add(new JLabel("Create Account"));
        adminPanel.add(new JLabel("Username:"));
        adminUsernameField = new JTextField();
        adminPanel.add(adminUsernameField);
        adminPanel.add(new JLabel("Password:"));
        adminPasswordField = new JPasswordField();
        adminPanel.add(adminPasswordField);
        adminPanel.add(createAdminButton);

        JButton addCourierButton = new JButton("Add Courier");
        addCourierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourier(courierNameField.getText(), licensePlateField.getText());
            }
        });
        adminPanel.add(new JLabel("Courier Name:"));
        courierNameField = new JTextField();
        adminPanel.add(courierNameField);
        adminPanel.add(new JLabel("License Plate:"));
        licensePlateField = new JTextField();
        adminPanel.add(licensePlateField);
        adminPanel.add(addCourierButton);

        JButton assignCourierButton = new JButton("Assign Courier");
        assignCourierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "assignCourier");
            }
        });
        adminPanel.add(assignCourierButton);

        JButton updateStatusButton = new JButton("Update Status");
        updateStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "updateStatus");
            }
        });
        adminPanel.add(updateStatusButton);

        JButton getOrderButton = new JButton("Get Order");
        getOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "getOrder");
            }
        });
        adminPanel.add(getOrderButton);

        return adminPanel;
    }

    private JPanel createAssignCourierPanel() {
        JPanel assignCourierPanel = new JPanel(new GridLayout(0, 1));
        JButton assignPickupButton = new JButton("Assign Pickup");
        assignPickupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Assign Pickup Button Pressed");
                System.out.println("Order ID Field: " + assignOrderIdField);
                System.out.println("Courier ID Field: " + courierIdField);
                System.out.println("Order ID Text: " + assignOrderIdField.getText());
                System.out.println("Courier ID Text: " + courierIdField.getText());
                assignCourier(assignOrderIdField.getText(), courierIdField.getText(), true);
            }
        });

        JButton assignDeliverButton = new JButton("Assign Deliver");
        assignDeliverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Assign Deliver Button Pressed");
                System.out.println("Order ID Field: " + assignOrderIdField);
                System.out.println("Courier ID Field: " + courierIdField);
                System.out.println("Order ID Text: " + assignOrderIdField.getText());
                System.out.println("Courier ID Text: " + courierIdField.getText());
                assignCourier(assignOrderIdField.getText(), courierIdField.getText(), false);
            }
        });

        assignCourierPanel.add(new JLabel("Order ID:"));
        assignOrderIdField = new JTextField();
        assignCourierPanel.add(assignOrderIdField);
        assignCourierPanel.add(new JLabel("Courier ID:"));
        courierIdField = new JTextField();
        assignCourierPanel.add(courierIdField);
        assignCourierPanel.add(assignPickupButton);
        assignCourierPanel.add(assignDeliverButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "admin");
            }
        });
        assignCourierPanel.add(backButton);

        return assignCourierPanel;
    }

    private JPanel createUpdateStatusPanel() {
        JPanel updateStatusPanel = new JPanel(new GridLayout(0, 1));
        JButton updatePickupStatusButton = new JButton("Update Pickup Status");
        updatePickupStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatus(orderIdField.getText(), "1", true);
            }
        });
        JButton updateDeliverStatusButton = new JButton("Update Deliver Status");
        updateDeliverStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatus(orderIdField.getText(), "1", false);
            }
        });
        updateStatusPanel.add(new JLabel("Order ID:"));
        orderIdField = new JTextField();
        updateStatusPanel.add(orderIdField);
        updateStatusPanel.add(updatePickupStatusButton);
        updateStatusPanel.add(updateDeliverStatusButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "admin");
            }
        });
        updateStatusPanel.add(backButton);

        return updateStatusPanel;
    }

    private JPanel createGetOrderPanel() {
        JPanel getOrderPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(0, 1));
        JButton getOrderButton = new JButton("Get Order");
        getOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getOrder(getOrderIdField.getText());
            }
        });
        inputPanel.add(new JLabel("Order ID:"));
        getOrderIdField = new JTextField();
        inputPanel.add(getOrderIdField);
        inputPanel.add(getOrderButton);

        orderInfoArea = new JTextArea(15, 50);
        orderInfoArea.setLineWrap(true);
        orderInfoArea.setWrapStyleWord(true);
        orderInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderInfoArea);

        getOrderPanel.add(inputPanel, BorderLayout.NORTH);
        getOrderPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "admin");
            }
        });
        getOrderPanel.add(backButton, BorderLayout.SOUTH);

        return getOrderPanel;
    }

    private void login(String username, String password) {
        try {
            URL url = new URL("http://localhost:8000/api/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = String.format("{\"username\": \"%s\", \"pass\": \"%s\"}", username, password);
    
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
    
            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            System.out.println(response);
    
            if (response.contains("Login successful")) {
                // Parse response to check user type
                if (response.contains("\"type\":\"admin\"")) {
                    JOptionPane.showMessageDialog(this, "Login successful");
                    cardLayout.show(mainPanel, "admin");
                } else {
                    JOptionPane.showMessageDialog(this, "Non-Admin Login, Access denied");
                }
            } else {
                JOptionPane.showMessageDialog(this, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error logging in");
        }
    }
    
    private void createAdminAccount(String username, String password) {
        try {
            URL url = new URL("http://localhost:8000/api/adminaccount");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = String.format("{\"username\": \"%s\", \"pass\": \"%s\"}", username, password);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            JOptionPane.showMessageDialog(this, "Admin Created: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating admin account");
        }
    }

    private void addCourier(String name, String licensePlate) {
        try {
            URL url = new URL("http://localhost:8000/api/courier/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = String.format("{\"name\": \"%s\", \"license_plate\": \"%s\"}", name, licensePlate);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            JOptionPane.showMessageDialog(this, "Courier Added: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding courier");
        }
    }

    private void assignCourier(String orderId, String courierId, boolean isPickup) {
        try {
            String apiUrl = isPickup ? "http://localhost:8000/api/assign_pickup/" : "http://localhost:8000/api/assign_deliver/";
            String completeURL = apiUrl + orderId;
            URL url = new URL(completeURL);

            System.out.println("url: " + url);
            System.out.println("orderId: " + orderId);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String jsonInputString;
            if (isPickup) {
                jsonInputString = String.format("{\"courier_pickup_id\": \"%s\"}", courierId);
            } else {
                jsonInputString = String.format("{\"courier_deliver_id\": \"%s\"}", courierId);
            }


            System.out.println("apiURL: " + apiUrl);
            System.out.println("jsonInputString: " + jsonInputString);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            JOptionPane.showMessageDialog(this, "Courier Assigned: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error assigning courier");
        }
    }

    private void updateStatus(String orderId, String status, boolean isPickup) {
        try {
            String apiUrl = isPickup ? "http://localhost:8000/api/pickup/" : "http://localhost:8000/api/deliver/";
            URL url = new URL(apiUrl + orderId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = String.format("{\"status\": \"%s\"}", status);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            JOptionPane.showMessageDialog(this, "Status Updated: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating status");
        }
    }

    private void getOrder(String orderId) {
        try {
            URL url = new URL("http://localhost:8000/api/getorderid/" + orderId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray("response");
            String formattedResponse = jsonArray.toString(4); // 4 is the number of spaces for indentation

            orderInfoArea.setText(formattedResponse);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching order");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DesktopClient2().setVisible(true);
            }
        });
    }
}
