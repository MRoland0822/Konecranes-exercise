import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MovingVehiclePanel extends JPanel {
    private List<Vehicle> vehicles;
    private Timer timer;
    private MovingVehicleGUI parent;

    public MovingVehiclePanel(MovingVehicleGUI parent) {
        this.parent = parent;
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.WHITE);

        vehicles = new ArrayList<>();
        timer = new Timer(50, e -> {
            moveVehicles();
            repaint();
            parent.updateVehicleList();
        });
        timer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectVehicle(e.getX(), e.getY());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Vehicle vehicle : vehicles) {
            vehicle.draw(g);
            g.setColor(Color.BLACK);
            g.drawString("X: " + vehicle.getX() + " Y: " + vehicle.getY(), vehicle.getX(), vehicle.getY() - 10);
        }
    }

    public void addVehicle(String name, int x, int y, int direction, int speed) {
        vehicles.add(new Vehicle(name, x, y, direction, speed, getWidth(), getHeight()));
    }

    private void moveVehicles() {
        for (Vehicle vehicle : vehicles) {
            vehicle.move();
        }
    }

    private void selectVehicle(int mouseX, int mouseY) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.contains(mouseX, mouseY)) {
                parent.selectVehicle(vehicle);
                break;
            }
        }
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
