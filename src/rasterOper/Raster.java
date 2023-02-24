package rasterOper;

public interface Raster {
    void drawPixel(int x, int y, int color);
    void drawPixel(int x, int y);
    int getWidth();
    int getHeight();
}