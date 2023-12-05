package ru.kuzminykh.is2023;

import ru.kuzminykh.is2023.dto.School;

import java.sql.*;
import java.util.List;

public class Database {
    Connection conn;
    public final static String PREP_STATMENT =
            "INSERT INTO Schools (district, school, county, grades, students, teachers, calworks," +
                    "lunch, computer, expenditure, income, english, read, math) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void connect(String dbName) throws SQLException {
        String url = "jdbc:sqlite:" + dbName + ".db";
        conn = DriverManager.getConnection(url);
        System.out.println("Connection to SQLite has been established.");
    }

    public void close() throws SQLException {
        conn.close();
    }

    public void createTable(String name) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS " + name + " " +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'district' INT NOT NULL, " +
                    "'school' TEXT NOT NULL, " +
                    "'county' VARCHAR(30) NOT NULL," +
                    "'grades' VARCHAR(10) NOT NULL, " +
                    "'students' INT," +
                    "'teachers' DOUBLE," +
                    "'calworks' DOUBLE," +
                    "'lunch' DOUBLE," +
                    "'computer' INT," +
                    "'expenditure' DOUBLE," +
                    "'income' DOUBLE," +
                    "'english' DOUBLE," +
                    "'read' DOUBLE," +
                    "'math' DOUBLE);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveSchools(List<School> schoolList) throws SQLException {
        try (PreparedStatement pstms = conn.prepareStatement(PREP_STATMENT)) {
            schoolList.stream().forEach(school -> {
                try {
                    pstms.setInt(1, school.getDistrict());
                    pstms.setString(2, school.getSchool());
                    pstms.setString(3, school.getCounty());
                    pstms.setString(4, school.getGrades());
                    pstms.setInt(5, school.getStudents());
                    pstms.setDouble(6, school.getTeachers());
                    pstms.setDouble(7, school.getCalworks());
                    pstms.setDouble(8, school.getLunch());
                    pstms.setInt(9, school.getComputer());
                    pstms.setDouble(10, school.getExpenditure());
                    pstms.setDouble(11, school.getIncome());
                    pstms.setDouble(12, school.getEnglish());
                    pstms.setDouble(13, school.getRead());
                    pstms.setDouble(14, school.getMath());
                    pstms.addBatch();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            pstms.executeBatch();
        }
    }
}