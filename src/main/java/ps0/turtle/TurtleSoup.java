/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package ps0.turtle;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class TurtleSoup {
    /**
     * Draw a square.
     *
     * @param turtle     the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        final double squareCornerAngle = 90;
        final int squareSides = 4;

        for (int i = 0; i < squareSides; i++) {
            turtle.forward(sideLength);
            turtle.turn(squareCornerAngle);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * <p>
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     *
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        if (sides <= 2) {
            throw new IllegalArgumentException("Sides must be > 2");
        }

        double interiorAnglesSum = (sides - 2) * 180;

        return interiorAnglesSum / sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * <p>
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     *
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        if (angle < 1 || angle > 179) {
            throw new IllegalArgumentException("Angle must be 0 < angle < 180");
        }

        return (int) Math.ceil(360 / (180 - angle));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * <p>
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     *
     * @param turtle     the turtle context
     * @param sides      number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double externalPolygonAngle = 180 - calculateRegularPolygonAngle(sides);

        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(externalPolygonAngle);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * <p>
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360.
     * <p>
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     *
     * @param currentHeading current direction as clockwise from north
     * @param currentX       current location x-coordinate
     * @param currentY       current location y-coordinate
     * @param targetX        target point x-coordinate
     * @param targetY        target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     * must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double deltaX = targetX - currentX;
        double deltaY = targetY - currentY;

        double atan2NorthAngleRadian = Math.atan2(deltaX, deltaY) + (2 * Math.PI);
        double atan2NorthAngleDegree = Math.toDegrees(atan2NorthAngleRadian);
        double turnAngle = ((atan2NorthAngleDegree - currentHeading) + 360) % 360; // 0 <= angle < 360

        return turnAngle;
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * <p>
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     *
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     * otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        int currentX = xCoords.get(0);
        int currentY = yCoords.get(0);
        double currentHeading = calculateHeadingToPoint(0, 0, 0, currentX, currentY);
        double turnAngle;

        List<Double> turnList = new ArrayList<>();

        for (int i = 1; i < xCoords.toArray().length; i++) {

            turnAngle = calculateHeadingToPoint(currentHeading, currentX, currentY, xCoords.get(i), yCoords.get(i));

            currentHeading += turnAngle;

            currentX = xCoords.get(i);
            currentY = yCoords.get(i);

            turnList.add(turnAngle);
        }

        return turnList;
    }

    /**
     * Draw your personal, custom art.
     * <p>
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     *
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        List<Integer> xCoords = new ArrayList<>();
        List<Integer> yCoords = new ArrayList<>();
        List<Integer> starAngles = new ArrayList<>(List.of(0, 144, 288, 72, 216, 0));
        List<Double> headingList;

        for (int i : starAngles) {

            double angleToRadian = Math.toRadians(i);

            double x = Math.sin(angleToRadian) * 100;
            double y = Math.cos(angleToRadian) * 100;

            xCoords.add((int) Math.round(x));
            yCoords.add((int) Math.round(y));
        }

        System.out.println(xCoords);
        System.out.println(yCoords);
        headingList = calculateHeadings(xCoords, yCoords);

        System.out.println(headingList);

        for (double degree : headingList) {
            turtle.turn(degree);
            turtle.forward(200);
        }
    }

    /**
     * Main method.
     * <p>
     * This is the method that runs when you run "java TurtleSoup".
     *
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawPersonalArt(turtle); //star

        //draw the window
        turtle.draw();
    }
}
