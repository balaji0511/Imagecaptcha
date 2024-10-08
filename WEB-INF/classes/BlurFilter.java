package jntu;
import java.awt.*;
import java.awt.image.*;
import java.net.*;

public class BlurFilter implements MyFilter {
public BufferedImage processImage(BufferedImage image) {
float[] blurMatrix = { 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f,
1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f };
BufferedImageOp blurFilter = new ConvolveOp(new Kernel(3, 3, blurMatrix),
ConvolveOp.EDGE_NO_OP, null);
return blurFilter.filter(image, null);
}
}
