package model;

import renderOperation.RendererLine;

import java.util.ArrayList;
import java.util.List;

public class RegularPolygon {
    private PolyLine pl = new PolyLine();
    private int n = 3;
    private double alpha;
    private float r;
    private List<Point> points = new ArrayList<>();

    public void addPoint(Point point){
        points.add(point);
    }
    public void addPoint(int x, int y){
        addPoint(new Point(x,y));
    }
    public int getListSize(){
        return points.size();
    }
    public Point getPoint(int index){
        return points.get(index);
    }

    public void setN(int n) {
        if(n >= 3){
            this.n = n;
        }
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setR(float r) {
        this.r = r;
    }

    public RegularPolygon() {
    }

    public RegularPolygon(Point s, float r, double alpha,int n){
        points.add(s);
        setR(r);
        setAlpha(alpha);
        setN(n);
    }

    private void calculate(){
        for (int i = 0; i<n;i++){
            pl.addPoint((int)(points.get(0).getX() + r*Math.cos(alpha+i*Math.PI*2/n)),
                    (int)(points.get(0).getY() + r*Math.sin(alpha+i*Math.PI*2/n)));
        }
    }
    public void draw(RendererLine r1){
            calculate();
            pl.draw(r1);
            pl.clearPoints();
    }
}
