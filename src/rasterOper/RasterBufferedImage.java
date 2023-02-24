package rasterOper;

import java.awt.image.BufferedImage;

public class RasterBufferedImage implements Raster {
    private BufferedImage bufferedImage;

    public RasterBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void drawPixel(int x, int y) {
        drawPixel(x,y,0x00ff00);
    }

    public void drawPixel(int x, int y,int color) {
        if((bufferedImage.getWidth() > x) && (bufferedImage.getHeight() > y ) &&(x>=0) && y>=0){
            bufferedImage.setRGB(x,y,color);
        }
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }
}