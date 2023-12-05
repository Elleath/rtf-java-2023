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

    /** TASK 1
     * Постройте график по среднему количеству студентов, в 10 различных странах, взять на свой выбор
     *
     * Comment: в таблице нет стран, county - округ
     * Среднее подсчитано как (общее количество учеников во всех школах округа)/(количество школ в округе)
     * */
    public double[] task1(String[] counties) throws SQLException {
        double[] result = new double[10];
        try (PreparedStatement pstms = conn.prepareStatement("SELECT SUM(students)/COUNT(students) " +
                                                                "FROM Schools WHERE county = ?")) {
            for (int i = 0; i < counties.length; i++) {
                try {
                    pstms.setString(1, counties[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ResultSet res = pstms.executeQuery();
                result[i] = res.getDouble(1);
            }
        }
        return result;
    }

    /** TASK 3
     * Выведите в консоль учебное заведение, с количеством студентов равному
     * от 5000 до 7500 и с 10000 до 11000,
     * с самым высоким показателем по математике (math)
     * */
    public String task3() throws SQLException {
        String answer = null;

        try (Statement stat = conn.createStatement()) {
            ResultSet ans = stat.executeQuery("SELECT school " +
                                                    "FROM (SELECT * FROM Schools WHERE (students BETWEEN 5000 AND 7500) " +
                                                    "OR (students BETWEEN 10000 AND 11000)) " +
                                                    "WHERE math = (SELECT MAX(math) FROM Schools " +
                                                    "WHERE (students BETWEEN 5000 AND 7500) " +
                                                    "OR (students BETWEEN 10000 AND 11000))");
            answer = ans.getString("school");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public void createTable(String name) throws SQLException {
        try (Statement stat = conn.createStatement()) {
            stat.execute("CREATE TABLE IF NOT EXISTS " + name + " " +
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
        }
        catch (SQLException e) {
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
