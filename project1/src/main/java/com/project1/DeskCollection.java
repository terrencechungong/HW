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
        if (l1.x >= r2.x || l2.x >= r1.x) {
            return false;
        }
        return !(l1.y <= r2.y || l2.y <= r1.y);
    }

    public static boolean doOverlap(Desk d1, Desk d2) {
        return doOverlap(d1.p1, d1.p2, d2.p1, d2.p2);
    }
}
