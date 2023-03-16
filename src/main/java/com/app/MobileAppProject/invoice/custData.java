package com.app.MobileAppProject.invoice;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;


public class custData {
    public String currentDir = System.getProperty("user.dir");
    public String path=(currentDir+"\\src\\main\\resources\\invoiceAndQr\\");
    PDDocument invc;
    int n;

    int total;
    int price;
    String custfName;
    String custlName;
    String custMoNumber;
    String custAddress;
    String paymentMode;
    String paymentDone;

    String qrCodeData;

    Scanner sc = new Scanner(System.in);
    List<String> productName = new ArrayList<String>();
    List<Integer> productPrice = new ArrayList<Integer>();
    List<Integer> productQty = new ArrayList<Integer>();
    String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
    String timeStamp1 = new SimpleDateFormat("ddMMyyyyHHmmss").format(new java.util.Date());


    //Using the constructor to create a PDF with a blank page
    public void invoice() {
        //Create Document
        invc = new PDDocument();
        //Create Blank Page
        PDPage newpage = new PDPage(PDRectangle.A4);
        //Add the blank page
        invc.addPage(newpage);
    }
    public void getData() throws IOException {
        System.out.println("Enter the First Customer Name: ");
        custfName = sc.nextLine();
        System.out.println("Enter the Last Customer Name: ");
        custlName = sc.nextLine();
        System.out.println("Enter the Customer Phone Number: ");
        custMoNumber = sc.nextLine();
        System.out.println("Enter the Customer Address: ");
        custAddress  = sc.nextLine();
        System.out.println("Enter the Number of Products: ");
        n = sc.nextInt();
        for(int i=0; i<n; i++) {
            System.out.println("Enter the Product Name: ");
            productName.add(sc.next());
            System.out.println("Enter the Price of the Product: ");
            productPrice.add(sc.nextInt());
            System.out.println("Enter the Quantity of the Product: ");
            productQty.add(sc.nextInt());
            //Calculating the total amount
            total = total + (productPrice.get(i)*productQty.get(i));

        }
    }

    public void checkingData() throws IOException {
        System.out.println("Select Payment Mode(Upi/Cash): ");
        paymentMode =sc.next();

        if (paymentMode.contains("upi")||paymentMode.contains("UPI")||paymentMode.contains("Upi")){
            System.out.println("QR Code Genrating>>>");
            generateQrCode();

        } else if (paymentMode.contains("cash")||paymentMode.contains("Cash")) {
            System.out.println("Collect Cash Carefully..!!");
            checkingPayment();

        }
    }


    void generateQrCode(){
        try{
            Scanner sc1 =new Scanner(System.in);
            qrCodeData="upi://pay?pa=9764152416@paytm&pn=Prime%Mobile%Shop&mc=0000&am="+total+"&cu=INR&mode=02&purpose=MobileBill&orgid=159761";
            String filePath =path+"QR.png";
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap< EncodeHintType, ErrorCorrectionLevel >();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 450, 450, hintMap);
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                    .lastIndexOf('.') + 1), new File(filePath));

//            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
//                Desktop.getDesktop().browse(new URI(filePath));
//            }
            checkingPayment();
        }catch (Exception e){
            System.err.println(e);
        }
    }

    void checkingPayment() throws IOException {

        System.out.println("Payment Done or Not??");
        paymentDone = sc.next();

        if (paymentDone.contains("done") || paymentDone.contains("Done") || paymentDone.contains("DONE")) {
            writeInvoice();
        } else {
            System.out.println("Make Payment First..!!");
            try {
                checkingData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void writeInvoice() throws IOException {

        String barPath=path+"barcode.png";
        // String barPath= barPath = "C:\\Users\\Pallavi\\Desktop\\ruturaj"+ ".png";
        String barCodeData =timeStamp;
        Code128Bean code128 = new Code128Bean();
        code128.setHeight(15f);
        code128.setModuleWidth(0.3);
        code128.setQuietZone(10);
        code128.doQuietZone(true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/x-png", 300, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        code128.generateBarcode(canvas, barCodeData);
        canvas.finish();
        //write to png file
        FileOutputStream fos = new FileOutputStream(barPath);
        fos.write(baos.toByteArray());
        fos.flush();
        fos.close();




        //get the page
        PDPage mypage = invc.getPage(0);
        String filePath1=path+"Invoice.pdf";

        // String filePath1="C:\\Users\\Pallavi\\Desktop\\"+"Ruturaj"+".pdf";
        try {
            //Prepare Content Stream
            PDPageContentStream cs = new PDPageContentStream(invc, mypage);

            File font =new File(path+"Rokkitt-Medium.ttf");

            PDFont myfont = PDType0Font.load(invc,font);
            //Writing Single Line text
            //Writing the Invoice title
            //Img inserting


            //barcode in pdf
            PDImageXObject  barImg = PDImageXObject.createFromFile(barPath,invc);
            cs.drawImage(barImg,420,775,150,40);

            //frame Image in pdf
            PDImageXObject outerFrame= PDImageXObject.createFromFile(path+"frame.png",invc);
            cs.drawImage(outerFrame,8,80,580,780);

            //for vertical and horizontal line
            File vrlinePath=new File(path+"vrline.png");
            File hrlinePath=new File(path+"hrline.png");
            PDImageXObject vrLine=PDImageXObject.createFromFile(String.valueOf(vrlinePath),invc);
            cs.drawImage(vrLine,260,603,55,230);

            PDImageXObject hrLine=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine,-145,595,815,20);

            //stamp image
            PDImageXObject stamp=PDImageXObject.createFromFile(path+"stamp.png",invc);
            cs.drawImage(stamp,430,(190 - (20 * n)),120,50);


            cs.beginText();
            cs.setFont(myfont, 7);
            cs.newLineAtOffset(450, 820);
            cs.setNonStrokingColor(Color.black);
            cs.showText("(ORIGINAL FOR RECIPIENT)");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.newLineAtOffset(270, 810);
            cs.setNonStrokingColor(Color.black);
            cs.showText("INVOICE");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 25);
            cs.setNonStrokingColor(Color.red);
            cs.newLineAtOffset(50, 780);
            cs.showText("Prime Mobile Shop");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 10);
            cs.setLeading(10f);
            cs.newLineAtOffset(50, 760);
            cs.setNonStrokingColor(Color.black);
            cs.showText("M.I.D.C BLOCK,'D' PLOT NO.P-90,CHINCHWAD,");
            cs.newLine();
            cs.showText("PUNE-411019.");
            cs.newLine();
            cs.showText("Contact No.: 9764152416");
            cs.newLine();
            cs.showText("GSTIN/UIN: 27AAKFR1522C1ZR");
            cs.newLine();
            cs.showText("Mail: info@primemobile.in");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(30, 710);
            cs.setNonStrokingColor(Color.black);
            cs.showText("--------------------------------------------------");
            cs.endText();


            cs.beginText();
            cs.setFont(myfont, 13);
            cs.newLineAtOffset(40, 700);
            cs.setNonStrokingColor(Color.black);
            cs.showText("Bill To,");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setLeading(10f);
            cs.setNonStrokingColor(Color.black);
            cs.newLineAtOffset(50, 685);
            cs.showText("Name: " + custfName+" " + custlName);
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Phone No: " + custMoNumber);
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Address: " + custAddress);
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Mail: vruturaj633@gmail.com");
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.newLineAtOffset(300, 775);
            cs.setNonStrokingColor(Color.black);
            cs.showText("Date: "+timeStamp.substring(0,10));
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 11);
            cs.setLeading(10f);
            cs.newLineAtOffset(300, 760);
            cs.setNonStrokingColor(Color.black);
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Invoice No.: "+timeStamp1);
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Payment Mode: UPI");
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Ref.No: 300823722276");
            cs.newLine();
            cs.showText("--------------------------------------------------");
            cs.newLine();
            cs.showText("Note: ");
            cs.endText();

            PDImageXObject hrLine1=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine1,-145,580,815,20);

            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(30, 580);
            cs.setNonStrokingColor(Color.black);
            cs.showText("Sr.");
            cs.newLine();
            cs.showText("No.");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 13);
            cs.setLeading(20f);
            cs.newLineAtOffset(35, 540);
            for (int i = 0; i < n; i++) {
                cs.showText(String.valueOf(i+1));
                cs.newLine();
            }
            cs.endText();

            PDImageXObject vrline1=PDImageXObject.createFromFile(String.valueOf(vrlinePath),invc);
            cs.drawImage(vrline1,40,597,30,(-233+ (-20 * n)));


            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(90, 575);
            cs.setNonStrokingColor(Color.black);
            cs.showText("Product Descripition");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 14);
            cs.setLeading(20f);
            cs.newLineAtOffset(65, 540);
            for (int i = 0; i < n; i++) {
                cs.showText(productName.get(i));
                cs.newLine();
            }
            cs.endText();

            PDImageXObject vrline2=PDImageXObject.createFromFile(String.valueOf(vrlinePath),invc);
            cs.drawImage(vrline2,220,597,30,(-233+ (-20 * n)));

            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(260, 575);
            cs.showText("Unit Price");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(270, 540);
            for (int i = 0; i < n; i++) {
                cs.showText(productPrice.get(i).toString());
                cs.newLine();
            }
            cs.endText();

            PDImageXObject vrline3=PDImageXObject.createFromFile(String.valueOf(vrlinePath),invc);
            cs.drawImage(vrline3,320,597,30,(-233+ (-20 * n)));

            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(350, 575);
            cs.showText("Quantity");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(370, 540);
            for (int i = 0; i < n; i++) {
                cs.showText(productQty.get(i).toString());
                cs.newLine();
            }
            cs.endText();

//                cs.beginText();
//                cs.setFont(myfont, 14);
//                cs.newLineAtOffset(370, (400 - (20 * n)));
//                for (int i = 0; i <n; i++) {
//                   for(int j= productQty.get(i);j>0;j--){
//                       x++;
//                   }
//                    z=z+x;
//                   cs.showText(String.valueOf(z));
//
//                }
//                cs.endText();

            PDImageXObject vrline4=PDImageXObject.createFromFile(String.valueOf(vrlinePath),invc);
            cs.drawImage(vrline4,400,597,30,(-233+ (-20 * n)));

            cs.beginText();
            cs.setFont(myfont, 14);
            cs.newLineAtOffset(470, 575);
            cs.showText("Amount");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(470, 540);
            for (int i = 0; i < n; i++) {
                price = productPrice.get(i) * productQty.get(i);
                cs.showText(String.valueOf(price));
                cs.newLine();
            }
            cs.endText();

            PDImageXObject hrLine2=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine2,-145,550,815,20);

            cs.beginText();
            cs.setFont(myfont, 20);
            cs.setNonStrokingColor(Color.red);
            cs.newLineAtOffset(430, (410 - (20 * n)));
            cs.showText("Total=  ");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 20);
            //Calculating where total is to be written using number of products
            cs.newLineAtOffset(490, (410 - (20 * n)));
            cs.showText(String.valueOf(total));
            cs.endText();

            PDImageXObject hrLine3=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine3,-145,(385 - (20 * n)),815,20);

            Numbertoword nw=new Numbertoword();

            cs.beginText();
            cs.setFont(myfont, 12);
            cs.setNonStrokingColor(Color.blue);
            cs.newLineAtOffset(30, (385 - (20 * n)));
            cs.showText("Indian Rupees "+nw.convert(total)+" Only");
            cs.endText();



            PDImageXObject hrLine4=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine4,-145,(370 - (20 * n)),815,20);

            PDImageXObject hrLine5=PDImageXObject.createFromFile(String.valueOf(hrlinePath),invc);
            cs.drawImage(hrLine5,-145,(250 - (20 * n)),815,20);

            cs.beginText();
            cs.setFont(myfont, 10);
            cs.setNonStrokingColor(Color.black);
            cs.setLeading(10f);
            cs.newLineAtOffset(30, (245 - (20 * n)));
            cs.showText("Company's PAN:BLTPV7503R");
            cs.newLine();
            cs.showText("Declaration,");
            cs.newLine();
            cs.showText("We declare that this invoice shows the actual price of the" +
                    " goods described and that");
            cs.newLine();
            cs.showText("particulars are true and correct.");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 10);
            cs.setNonStrokingColor(Color.black);
            cs.newLineAtOffset(35, (185 - (20 * n)));
            cs.showText("Customer's Signature");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 10);
            cs.setNonStrokingColor(Color.black);
            cs.newLineAtOffset(445, (185 - (20 * n)));
            cs.showText("Authorised Signature");
            cs.endText();

            cs.beginText();
            cs.setFont(myfont, 10);
            cs.setNonStrokingColor(Color.black);
            cs.newLineAtOffset(30, (165 - (20 * n)));
            cs.showText("*This is a Computer Generated Invoice");
            cs.endText();

            //Close the content stream
            cs.close();
            //Save the PDF
            invc.save(filePath1);
            System.out.println("Invoice Genreated Sucessfully!!");



//                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
//                    Desktop.getDesktop().browse(new URI("file:///C:/Users/Pallavi/Desktop/"+"Ruturaj"+".pdf"));
//                }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
