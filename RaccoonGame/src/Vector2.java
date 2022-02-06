public class Vector2 {
    public double x;
    public double y;

    Vector2() {}

    Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void normalize() {
        double hypotenuse = Math.sqrt(getX() * getX() + getY() * getY());

        setX(getX()/hypotenuse);
        setY(getY()/hypotenuse);
    }

}
