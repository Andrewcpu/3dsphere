import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RenderCanvas extends JComponent {
    private final World world;
    private final Camera camera;
    private final int width;
    private final int height;

    public RenderCanvas(World world, Camera camera, int width, int height) {
        this.world = world;
        this.camera = camera;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        world.particles
                .stream()
                .map(e -> camera.project(e.position, width, height))
                .filter(Objects::nonNull)
                .forEach(screenPosition -> {
                    double distance = screenPosition.z;
                    int size = (int) Math.max(1, 1000 / distance); // Adjust size based on distance
                    g.fillOval(((int) screenPosition.x) - size / 2, ((int) screenPosition.y) - size / 2, size, size); // Render particle with perspective
                });
    }

    public static void main(String[] args) {
        World world = new World();
        world.particles.addAll(world.generateSpherePoints(Vector3d.ZERO, 250, 1024)
                .stream()
                .map(n -> new Particle(n, 5e6))
                .toList());
        Particle e1 = new Particle(Vector3d.ZERO, 5e12);
        world.particles.add(e1);

        Camera camera = new Camera(new Vector3d(0, 0, -500), 90);

        JFrame frame = new JFrame("3D Particle Renderer");
        RenderCanvas renderer = new RenderCanvas(world, camera, 800, 800);
        frame.add(renderer);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Timer(16, e -> {
            world.tick(1); // Update world with a fixed time step

            // Update camera to point to the target particle
            Vector3d relativePosition = e1.position.subtract(camera.position);
            double distance = relativePosition.magnitude();

            if (distance > 50) {
                relativePosition = relativePosition.multiply(50 / distance);
                camera.position = (e1.position.subtract(relativePosition));
            }

            double yaw = Math.toDegrees(Math.atan2(relativePosition.x, relativePosition.z));
            double pitch = Math.toDegrees(Math.atan2(relativePosition.y, Math.sqrt(relativePosition.x * relativePosition.x + relativePosition.z * relativePosition.z)));
            camera.setYaw(yaw);
            camera.setPitch(pitch);
            renderer.repaint();
        }).start();
    }
}
