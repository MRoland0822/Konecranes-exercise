import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MovingVehicleGUI extends JFrame implements ActionListener {
    private MovingVehiclePanel vehiclePanel;
    private JButton addVehicleButton;
    private JButton autoAddVehicleButton;
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private Vehicle selectedVehicle;
    private VehicleListWindow vehicleListWindow;
    private int vehicleCount = 1;

    public MovingVehicleGUI() {
        setTitle("Moving Vehicles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        vehiclePanel = new MovingVehiclePanel(this);
        add(vehiclePanel, BorderLayout.CENTER);

        addVehicleButton = new JButton("Add Vehicle");
        addVehicleButton.addActionListener(this);

        autoAddVehicleButton = new JButton("Auto Add Vehicle");
        autoAddVehicleButton.addActionListener(this);

        JPanel controlPanel = new JPanel();
        controlPanel.add(addVehicleButton);
        controlPanel.add(autoAddVehicleButton);
        add(controlPanel, BorderLayout.NORTH);

        JPanel directionPanel = new JPanel();
        directionPanel.setLayout(new GridLayout(2, 3));
        upButton = new JButton("Up");
        downButton = new JButton("Down");
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");

        upButton.addActionListener(e -> changeVehicleDirection(3));
        downButton.addActionListener(e -> changeVehicleDirection(1));
        leftButton.addActionListener(e -> changeVehicleDirection(2));
        rightButton.addActionListener(e -> changeVehicleDirection(0));

        directionPanel.add(new JLabel());
        directionPanel.add(upButton);
        directionPanel.add(new JLabel());
        directionPanel.add(leftButton);
        directionPanel.add(downButton);
        directionPanel.add(rightButton);

        add(directionPanel, BorderLayout.SOUTH);
        directionPanel.setVisible(false);

        vehicleListWindow = new VehicleListWindow(this);
        vehicleListWindow.setVisible(true);
    }

    public void createAndShowGUI() {
        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addVehicleButton) {
            showAddVehicleDialog();
        }else if (e.getSource() == autoAddVehicleButton) { // Check if auto add button clicked
            addRandomVehicle();
        }
    }

    private void showAddVehicleDialog() {
        JTextField nameField = new JTextField("Vehicle" + vehicleCount++, 5);
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);
        JTextField directionField = new JTextField(5);
        JTextField speedField = new JTextField(5);
        JTextField countField = new JTextField("1", 5);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("X:"));
        panel.add(xField);
        panel.add(new JLabel("Y:"));
        panel.add(yField);
        panel.add(new JLabel("Direction (0-East, 1-South, 2-West, 3-North):"));
        panel.add(directionField);
        panel.add(new JLabel("Speed:"));
        panel.add(speedField);
        panel.add(new JLabel("Number of Vehicles:")); // Label for vehicle count
        panel.add(countField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Enter Vehicle Parameters", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                int direction = Integer.parseInt(directionField.getText());
                int speed = Integer.parseInt(speedField.getText());
                int count = Integer.parseInt(countField.getText());
                for (int i = 0; i < count; i++) {
                    vehiclePanel.addVehicle(name + "_" + (i + 1), x, y, direction, speed); // Add multiple vehicles with unique names
                }
//                vehiclePanel.addVehicle(name, x, y, direction, speed);
                vehicleListWindow.updateVehicleList(vehiclePanel.getVehicles());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numerical values.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void selectVehicle(Vehicle vehicle) {
        selectedVehicle = vehicle;
        JPanel directionPanel = (JPanel) getContentPane().getComponent(2);
        directionPanel.setVisible(true);
    }

    public void selectVehicleByIndex(int index) {
        selectedVehicle = vehiclePanel.getVehicles().get(index);
        showModifyVehicleDialog(selectedVehicle);
    }

    private void showModifyVehicleDialog(Vehicle vehicle) {
        JTextField nameField = new JTextField(vehicle.getName(), 5);
        JTextField xField = new JTextField(String.valueOf(vehicle.getX()), 5);
        JTextField yField = new JTextField(String.valueOf(vehicle.getY()), 5);
        JTextField directionField = new JTextField(String.valueOf(vehicle.getDirection()), 5);
        JTextField speedField = new JTextField(String.valueOf(vehicle.getSpeed()), 5);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("X:"));
        panel.add(xField);
        panel.add(new JLabel("Y:"));
        panel.add(yField);
        panel.add(new JLabel("Direction (0-East, 1-South, 2-West, 3-North):"));
        panel.add(directionField);
        panel.add(new JLabel("Speed:"));
        panel.add(speedField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modify Vehicle Parameters", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                int direction = Integer.parseInt(directionField.getText());
                int speed = Integer.parseInt(speedField.getText());
                vehicle.setName(name);
                vehicle.setX(x);
                vehicle.setY(y);
                vehicle.setDirection(direction);
                vehicle.setSpeed(speed);
                vehicleListWindow.updateVehicleList(vehiclePanel.getVehicles());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numerical values.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changeVehicleDirection(int direction) {
        if (selectedVehicle != null) {
            selectedVehicle.setDirection(direction);
        }
    }

    public void updateVehicleList() {
        vehicleListWindow.updateVehicleList(vehiclePanel.getVehicles());
    }

    private void addRandomVehicle() {
        Random random = new Random();
        String name = "Vehicle" + vehicleCount++;
        int x = random.nextInt(vehiclePanel.getWidth());
        int y = random.nextInt(vehiclePanel.getHeight());
        int direction = random.nextInt(4);
        int speed = random.nextInt(5) + 1; // Speed between 1 and 5
        vehiclePanel.addVehicle(name, x, y, direction, speed);
        vehicleListWindow.updateVehicleList(vehiclePanel.getVehicles());
    }
}
