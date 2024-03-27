 <%@page import='java.awt.*,java.awt.image.*,java.io.*,javax.imageio.*,javax.swing.*' %>
<%--WatermarkImage--%>
<%

File origFile = new File("D:\\Tomcat 9.0\\webapps\\Imagecaptcha\\images\\s1.jpg");
ImageIcon icon = new ImageIcon(origFile.getPath());
// create BufferedImage object of same width and height as of original image
BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(),
icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
// create graphics object and add original image to it
Graphics graphics = bufferedImage.getGraphics();
graphics.drawImage(icon.getImage(), 1, 0, null);
// set font for the watermark text
graphics.setFont(new Font("Arial", Font.BOLD, 30));
graphics.setColor(Color.BLACK);
//unicode characters for (c) is \u00a9
String watermark = "harsha";
// add the watermark text
graphics.drawString(watermark, 20, icon.getIconHeight()-50);
graphics.dispose();
File newFile = new File("D:\\Tomcat 9.0\\webapps\\Imagecaptcha\\images\\12.jpg");
try {
boolean write = ImageIO.write(bufferedImage, "jpg", newFile);
File newFile2 = new File("D:\\Tomcat 9.0\\webapps\\Imagecaptcha\\images\\11.jpg");
File newFile1 =newFile;
addWatermark(newFile1,newFile2);
} catch (IOException e) {
System.out.println(e.toString());
}
System.out.println(newFile.getPath() + " created successfully!");
%>
<%!private void addWatermark(File imageFile1,File imageFile2) {
    try {
        File watermarkFile = imageFile2;
        BufferedImage image = ImageIO.read(imageFile1);
        BufferedImage watermark = ImageIO.read(watermarkFile);
        Graphics g = image.getGraphics();
        g.drawImage(watermark, 0, 0, 50, 50, null);
        ImageIO.write(image, "jpg", imageFile1);
    } catch (IOException  e) {
        System.out.println("i2i:"+e.toString());
    }
}%>