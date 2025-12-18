package com.example;

import com.example.dao.QR_dao;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class QRGenerator {

    public static byte[] generateQRCodeBytes(String text, int width, int height)
            throws Exception {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =  qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);

        return baos.toByteArray();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        QR_dao.createDbAndTable();

        while (true) {
            System.out.print("Enter URL (or exit): ");
            String text = sc.next();

            if (text.equalsIgnoreCase("exit")) break;

            try {
                byte[] qrBytes = generateQRCodeBytes(text, 300, 300);
                QR_dao.saveToDatabase(text, qrBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sc.close();
    }
}
