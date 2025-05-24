package ru.itis.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("data.txt"));
             FileWriter writer = new FileWriter("tests.txt");) {
            writer.write("Размер дерева | Insert | Search | Delete\n");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                int size = Integer.parseInt(parts[0]);
                int[] array = parseArray(parts[1]);
                SplayTree tree = new SplayTree(array[0]);
                for (int i = 1; i <array.length-1 ; i++) {
                    tree.insert(array[i]);
                }
                long startTimeInsert = System.nanoTime();
                tree.insert(array[array.length-1]);
                long durationInsert = System.nanoTime() - startTimeInsert;
                Random random = new Random();
                long startTimeSearch = System.nanoTime();
                tree.search(array[random.nextInt(array.length)]);
                long durationSearch = System.nanoTime() - startTimeSearch;
                long startTimeDelete = System.nanoTime();
                tree.delete(array[random.nextInt(array.length)]);
                long durationDelete = System.nanoTime() - startTimeDelete;
                writer.write(String.format("%12d | %10d | %10d | %10d \n",
                        size, durationInsert,durationSearch,durationDelete));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static int[] parseArray(String str) {
        return Arrays.stream(str.trim().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}