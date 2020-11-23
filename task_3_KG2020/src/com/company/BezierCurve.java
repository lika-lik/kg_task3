package com.company;

import com.company.line.LineDrawer;
import com.company.point.RealPoint;
import com.company.point.ScreenConverter;
import com.company.point.ScreenPoint;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class BezierCurve {
    ArrayList<RealPoint> finalPoints = new ArrayList<>();

    public ArrayList<RealPoint> getFinalPoints() {
        return finalPoints;
    }

    public BezierCurve(ArrayList<RealPoint> sourcePoints) {
        for (double t=0; t<=1; t += 0.01)
            finalPoints.add(calculateBezierFunction(t, sourcePoints));
    }


    private RealPoint calculateBezierFunction(double t, ArrayList<RealPoint> sourcePoints)
    {   double x = 0;
        double y = 0;

        int n = sourcePoints.size() - 1;
        for (int i=0; i <= n; i++)
        {
            x += fact(n)/(fact(i)*fact(n-i)) * sourcePoints.get(i).getX() * Math.pow(t, i) * Math.pow(1-t, n-i);
            y += fact(n)/(fact(i)*fact(n-i)) * sourcePoints.get(i).getY() * Math.pow(t, i) * Math.pow(1-t, n-i);
        }
        return new RealPoint(x, y);
    }

    private double fact(double arg){
        if (arg == 0) return 1;

        double result = 1;
        for (int i=1; i<=arg; i++)
            result *= i;
        return result;
    }

    public void draw(LineDrawer ld, ScreenConverter sc){
        for (int i = 1; i < finalPoints.size(); i++)
        {
            double x1 = (finalPoints.get(i-1).getX());
            double y1 = (finalPoints.get(i-1).getY());
            double x2 = (finalPoints.get(i).getX());
            double y2 = (finalPoints.get(i).getY());
            ScreenPoint point1 = sc.r2s(new RealPoint(x1, y1));
            ScreenPoint point2 = sc.r2s(new RealPoint(x2, y2));
            ld.drawLine(point1, point2);
        }
    }


}
