package com.project1;

import java.util.ArrayList;

import com.project1.Desk.Point;

public class DeskCollection {
    ArrayList<Desk> desks;

    public DeskCollection() {
        this.desks = new ArrayList<Desk>();
    }

    /**
     * Adds a desk to desks if the desk doesn't overlap with any other desk.
     * @param newDesk
     * @return true if the desk can be added, false otherwise
    */
    public boolean addDesk(Desk newDesk) {
        for (Desk desk : desks) {
            if (doOverlap(desk, newDesk)) {
                return false;
            }
        }
        desks.add(newDesk);
        return true;
    }
    
    /**
     * Checks if two desks overlap
     * @param l1 top left point of the first desk
     * @param r1 bottom right point of the first desk
     * @param l2 top left point of the second desk
     * @param r2 bottom right point of the second desk
     * @return true if the desks overlap, false if not
     */
    public static boolean doOverlap(Point l1, Point r1, Point l2, Point r2) {
        // 19 cases
        Point l3 = new Point(l1.x, l1.y); // bottom left of desk one
        Point r3 = new Point(r1.x, r1.y);  // top right of desk one
        Point l4 = new Point(l2.x, l2.y); // bottom left of desk two
        Point r4 = new Point(r2.x, r2.y); // top right of desk two

        return true;
    }

    public static boolean doOverlap(Desk d1, Desk d2) {
        return doOverlap(d1.p1, d1.p2, d2.p1, d2.p2);
    }
}
