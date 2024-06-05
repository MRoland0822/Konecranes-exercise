import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VehicleListWindow extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> vehicleList;
    private MovingVehicleGUI parent;

    public VehicleListWindow(MovingVehicleGUI parent) {
        this.parent = parent;
        setTitle("Vehicle List");
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        listModel = new DefaultListModel<>();
        vehicleList = new JList<>(listModel);
        vehicleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = vehicleList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        parent.selectVehicleByIndex(index);
                    }
                }
            }
        });

        add(new JScrollPane(vehicleList), BorderLayout.CENTER);
    }

    public void updateVehicleList(List<Vehicle> vehicles) {
        listModel.clear();
        for (Vehicle vehicle : vehicles) {
            listModel.addElement(vehicle.getName() + " at (" + vehicle.getX() + ", " + vehicle.getY() + "), Direction: " + vehicle.getDirection() + ", Speed: " + vehicle.getSpeed());
        }
    }
}
