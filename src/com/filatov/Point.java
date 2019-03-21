package com.filatov;

public class Point {
    private double x;
    private double y;
    private String date;

    public Point(double x, double y, String date){
        this.x = x;
        this.y = y;
        this.date = date;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getDate(){ return date; }

    @Override
    public String toString() {
        return "x=" + x + " y=" + y + " date=" + date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Double.compare(point.x, x) != 0) return false;
        if (Double.compare(point.y, y) != 0) return false;
        return date != null ? date.equals(point.date) : point.date == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
