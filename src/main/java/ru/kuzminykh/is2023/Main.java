package ru.kuzminykh.is2023;

import ru.kuzminykh.is2023.dto.School;

import java.util.List;

public class Main {

    public static void main (String[] args) throws Exception {
        Parser parser = new Parser();
        List<School> schoolList = parser.parseCsvFile("Schools.csv");
    }
}
