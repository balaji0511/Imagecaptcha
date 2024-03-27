<%@ page import='java.awt.*,java.util.*,java.net.*,java.awt.image.*,javax.imageio.ImageIO,java.io.*,jntu.*'%>

<%! 
void captcha(int opt,int fr) throws Exception
{
String cpath= getServletContext().getRealPath("/");

MyFilter invertFilter = new InvertFilter();
MyFilter sharpenFilter = new SharpenFilter();
MyFilter blurFilter = new BlurFilter();
MyFilter colorFilter = new ColorFilter();
  java.util.Random r=new java.util.Random();
int option=opt;//r.nextInt(10);
System.out.println("option="+option);

MyFilter filter=invertFilter;
FileInputStream in=new FileInputStream(cpath+"//images//a//"+fr+".jpg");
BufferedImage displayImage=ImageIO.read(in);



if(option==0)
{
filter=invertFilter;
displayImage = filter.processImage(displayImage);
}
else if(option==1)
{
filter=colorFilter ;
displayImage = filter.processImage(displayImage);
}
else if(option==2)
{
FileInputStream in1=new FileInputStream(cpath+"//images//a//"+(fr+1)+".jpg");
BufferedImage image1=ImageIO.read(in1);

Graphics2D g2d = (Graphics2D)image1.getGraphics();
Shape shape = new java.awt.geom.Ellipse2D.Float(1, 10, 200,200);
g2d.setClip(shape);
int x = 10;
int y = 10;
in1.close();
g2d.drawImage(displayImage, x, y, null);
displayImage=image1;
}
else if(option==3)
{
int[] rr={45,60,90,180};
FileInputStream in1=new FileInputStream(cpath+"//images//a//"+(fr+1)+".jpg");
BufferedImage image1=ImageIO.read(in1);

Graphics2D g2d = (Graphics2D)image1.getGraphics();
g2d.rotate(Math.toRadians(rr[new Random().nextInt(4)]),(double)image1.getWidth()/2,(double)image1.getHeight()/2);
in1.close();
g2d.drawImage(displayImage, 0, 0, null);
displayImage=image1;
}
else
{
	displayImage = displayImage.getSubimage(5, 5,( displayImage.getWidth()/2)+(displayImage.getWidth()/4), (displayImage.getHeight()/2)+(displayImage.getHeight()/4));
}

FileOutputStream outt=new FileOutputStream(cpath+"//images//b//"+fr+".jpg");

ImageIO.write(displayImage ,"JPG",outt);
in.close();
outt.close();

}
%>

