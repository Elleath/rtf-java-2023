package ru.kuzminykh.is2023;

import ru.kuzminykh.is2023.dto.School;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main (String[] args) throws Exception {
        Parser parser = new Parser();
        List<School> schoolList = parser.parseCsvFile("Schools.csv");
        Database db = new Database();

        try {
            db.connect("project");
            //db.createTable("Schools");
            //db.saveSchools(schoolList);
            System.out.println("Доступны следующие действия:\n" +
                    "1 - Вывести ответ на первое задание\n" +
                    "2 - Вывести ответ на второе задание\n" +
                    "3 - Вывести ответ на третье задание\n" +
                    "4 - Завершить работу программы");

            Scanner in = new Scanner(System.in);
            while (true) {
                System.out.print("Вводите команду: ");
                String input = in.nextLine().strip();

                if (input.isBlank()) {
                    System.out.println("Введена пустая пустая команда");
                    continue;
                }

                switch (input) {
                    case "1":
                        String[] counties = new String[] {"Butte", "Fresno", "Tulare", "Kern", "Merced", "Los Angeles",
                                                        "Shasta", "Santa Barbara", "Riverside", "Orange"};
                        double[] studentsAverage = db.task1(counties);
                        System.out.println("Среднее количество учеников в школе по округу:");
                        for (int i = 0; i < studentsAverage.length; i++) {
                            System.out.println(counties[i] + ": " + studentsAverage[i] + " учеников");
                        }

                        System.out.println("\nПолученная гистограмма открылась в новом окне!");

                        EventQueue.invokeLater(() -> {
                            Chart chart = new Chart(counties, studentsAverage);
                            chart.setVisible(true);
                        });
                        break;
                    case "2":
                        System.out.println("В разработке");
                        break;
                    case "3":
                        String result = db.task3();
                        System.out.println("Учебное заведение с количеством студентов в диапазонах от 5000 до 7500 " +
                                "и 10000 до 11000 c самым высоким баллом по математике: " + result);
                        break;
                    case "4":
                        db.close();
                        System.out.println("Работа программы завершена");
                        System.exit(0);
                    default:
                        System.out.println("Неизвестная команда");
                        break;
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            db.close();
        }
    }
}
