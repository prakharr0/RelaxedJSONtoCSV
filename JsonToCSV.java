package com.prakharrathi.nlp;

import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonToCSV {

    public static void main(String filePath) {
        Path pathToFile = Paths.get(filePath);// filePath is the path to json file

        //instance of BufferedReader to read the file
        try (BufferedReader bufferedReader = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            //read a new line
            String line = bufferedReader.readLine();

            List<String[]> listArrayList = new ArrayList<>();
            
            //Suppose the dataset in relaxed json is such a format : 
            // {"category" : ... , "headline": ... , "date":...}
            // {"category" : ... , "headline": ... , "date":...} and so on.

            while (line != null) {

                line = line.replaceAll("\\{", "")
                        .replaceAll("}", "")
                        .replaceAll("category", "")
                        .replaceAll("\"", "")
                        .replaceAll(": ", "")
                        .replaceAll(", headline", ",\t")
                        .replaceAll(", date", ",\t");

                String[] attributes = line.split(",\t");

                listArrayList.add(attributes);

                line = bufferedReader.readLine();
            }

            File file = new File(pathToCSV);

            try {
                //create a file writer
                FileWriter fileWriter = new FileWriter(file);

                //create csv writer obj
                CSVWriter csvWriter = new CSVWriter(fileWriter);

                //adding header to csv
                String[] header = {"CATEGORY", "HEADLINE", "DATE"};
                csvWriter.writeNext(header);

                //add data to csv
                csvWriter.writeAll(listArrayList);

                //close writer
                csvWriter.close();
            } catch (Exception e) {
                System.out.println("the exception is " + e);
            }

        } catch (Exception e) {
            System.out.println("here is the exception");
            e.printStackTrace();
        }
    }
}
