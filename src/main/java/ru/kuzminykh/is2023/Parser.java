package ru.kuzminykh.is2023;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.kuzminykh.is2023.dto.School;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    public List<School> parseCsvFile (String file) throws IOException, CsvException{
        List<String[]> strings = readAllLines(Path.of(file));
        return strings.stream().map(School::new).collect(Collectors.toList());
    }

    public List<String[]> readAllLines(Path filePath) throws IOException, CsvException {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] headers = csvReader.readNext();
                return csvReader.readAll();
            }
        }
    }
}
