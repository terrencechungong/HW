package com.project1;

public class Desk {
    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Point p1, p2;
    Desk(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public String toString() {
        return String.format("%d %d %d %d", p1.x, p1.y, p2.x, p2.y);
    }
}
