package com.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class QRGenerator {

    private static int count = 1;

    public static void saveToDatabase(String text, byte[] imageBytes) {

        String url = "jdbc:mysql://localhost:3306/qr";
        String user = "root";
        String password = "1234";

        String sql = "INSERT INTO qr_codes (qr_text, qr_image) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, text);
            ps.setBytes(2, imageBytes);
            ps.executeUpdate();

            System.out.println("QR Code stored in database");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    

    public static byte[] generateQRCodeBytes(String text, int width, int height)
            throws Exception {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =  qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);

        return baos.toByteArray();
    }


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
            System.out.print("Enter URL (or exit): ");
            String text = sc.next();

            if (text.equalsIgnoreCase("exit")) break;

            try {
                byte[] qrBytes = generateQRCodeBytes(text, 300, 300);
                saveToDatabase(text, qrBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sc.close();
    }
}
