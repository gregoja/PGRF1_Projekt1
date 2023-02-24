package renderOperation;

import rasterOper.Raster;

import java.awt.image.BufferedImage;

public class Renderer {
    protected Raster raster;

    Renderer(Raster raster){
        setRaster(raster);
    }
    void setRaster(Raster raster){
        this.raster = raster;
    }
}