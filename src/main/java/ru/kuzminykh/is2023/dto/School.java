package ru.kuzminykh.is2023.dto;

import lombok.Data;

@Data
public class School {
    private int district;
    private String school;
    private String county;
    private String grades;
    private int students;
    private double teachers;
    private double calworks;
    private double lunch;
    private int computer;
    private double expenditure;
    private double income;
    private double english;
    private double read;
    private double math;

    public School(String[] line) {
        this.district = Integer.parseInt(line[1]);
        this.school = line[2];
        this.county = line[3];
        this.grades = line[4];
        this.students = Integer.parseInt(line[5]);
        this.teachers = Double.parseDouble(line[6]);
        this.calworks = Double.parseDouble(line[7]);
        this.lunch = Double.parseDouble(line[8]);
        this.computer = Integer.parseInt(line[9]);
        this.expenditure = Double.parseDouble(line[10]);
        this.income = Double.parseDouble(line[11]);
        this.english = Double.parseDouble(line[12]);
        this.read = Double.parseDouble(line[13]);
        this.math = Double.parseDouble(line[14]);
    }
}
