package com.company;

import com.company.line.DDALineDrawer;
import com.company.line.LineDrawer;
import com.company.point.RealPoint;
import com.company.point.ScreenConverter;
import com.company.point.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

    private ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
    private ArrayList<RealPoint> sourcePoints1 = new ArrayList<>();
    private ArrayList<RealPoint> sourcePoints2 = new ArrayList<>();
    private Timer timer;

    private boolean is1lineComplete = false;
    private boolean is2lineComplete = false;
    private  double timeCur = 0;
    private double timeCount;

    public void setTime(int time) {
        this.timeCount = time;
    }

    public void setIs1lineComplete(boolean is1lineComplete) {
        this.is1lineComplete = is1lineComplete;
    }

    public void setIs2lineComplete(boolean is2lineComplete) {
        this.is2lineComplete = is2lineComplete;
    }

    public void startTimer(){
        timer.start();
    }

    public DrawPanel(){
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeCur >= 1)
                    return;
                timeCur += 1 / timeCount;
                if (timeCur > 1)
                    timeCur = 1;
                repaint();
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        sc.setsW(getWidth());
        sc.setsH(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics2D gr = bi.createGraphics();
        gr.setColor(Color.white);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.dispose();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);

        BezierCurve bc1 = new BezierCurve(sourcePoints1);
        bc1.draw(ld, sc);

        ld.setColor(Color.black);

        BezierCurve bc2 = new BezierCurve(sourcePoints2);
        bc2.draw(ld, sc);

        if (is1lineComplete && is2lineComplete){
            ld.setColor(Color.red);
            CurveMover lt = new CurveMover(new BezierCurve(sourcePoints1), new BezierCurve(sourcePoints2));
            BezierCurve bc = new BezierCurve(lt.change(timeCur));
            bc.draw(ld, sc);
        }
        g.drawImage(bi, 0, 0, null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!is1lineComplete) {
            sourcePoints1.add(sc.s2r(new ScreenPoint(e.getX(), e.getY())));
        }else if(!is2lineComplete){
            sourcePoints2.add(sc.s2r(new ScreenPoint(e.getX(), e.getY())));
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private RealPoint oldPoint = null;

    @Override
    public void mouseDragged(MouseEvent e) {
        RealPoint newPoint = sc.s2r(new ScreenPoint(e.getX(), e.getY()));
        if(oldPoint != null){
            double dx = newPoint.getX() - oldPoint.getX();
            double dy = newPoint.getY() - oldPoint.getY();
            sc.setrX(sc.getrX()-dx);
            sc.setrY(sc.getrY()-dy);
            repaint();
        }
        oldPoint = newPoint;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
}
