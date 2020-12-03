package com.hwt;


import com.alibaba.fastjson.JSON;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: hwt
 * @date: 2020/12/3
 * @description:
 */
public class QrCode {
    private static final String QR_CODE_IMAGE_PATH = "C:\\Users\\Administrator\\Desktop\\QrCode\\MyQRCode.png";

    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws Exception{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println("运行了！2");
    }

    /**
     * 解析图像
     */
    public static void testDecode() throws Exception{
        BufferedImage image;
        try {
            image = ImageIO.read(new File(QR_CODE_IMAGE_PATH));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);  //对图像进行解码
            System.out.println("content: " + result.getText());
            System.out.println("encode： " + result.getBarcodeFormat());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Map<String, String> param = new HashMap();
            param.put("param1", "hahahahh");
            param.put("param2", "hahahahh2");
            param.put("param3", "hahahahh3");
            param.put("param4", "hahahahh4");
            generateQRCodeImage(JSON.toJSONString(param), 350, 350, QR_CODE_IMAGE_PATH);
            testDecode();
        } catch (Exception e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        }

    }
}
