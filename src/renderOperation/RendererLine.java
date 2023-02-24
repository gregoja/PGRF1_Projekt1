package renderOperation;

import rasterOper.Raster;

public class RendererLine extends Renderer{

    public RendererLine(Raster raster){
        super(raster);
    }

    public void drawLineTriv(int x1, int y1, int x2, int y2) {

        int dx = Math.abs(x2-x1);
        int dy = Math.abs (y2-y1);

        if(dx<=dy && dx!=0){  // y axis
            if(y1>=y2){int temporary = y1; y1=y2; y2=temporary; temporary = x1; x1=x2;x2=temporary;}
            double k = (y2-y1)/(double)(x2-x1);
            double q=y1-k*x1;
            for (int y = y1; y <=y2 ; y++) {
                raster.drawPixel((int)((y-q)/k),y);
            }
        }else if(dx>dy && dx!=0){
            if(x1>=x2){int temporary = x1; x1=x2; x2= temporary;temporary = y1;y1=y2;y2=temporary;}
            double k = (y2-y1)/(double)(x2-x1);
            double q=y1-k*x1;
            for (int x=x1;x<=x2;x++){
                raster.drawPixel(x,(int)(k*x+q));
            }
        }else if (dx == 0){
            if(y1>=y2){int temporary = y1; y1=y2; y2=temporary; temporary = x1; x1=x2;x2=temporary;}
            for(int y=y1;y<=y2;y++){
                raster.drawPixel(x1,y);
            }
        }
    }

    public void drawLine(int x1,int y1,int x2,int y2){
        drawLine(x1,y1,x2,y2,0x00ff00);
    }
    public void drawLine(int x1,int y1,int x2,int y2,int color){
        int dx = x2 - x1;
        int dy = y2 - y1;

        if(Math.abs(dx) >= Math.abs(dy)){   // x axis
            if(x1>x2){int temporary = x1; x1=x2; x2= temporary;temporary = y1;y1=y2;y2=temporary;}
            double k = dy/(double)dx;
            double y = y1;

            for(int x=x1;x<=x2;x++){
                raster.drawPixel(x,(int)y,color);
                y+=k;
            }
        }else{  // y axis
            if(y1>y2){int temporary = y1; y1=y2;y2=temporary; temporary = x1; x1=x2; x2=temporary;}
            double k = dx/(double)dy;
            double x = x1;

            for(int y=y1;y<=y2;y++){
                raster.drawPixel((int)x,y,color);
                x+=k;
            }
        }
    }
}
