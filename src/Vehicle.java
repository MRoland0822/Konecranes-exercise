import java.awt.*;
import java.util.Random;

public class Vehicle {
    private String name;
    private int x, y, direction, speed;
    private Color color;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 30;
    private int panelWidth, panelHeight;

    public Vehicle(String name, int x, int y, int direction, int speed, int panelWidth, int panelHeight ) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.color = generateRandomColor();
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, WIDTH, HEIGHT);

        // Draw wheels
        g.setColor(Color.BLACK);
        g.fillOval(x + 5, y + 25, 10, 10);
        g.fillOval(x + 35, y + 25, 10, 10);

        // Draw name
        g.setColor(Color.BLUE);
        g.drawString(name, x + 10, y + 20);
    }

    public void move() {
        switch (direction) {
            case 0: // East
                x += speed;
                if (x + WIDTH > panelWidth) {
                    x = panelWidth - WIDTH;
                }
                break;
            case 1: // South
                y += speed;
                if (y + HEIGHT > panelHeight) {
                    y = panelHeight - HEIGHT;
                }
                break;
            case 2: // West
                x -= speed;
                if (x < 0) {
                    x = 0;
                }
                break;
            case 3: // North
                y -= speed;
                if (y < 0) {
                    y = 0;
                }
                break;
        }
    }

    public boolean contains(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + WIDTH && mouseY >= y && mouseY <= y + HEIGHT;
    }

    private Color generateRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }
}
