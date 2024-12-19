public class Particle {
    public Vector3d position;
    public Vector3d velocity;
    public double mass;

    public Particle(double x, double y, double z, double mass) {
        this.position = new Vector3d(x, y, z);
        this.velocity = Vector3d.ZERO;
        this.mass = mass;
    }

    public Particle(Vector3d position, double mass) {
        this(position.x, position.y, position.z, mass);
    }

    public void tick(Vector3d netForce, double steps) {
        Vector3d acceleration = netForce.divide(mass);
        acceleration = acceleration.multiply(steps);
        velocity = velocity.add(acceleration);
        position = position.add(velocity.multiply(steps));
    }

    public Vector3d calculateGravitationalPull(Particle other) {
        final double G = 6.67430e-11; // Gravitational constant
        Vector3d displacement = other.position.subtract(this.position);
        double distance = displacement.magnitude();

        if (distance == 0) {
            return Vector3d.ZERO; // Avoid division by zero
        }

        double forceMagnitude = G * this.mass * other.mass / (distance * distance);
        return displacement.multiply(forceMagnitude / distance);
    }
}
