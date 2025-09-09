package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dbProcess {
    
    public void dbInsert(String tc, String username, String surname, String organizationName, Long requestedAmount, Long minLotAmount) {
        // MySQL bağlantısı için JDBC driver sınıfını yükleyin
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Include it in your library path");
            e.printStackTrace();
            return;
        }
    
        // Bağlantıyı kurun ve veri ekleyin
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Aa123456")) {
            // TC'nin mevcut olup olmadığını kontrol edin
            String checkSql = "SELECT COUNT(*) FROM requests WHERE tc = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                checkStmt.setString(1, tc);
                ResultSet rs = checkStmt.executeQuery();
                boolean isDuplicate = rs.next() && rs.getInt(1) > 0;
    
                // Yeni kayıt ekleyin
                String insertSql = "INSERT INTO requests (tc,username,surname,organizationName,requestedAmount,minLotAmount,distributedAmount,duplicateRecord) VALUES (?,?,?,?,?,?,0,?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setString(1, tc);
                    insertStmt.setString(2, username);
                    insertStmt.setString(3, surname);
                    insertStmt.setString(4, organizationName);
                    insertStmt.setLong(5, requestedAmount);
                    insertStmt.setLong(6, minLotAmount);
                    insertStmt.setBoolean(7, isDuplicate); // duplicateRecord'u ayarla
                    int rowsInserted = insertStmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("A new user was inserted successfully!");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        }
    }

    public List<Request> getRequestsFromDatabase() {
        List<Request> requests = new ArrayList<>();

        // MySQL bağlantısı için JDBC driver sınıfını yükleyin
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Include it in your library path");
            e.printStackTrace();
            return requests;
        }

        // Bağlantıyı kurun ve veri çekin
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Aa123456")) {
            // SQL select sorgusu
            String sql = "SELECT tc, username, surname, organizationName, requestedAmount, minLotAmount, distributedAmount, duplicateRecord FROM requests WHERE duplicateRecord = FALSE";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String tc = resultSet.getString("tc");
                    String username = resultSet.getString("username");
                    String surname = resultSet.getString("surname");
                    String organizationName = resultSet.getString("organizationName");
                    Long requestedAmount = resultSet.getLong("requestedAmount");
                    Long minLotAmount = resultSet.getLong("minLotAmount");
                    Long distributedAmount = resultSet.getLong("distributedAmount");
                    Boolean duplicateRecord = resultSet.getBoolean("duplicateRecord");


                    requests.add(new Request(tc, username, surname, organizationName, requestedAmount, minLotAmount, distributedAmount,duplicateRecord));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        }

        return requests;
    }

    public void updateDistributedAmounts(List<Request> requests) {
        // MySQL bağlantısı için JDBC driver sınıfını yükleyin
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Include it in your library path");
            e.printStackTrace();
            return;
        }

        // Bağlantıyı kurun ve verileri güncelleyin
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Aa123456")) {
            String updateSql = "UPDATE requests SET distributedAmount = ? WHERE tc = ? AND duplicateRecord = FALSE";

            try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                for (Request request : requests) {
                    updateStmt.setLong(1, request.getDistributedAmount());
                    updateStmt.setString(2, request.getTc());
                    updateStmt.addBatch();
                }
                updateStmt.executeBatch();
                System.out.println("Distributed amounts updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        }
    }
}
