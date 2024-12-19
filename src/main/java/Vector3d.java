public class Vector3d {
    public static final Vector3d ZERO = new Vector3d(0,0,0);
    public double x;
    public double y;
    public double z;
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d add(Vector3d vector) {
        return new Vector3d(x + vector.x, y + vector.y, z + vector.z);
    }

    public Vector3d subtract(Vector3d vector) {
        return new Vector3d(x - vector.x, y - vector.y, z - vector.z);
    }

    public Vector3d multiply(double scalar) {
        return new Vector3d(x * scalar, y * scalar, z * scalar);
    }

    public Vector3d divide(double scalar) {
        return new Vector3d(x / scalar, y / scalar, z / scalar);
    }

    public double dot(Vector3d vector) {
        return x * vector.x + y * vector.y + z * vector.z;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
