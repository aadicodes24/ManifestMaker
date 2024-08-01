package org.orca;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    //version 0.5
    public static void main(String[] args) throws IOException, CsvValidationException, ArrayIndexOutOfBoundsException {
        CSVReader csvReader = new CSVReader(new FileReader("File path here"));
        HashMap<String, Integer> map = new HashMap<>();
        String[] lines;
        csvReader.readNext();
        String sku;
        int qt = 0;
        while((lines = csvReader.readNext()) != null) {
            if(lines.length == 1) {
                break;
            }
//            System.out.print(lines[3] + " ## ");
            sku = lines[8];
            qt = Integer.parseInt(lines[18]);
            if(!map.containsKey(sku)) {
                map.put(sku, qt);
            } else {
                map.put(sku, map.get(sku) + qt);
            }
        }
        for(String x: map.keySet()) {
            if(map.get(x) <= 1) {
                System.out.println(x);
            } else {
                System.out.println(x + "(" + map.get(x) + "pcs)");
            }
        }
        map.clear();
        csvReader.close();
    }

    static void displayInfo() {
        System.out.println("Manifest maker V.0.05 ");
    }
}