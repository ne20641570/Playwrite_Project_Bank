package utils;

import config.DBConfig;
import pages.InputField;

import java.sql.*;
import java.util.*;

public class DBUtils {

    /* ===================== CONNECTION ===================== */

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DBConfig.URL,
                DBConfig.USER,
                DBConfig.PASSWORD
        );
    }

    /* ===================== INSERT ===================== */

    public static void insertRegistration(Map<InputField, String> data)
            throws SQLException {

        String sql = "INSERT INTO registration_details " +
                "(FirstName, LastName, Address, City, State, ZipCode, PhoneNumber, SSN, UserName) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, data.get(InputField.FirstName));
            ps.setString(2, data.get(InputField.LastName));
            ps.setString(3, data.get(InputField.Address));
            ps.setString(4, data.get(InputField.City));
            ps.setString(5, data.get(InputField.State));
            ps.setString(6, data.get(InputField.ZipCode));
            ps.setString(7, data.get(InputField.PhoneNumber));
            ps.setString(8, data.get(InputField.SSN));
            ps.setString(9, data.get(InputField.UserName));

            ps.executeUpdate();
        }
    }

    /* ===================== SELECT ===================== */

    public static Map<InputField, String> getUserByUserName(String searchWith,String searchValue)
            throws SQLException {

        String sql =
                "SELECT * FROM registration_details WHERE "+searchWith+"=?";

        Map<InputField, String> result = new HashMap<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, searchValue);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    for (InputField field : InputField.values()) {
                        if(field!=InputField.Password&&field!=InputField.ConfirmPassword) {
                            result.put(field, rs.getString(field.name()));
                        }
                    }
                }
            }
        }
        return result;
    }

    /* ===================== DELETE (Optional Cleanup) ===================== */

    public static void deleteUser(String userName) throws SQLException {
        String sql =
                "DELETE FROM registration_details WHERE UserName=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userName);
            ps.executeUpdate();
        }
    }

    public static List<String> getRecentUserNames() throws SQLException {
        List<String> usernames = new ArrayList<>();
        String sql = "SELECT UserName FROM registration_details WHERE created_at >= NOW() - INTERVAL 5 MINUTE";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usernames.add(rs.getString("UserName"));
            }
        }

        return usernames;
    }

    // Delete rows for a given list of usernames
    public static void deleteUsers(List<String> usernames) throws SQLException {
        if (usernames == null || usernames.isEmpty()) return;

        // Prepare SQL with IN clause
        String placeholders = String.join(",", Collections.nCopies(usernames.size(), "?"));
        String sql = "DELETE FROM registration_details WHERE UserName IN (" + placeholders + ")";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < usernames.size(); i++) {
                ps.setString(i + 1, usernames.get(i));
            }

            int deletedRows = ps.executeUpdate();
            System.out.println("Deleted " + deletedRows + " rows from DB.");
        }
    }

    public static String getRandomUserName() throws SQLException {

        String sql = "SELECT userName FROM registration_details";
        List<String> userNames = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                userNames.add(rs.getString("userName"));
            }
        }

        if (userNames.isEmpty()) {
            return null; // or throw exception if you prefer
        }

        Random random = new Random();
        return userNames.get(random.nextInt(userNames.size()));
    }


    public static void deleteOldUsers() throws SQLException {
        try (Connection con = getConnection()) {

            try (Statement stmt = con.createStatement()) {

                // Disable safe updates
                stmt.execute("SET SQL_SAFE_UPDATES = 0");

                // Delete old records
                stmt.executeUpdate(
                        "DELETE FROM registration_details " +
                                "WHERE created_at < NOW() - INTERVAL 5 MINUTE"
                );

                // Enable safe updates
                stmt.execute("SET SQL_SAFE_UPDATES = 1");
            }
        }
    }
}
