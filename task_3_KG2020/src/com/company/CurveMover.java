package com.company;

import com.company.point.RealPoint;
import java.util.ArrayList;

public class CurveMover {
    private ArrayList<RealPoint> lineFrom;
    private ArrayList<RealPoint> lineTo;

    public CurveMover(BezierCurve curveFrom, BezierCurve curveTo) {
        this.lineFrom = curveFrom.getFinalPoints();
        this.lineTo = curveTo.getFinalPoints();
        int n = Math.max(lineFrom.size(), lineTo.size());
        if (lineFrom.size() < n){
            addPointsToN(lineFrom, n);
        }else if (lineTo.size() < n)
            addPointsToN(lineTo, n);
    }

    private void addPointsToN(ArrayList<RealPoint> line, int n){
        int k = n - line.size();
        RealPoint lastPoint = line.get(line.size()-1);
        for (int i = 0; i < k; i ++)
            line.add(lastPoint);
    }

    public ArrayList<RealPoint> change(double timePast){
        ArrayList<RealPoint> lineMove = new ArrayList<>();
        RealPoint pFrom, pTo;
        double xMove, yMove;
        double delX, delY;
        for (int i = 0; i < lineFrom.size(); i ++) {
            pFrom = lineFrom.get(i);
            pTo = lineTo.get(i);
            delX = (pTo.getX() - pFrom.getX());
            delY = (pTo.getY() - pFrom.getY());
            xMove = (pFrom.getX() + delX*timePast);
            yMove = (pFrom.getY() + delY*timePast);
            lineMove.add(new RealPoint(xMove, yMove));
        }
        return lineMove;
    }
}
