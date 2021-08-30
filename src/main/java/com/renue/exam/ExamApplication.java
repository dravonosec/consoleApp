package com.renue.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;



@SpringBootApplication
public class ExamApplication implements CommandLineRunner {

    @Value("${config.columns}")
    private Integer columns;

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку: ");
        String filter = scanner.nextLine();
        scanner.close();
        long m = System.currentTimeMillis();
        int count = getNumberOfMatches(filter);

        System.out.println("Количество совпадений:" + count);
        System.out.println("Время выполнения программы: " + (System.currentTimeMillis() - m) + " ms");
    }
    
    public int getNumberOfMatches (String filter) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        FileReader fileReader = new FileReader("airports.dat");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int counter = 0;

        Map<String, String> map = new TreeMap<>();
        while((line=bufferedReader.readLine())!=null){
            String element = getField(line, columns);
            if (element.contains(filter)) {
                map.put(element, line);
                counter++;
            }
        }

        bufferedReader.close();
        System.out.println("Результат работы:");
        for(String val : map.values()){
            System.out.println(val);
        }
        return counter;
    }

    private static String getField(String line, int columnNumber) {
        return line.split(",")[columnNumber];
    }
}
