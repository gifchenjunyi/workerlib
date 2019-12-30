package yizhit.workerlib.interfaceuilt;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Hashtable;

public class QRCodeUtil {
    private static final int QRCOLOR = 0xFF000000;   //默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF;   //背景颜色

    /**
     * 生成带有参数的二维码，返回base64码
     * @param contents
     * @param width
     * @param height
     * @return
     */
    public static byte[] creatRrCode(String contents, int width, int height) {
        byte[] bytes = null;
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = null; // 1、读取文件转换为字节数组
        try {
            bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedImage image = toBufferedImage(bitMatrix);
        //转换成png格式的IO流
        try {
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = out.toByteArray();

        return bytes;
    }



    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

}
