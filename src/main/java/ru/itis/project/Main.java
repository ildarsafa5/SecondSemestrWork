package ru.itis.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("data.txt"));
             FileWriter writer = new FileWriter("tests.txt")) {
            writer.write("Размер дерева | Insert | Search | Delete\n");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                int size = Integer.parseInt(parts[0]);
                int[] array = parseArray(parts[1]);
                SplayTree tree = new SplayTree(array[0]);
                long startTimeInsert = System.nanoTime();
                for (int i = 1; i <array.length ; i++) {
                    tree.insert(array[i]);
                }
                long durationInsert = System.nanoTime() - startTimeInsert;
                long startTimeSearch = System.nanoTime();
                for (int i = 0; i <array.length ; i++) {
                    tree.search(array[i]);
                }
                long durationSearch = System.nanoTime() - startTimeSearch;
                long startTimeDelete = System.nanoTime();
                for (int i = 0; i <array.length ; i++) {
                    tree.delete(array[i]);
                }
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



//SplayTree tree = new SplayTree(10);
//        tree.insert(65);
//        tree.insert(15);
//        System.out.println(tree.getRoot().getValue());
//        tree.insert(34);
//        System.out.println(tree.getRoot().getValue());
//        tree.insert(22);
//        System.out.println(tree.getRoot().getValue());
//        tree.insert(65);
//        System.out.println(tree.getRoot().getValue());
//        tree.insert(9);
//        System.out.println(tree.getRoot().getValue());
//        tree.insert(6);
//        System.out.println(tree.getRoot().getValue());
//        tree.delete(34);
//        System.out.println(tree.getRoot().getValue());