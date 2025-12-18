package com.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class QRGenerator {

    private static int count = 1;

    public static void generateQRCode(String text, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        String fileName = "qrcode_" + count + ".png";
        Path path = FileSystems.getDefault().getPath(fileName);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println("QR Code saved as: " + fileName);

        count++;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter URL (or type 'exit'): ");
            String text = sc.next();

            if (text.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                generateQRCode(text, 300, 300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sc.close();
    }
}
