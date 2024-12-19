class Camera {
    public Vector3d position;
    private double fov; // Field of view in degrees
    private double yaw = 0, pitch = 0;

    public Camera(Vector3d position, double fov) {
        this.position = position;
        this.fov = fov;
    }
    public Vector3d project(Vector3d point, int screenWidth, int screenHeight) {
        Vector3d relativePosition = point.subtract(position);

        // Apply rotation for yaw (horizontal rotation)
        double yawRadians = Math.toRadians(yaw);
        double cosYaw = Math.cos(yawRadians);
        double sinYaw = Math.sin(yawRadians);
        double rotatedX = cosYaw * relativePosition.x - sinYaw * relativePosition.z;
        double rotatedZ = sinYaw * relativePosition.x + cosYaw * relativePosition.z;

        // Apply rotation for pitch (vertical rotation)
        double pitchRadians = Math.toRadians(pitch);
        double cosPitch = Math.cos(pitchRadians);
        double sinPitch = Math.sin(pitchRadians);
        double rotatedY = cosPitch * relativePosition.y - sinPitch * rotatedZ;
        rotatedZ = sinPitch * relativePosition.y + cosPitch * rotatedZ;

        if (rotatedZ <= 0) {
            return null; // Points behind the camera are not visible
        }

        double scale = (screenWidth / 2.0) / Math.tan(Math.toRadians(fov / 2.0));
        double x = scale * (rotatedX / rotatedZ) + screenWidth / 2.0;
        double y = scale * (rotatedY / rotatedZ) + screenHeight / 2.0;

        return new Vector3d(x, y, rotatedZ);
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }
}
