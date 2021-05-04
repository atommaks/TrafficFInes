package coursework;

import coursework.form.MainForm;

import java.sql.*;

public class TrafficFinesApp {
    private static final String URL = "jdbc:mysql://localhost:3306/TrafficFines?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "atom1105";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            MainForm form = new MainForm(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}