package model;

import renderOperation.RendererLine;

import java.util.ArrayList;
import java.util.List;

public class PolyLine {
    private List<Point> list;

    public PolyLine() {
        this.list = new ArrayList<>();
    }

    public Point getPoint(int index){
        return list.get(index);
    }

    public int getListSize(){
        return list.size();
    }

    void clearPoints(){
        list.clear();
    }
    public void addPoint(Point point){
        list.add(point);
    }

    public void addPoint(int x, int y){
        list.add(new Point(x,y));
    }

    public void draw(RendererLine rendererLine){
        for(int i = 0; i< list.size();i++){
          rendererLine.drawLine(list.get(i).getX(),list.get(i).getY(),
                  list.get((i+1)%list.size()).getX(),list.get((i+1)%list.size()).getY());
        }
    }
}
