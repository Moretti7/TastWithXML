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

    @Override
    public String toString() {
        return "x=" + x + " y=" + y + " date=" + date;
    }
}
