package jntu;
import java.awt.*;
import java.net.*;
import java.awt.image.*;
public class ColorFilter implements MyFilter {
public BufferedImage processImage(BufferedImage image) {
float[][] colorMatrix = { { 1f, 0f, 0f }, { 0.5f, 1.0f, 0.5f }, { 0.2f, 0.4f, 0.6f } };
BandCombineOp changeColors = new BandCombineOp(colorMatrix, null);
Raster sourceRaster = image.getRaster();
WritableRaster displayRaster = sourceRaster.createCompatibleWritableRaster();
changeColors.filter(sourceRaster, displayRaster);
return new BufferedImage(image.getColorModel(), displayRaster, true, null);

}
}

