import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class BasicDraw {
public static void main(String[] args) {
JFrame frame = new JFrame();
frame.add(new MyComponent());
frame.setSize(300, 300);
frame.setVisible(true);
}
}

class MyComponent extends JComponent {
public void paint(Graphics g) {
Graphics2D g2d = (Graphics2D) g;
Shape shape = new java.awt.geom.Ellipse2D.Float(20, 20, 200,200);

g2d.setClip(shape);

int x = 0;
int y = 0;
g2d.drawImage(new ImageIcon("a.png").getImage(), x, y, this);
}
}


