
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Img {
    private BufferedImage img = null;
    public boolean isValid = false;
    private int height;
    private int width;
    private int crop_size = 256;

    public Img(int crop_size) {
        this.crop_size = crop_size;
    }

    public Img(String file) {

        isValid = false;

        try {

            this.img = ImageIO.read(new File(file));
            isValid = true;
            height = img.getHeight();
            width = img.getWidth();

        } catch (IOException e) {

            isValid = false;

        }
    }

//    public BufferedImage rescale(int newW, int newH){
//
//        BufferedImage oldImg = this.img;
//
//        Image tmp = oldImg.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
//        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
//
//        Graphics2D g2d = dimg.createGraphics();
//        g2d.drawImage(tmp, 0, 0, null);
//        g2d.dispose();
//
//        return dimg;
//
//    }

    public BufferedImage rescale( int newW, int newH) {

        BufferedImage oldImg = this.img;

        Image tmp = oldImg.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        int w = oldImg.getWidth();
        int h = oldImg.getHeight();
        if(w > h) {
            newH = newH * h / w;
        } else if(h > w){
            newW = newH * w / h;
        }

        Graphics2D    graphics = dimg.createGraphics();
        graphics.setPaint( new Color(0,0,0));
        graphics.fillRect ( 0, 0,Math.max(newH,newW),Math.max(newH,newW));
        graphics.drawImage(oldImg, 0, 0, newW, newH, 0, 0, oldImg.getWidth(), oldImg.getHeight(), null);
        graphics.dispose();

        return dimg;

    }

    public BufferedImage randomCropByCropSize(){

        BufferedImage oldImg = this.img;

        int w = oldImg.getWidth();
        int h = oldImg.getHeight();

        if( w <= crop_size + 1|| h <= crop_size + 1){
            return rescale(crop_size,crop_size);
        }

        Random rand = new Random();
        int x = rand.nextInt(w - crop_size) + 1;//width
        int y = rand.nextInt(h - crop_size) + 1;//height

        return oldImg.getSubimage(x,y,crop_size,crop_size);

    }

    public int[][][] toArrayImg(){

        int arr[][][] = new int[crop_size][crop_size][3];

        BufferedImage imgForArr = rescale(crop_size,crop_size);

        for (int i = 0; i < crop_size ; i++) {
            for (int j = 0; j < crop_size; j++) {
                arr[i][j][0] = getRed(imgForArr,i,j);
                arr[i][j][1] = getGreen(imgForArr,i,j);
                arr[i][j][2] = getBlue(imgForArr,i,j);
            }

        }

        return arr;
    }

    public int[][][] toArrayRandomCrop(){

        int arr[][][] = new int[crop_size][crop_size][3];

        BufferedImage imgForArr = randomCropByCropSize();

        for (int i = 0; i < crop_size ; i++) {
            for (int j = 0; j < crop_size; j++) {
                arr[i][j][0] = getRed(imgForArr,i,j);
                arr[i][j][1] = getGreen(imgForArr,i,j);
                arr[i][j][2] = getBlue(imgForArr,i,j);
            }

        }

        return arr;
    }

    public int getRed(BufferedImage image,int x, int y){

        Color color = new Color( image.getRGB(x,y) );
        int red = color.getRed();

        return red;
    }

    public int getGreen(BufferedImage image,int x, int y){

        Color color = new Color( image.getRGB(x,y) );
        int green = color.getGreen();

        return green;
    }

    public int getBlue(BufferedImage image, int x, int y){

        Color color = new Color( image.getRGB(x,y) );
        int blue = color.getBlue();

        return blue;
    }

    public int getCrop_size() {
        return crop_size;
    }

    public void setCrop_size(int crop_size) {
        this.crop_size = crop_size;
    }
}
