import javax.swing.*;

 class MovingVehicleApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MovingVehicleGUI gui = new MovingVehicleGUI();
            gui.createAndShowGUI();
        });
    }
}
