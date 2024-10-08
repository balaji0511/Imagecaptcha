package jntu;
import java.awt.*;
import java.net.*;
import java.awt.image.*;
public class SharpenFilter implements MyFilter {
public BufferedImage processImage(BufferedImage image) {
float[] sharpenMatrix = { 0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f, -1.0f, 0.0f };
BufferedImageOp sharpenFilter = new ConvolveOp(new Kernel(3, 3, sharpenMatrix),
ConvolveOp.EDGE_NO_OP, null);
return sharpenFilter.filter(image, null);
}
}

