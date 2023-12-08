package ru.kuzminykh.is2023;

import ru.kuzminykh.is2023.dto.School;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    Connection conn;
    private final static String[] PREP_STATEMENTS = new String[] {
            "INSERT INTO Schools (district, school_name, county, grades) VALUES (?, ?, ?, ?);",
            "INSERT INTO People (students, teachers) VALUES (?, ?);",
            "INSERT INTO Computers (computers) VALUES (?);",
            "INSERT INTO Money (expenditure, income) VALUES (?, ?);",
            "INSERT INTO Scores (calworks, lunch, english, read, math) VALUES (?, ?, ?, ?, ?);"
    };

    public final static String TASK1_STATEMENT = """
                                                 SELECT SUM(students)/COUNT(students)
                                                 FROM Schools INNER JOIN People USING(school_id)
                                                 WHERE county = ?
                                                 """;

    public final static String TASK2_STATEMENT = """
                                                SELECT SUM(expenditure)/count(expenditure)
                                                FROM Schools INNER JOIN Money USING(school_id)
                                                WHERE county = ? AND income > 10
                                                """;

    public final static String TASK3_STATEMENT =
            """
            SELECT school_name
            FROM (SELECT * FROM Schools INNER JOIN People USING(school_id)
                                        INNER JOIN Scores USING(school_id)
                                        WHERE (students BETWEEN 5000 AND 7500) OR (students BETWEEN 10000 AND 11000))
            WHERE math = (SELECT MAX(math) FROM Schools
                                        INNER JOIN People USING(school_id)
                                        INNER JOIN Scores USING(school_id)
                                        WHERE (students BETWEEN 5000 AND 7500) OR (students BETWEEN 10000 AND 11000))
            """;



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
        try (PreparedStatement pstms = conn.prepareStatement(TASK1_STATEMENT)) {
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

    /**
     * TASK 2
     * Выведите в консоль среднее количество расходов(expenditure) в Fresno, Contra Costa,
     * El Dorado и Glenn, у которых расход больше 10.
     *
     * Comment: вероятно, в условии задания допущена ошибка, так как во всех школах расход
     * в разы больше 10 (составляет 4000-5000 и более), поэтому подсчитано среднее количество расходов
     * в школах с ДОХОДОМ (income) больше 10
     *
     * Среднее подсчитано как (общее количество расходов в школах округа)/(количество школ в округе)
     * */
    public List<String> task2(String[] counties) throws SQLException {
        List<String> result = new ArrayList<>();
        try (PreparedStatement pstms = conn.prepareStatement(TASK2_STATEMENT)) {
            for (String county : counties) {
                try {
                    pstms.setString(1, county);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ResultSet res = pstms.executeQuery();
                result.add(String.format("%.2f",res.getDouble(1)));
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
            ResultSet ans = stat.executeQuery(TASK3_STATEMENT);
            answer = ans.getString("school_name");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public void createTables() throws SQLException {
        try (Statement stat = conn.createStatement()) {
            stat.execute("CREATE TABLE IF NOT EXISTS Schools " +
                    "('school_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'district' INT NOT NULL, " +
                    "'school_name' TEXT NOT NULL, " +
                    "'county' VARCHAR(30) NOT NULL, " +
                    "'grades' VARCHAR(10) NOT NULL);");

            stat.execute("CREATE TABLE IF NOT EXISTS People " +
                    "('school_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'students' INT, " +
                    "'teachers' DOUBLE);");

            stat.execute("CREATE TABLE IF NOT EXISTS Money " +
                    "('school_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'expenditure' DOUBLE," +
                    "'income' DOUBLE);");

            stat.execute("CREATE TABLE IF NOT EXISTS Computers " +
                    "('school_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'computers' INT);");

            stat.execute("CREATE TABLE IF NOT EXISTS Scores " +
                    "('school_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'calworks' DOUBLE," +
                    "'lunch' DOUBLE," +
                    "'english' DOUBLE," +
                    "'read' DOUBLE," +
                    "'math' DOUBLE);");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public void saveSchools(List<School> schoolList) throws SQLException {
         schoolList.stream().forEach(school -> {
             try {
                PreparedStatement pstms = null;
                pstms = conn.prepareStatement(PREP_STATEMENTS[0]);
                pstms.setInt(1, school.getDistrict());
                pstms.setString(2, school.getSchool());
                pstms.setString(3, school.getCounty());
                pstms.setString(4, school.getGrades());
                pstms.executeUpdate();

                pstms = conn.prepareStatement(PREP_STATEMENTS[1]);
                pstms.setInt(1, school.getStudents());
                pstms.setDouble(2, school.getTeachers());
                pstms.executeUpdate();

                 pstms = conn.prepareStatement(PREP_STATEMENTS[2]);
                 pstms.setInt(1, school.getComputer());
                 pstms.executeUpdate();

                 pstms = conn.prepareStatement(PREP_STATEMENTS[3]);
                 pstms.setDouble(1, school.getExpenditure());
                 pstms.setDouble(2, school.getIncome());
                 pstms.executeUpdate();

                 pstms = conn.prepareStatement(PREP_STATEMENTS[4]);
                 pstms.setDouble(1, school.getCalworks());
                 pstms.setDouble(2, school.getLunch());
                 pstms.setDouble(3, school.getEnglish());
                 pstms.setDouble(4, school.getRead());
                 pstms.setDouble(5, school.getMath());
                 pstms.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
