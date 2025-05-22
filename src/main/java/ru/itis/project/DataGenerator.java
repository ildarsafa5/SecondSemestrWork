package ru.itis.project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenerator {
    public static void main(String[] args) {
        generateTestFile("data.txt", 60, 101, 10000);
    }

    public static void generateTestFile(String filename, int arraysCount, int minSize, int maxSize) {
        try (FileWriter writer = new FileWriter(filename)) {
            Random random = new Random();
            for (int i = 0; i < arraysCount; i++) {
                int size = minSize + ((maxSize - minSize) * i) / arraysCount;
                int[] array = generateArray(size, random);
                writer.write(size + "|");
                for (int num : array) {
                    writer.write(num + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] generateArray(int size, Random random) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100000);
        }
        return array;
    }

    private static int[] generateGoodArray(int size, Random random) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    private static int[] generateBadArray(int size, Random random) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }
}