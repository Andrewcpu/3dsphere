import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class World {
    public List<Particle> particles;
    public World() {
        particles = new ArrayList<>();
    }


    public void tick(double timeStep) {
        // Calculate net forces on each particle
        Map<Particle, Vector3d> forceMap = particles.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        particle -> particles.stream()
                                .filter(other -> other != particle) // Exclude self
                                .map(particle::calculateGravitationalPull)
                                .reduce(Vector3d.ZERO, Vector3d::add) // Sum forces
                ));
        // Update each particle based on net forces
        forceMap.forEach((particle, netForce) -> particle.tick(netForce, timeStep));
    }

    public List<Vector3d> generateSpherePoints(Vector3d center, double radius, int numPoints) {
        List<Vector3d> points = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numPoints; i++) {
            double u = random.nextDouble();
            double v = random.nextDouble();

            double theta = 2 * Math.PI * u; // Random angle around the sphere
            double phi = Math.acos(2 * v - 1); // Random angle from pole to pole

            double x = radius * Math.sin(phi) * Math.cos(theta);
            double y = radius * Math.sin(phi) * Math.sin(theta);
            double z = radius * Math.cos(phi);

            points.add(new Vector3d(center.x + x, center.y + y, center.z + z));
        }

        return points;
    }
}
