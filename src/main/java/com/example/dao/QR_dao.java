package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class QR_dao {

	private static final String url = "jdbc:mysql://localhost:3306/qr";
	private static final String user = "root";
	private static final String password = "1234";

	public static void saveToDatabase(String text, byte[] imageBytes) {

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

	public static void createDbAndTable() {

		String sql1 = "create database qr";
		
		String sql2 = "CREATE TABLE IF NOT EXISTS qr_codes ("
					+ "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "qr_text VARCHAR(2048) NOT NULL,"
					+ "qr_image LONGBLOB NOT NULL,"
					+ "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				Statement statement = conn.createStatement()) {
			
			statement.execute(sql1);
			statement.execute(sql2);

			

			System.out.println("QR Code stored in database");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
