package com.renue.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

@SpringBootApplication
public class ExamApplication implements CommandLineRunner {

    private static Logger LOG  = LoggerFactory
            .getLogger(ExamApplication.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(ExamApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info ("EXECUTING: command line runner");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку: ");
        Runtime rt = Runtime.getRuntime();
        String filter = scanner.nextLine();
        long m = System.currentTimeMillis();
        readAllLines(filter);
        for (int i = 0; i < args.length; i++)
            LOG.info ("args[{}]: {}", i, args[i]);
        System.out.println("Время выполнения программы: " + (System.currentTimeMillis() - m));

        System.out.println("Free Memory:" + rt.freeMemory());
        System.out.println("Total Memory:" + rt.freeMemory());
        System.out.println("Max Memory:" + rt.maxMemory());
    }
    
    public void readAllLines (String filter) throws IOException {
        FileReader fileReader = new FileReader("src/main/resources/airports.dat");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int counter = 0;
        Map<String, String> map=new TreeMap<String, String>();
        while((line=bufferedReader.readLine())!=null){
            if (getField(line).contains(filter)) {
                map.put(getField(line), line);
                counter++;
            }
        }
        bufferedReader.close();
        FileWriter writer = new FileWriter("sorted_numbers.txt");
        for(String val : map.values()){
            writer.write(val);
            writer.write('\n');
        }
        writer.close();
    }
    private static String getField(String line) {
        return line.split(",")[1];
    }
}
