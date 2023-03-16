package com.app.MobileAppProject.invoice;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QRCode {

    public static void main(String[] args) {
        QRCode qrCode= new QRCode();
        qrCode.generateQrCode(490000.00f);
    }

    public void generateQrCode(double total){
        String currentDir = System.getProperty("user.dir");
       // String path=(currentDir+"src\\main\\resources\\static\\invoiceAndQr\\");
        String path="C:\\Users\\Capricon\\IdeaProjects\\MobileAppProject\\src\\main\\resources\\static\\invoiceAndQr\\";
        String qrCodeData;
        try{
            Scanner sc1 =new Scanner(System.in);
            qrCodeData="upi://pay?pa=8805545888@paytm&pn=Prime%Mobile%Shop&mc=0000&am="+total+"&cu=INR&mode=02&purpose=MobileBill&orgid=159761";
            String filePath =path+"QR.png";
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap< EncodeHintType, ErrorCorrectionLevel >();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 450, 450, hintMap);
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                    .lastIndexOf('.') + 1), new File(filePath));
            System.out.println("Qr Genrated Succesfully!!");

        }catch (Exception e){
            System.err.println(e);
        }
    }
}
