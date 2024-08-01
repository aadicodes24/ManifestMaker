package org.orca;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class ManifestMaker {
    static LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
    static Scanner sc = new Scanner(System.in);
    static CSVReader csvReader;
    static FileWriter fWriter;
    public static void main(String[] args) throws CsvValidationException, IOException {

        boolean run = true;
        while(run) {
            System.out.println("Choose an option");
            System.out.println("1.start\n0.Quit");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    process();
                    break;
                case 0:
                    run = false;
                    break;
                default:
                    System.out.println("Choose valid option!");
            }
        }

        LocalDateTime dateToday = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH_mm_ss");
        String date  = dateToday.format(format);
        File output = new File("Order" + date + ".txt");
        for(String x: map.keySet()) {
            if(map.get(x) <= 1) {
                System.out.println(x);
            } else {
                System.out.println(x + "(" + map.get(x) + "pcs)");
            }
        }


        if(output.createNewFile()) {
            System.out.println("Writing into file");
            fWriter = new FileWriter(output);
            for(String x: map.keySet()) {
                if(map.get(x) <= 1) {
                    String data = x +"\n";
                    fWriter.write(data);
                } else {
                    String data = x + "(" + map.get(x) + "pcs)\n";
                    fWriter.write(data);
                }
            }
        } else {
            System.out.println("Error creating a file");
        }
        map.clear();
        csvReader.close();
        sc.close();
        fWriter.close();

    }
    static void process() throws IOException, CsvValidationException {
        System.out.println("Enter label");
        sc.nextLine();
        String label = sc.nextLine();
        System.out.println("Enter file path");
        String path = sc.nextLine();
        csvReader = new CSVReader(new FileReader(path));
        csvReader.readNext();
        String[] lines;
        String sku;
        int qt;
        map.put(label, 0);
        int counter = 0;
        while( (lines = csvReader.readNext()) != null ) {
            counter++;
            if(lines.length <= 1) {
                break;
            } else {
                sku = lines[8];
                qt = Integer.parseInt(lines[18]);
                if(!map.containsKey(sku)) {
                    map.put(sku, qt);
                } else {
                    map.put(sku, map.get(sku) + qt);
                }
            }
        }
        map.put(label, counter);

    }
}
