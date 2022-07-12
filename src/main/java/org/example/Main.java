package org.example;

public class Main {
    public static void main(String[] args) {
        arrayOne();
        arrayOneThread();
    }

    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];
    static MathMethod math = new MathMethod();
    public static void arrayOne() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long finish = System.currentTimeMillis();
        long time = finish - start;
        System.out.println("Время исполнения первого метода: " + time);
    }

    public static void arrayOneThread() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long start = System.currentTimeMillis();
        synchronized (math) {
            Thread tr1 = new Thread(() -> {
                float[] a1 = new float[h];
                System.arraycopy(arr, 0, a1, 0, h);
                for (int i = 0; i < a1.length; i++) {
                    arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                System.arraycopy(a1, 0, arr, 0, h);
            }, "Поток A");

            Thread tr2 = new Thread(() -> {
                float[] a2 = new float[h];
                System.arraycopy(arr, h, a2, 0, h);
                for (int i = 0; i < a2.length; i++) {
                    arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                System.arraycopy(a2, 0, arr, h, h);
            }, "Поток B");

            tr1.start();
            tr2.start();
        }

        long finish = System.currentTimeMillis();
        long time = finish - start;

        System.out.println("Время исполнения первого метода: " + time);
    }
}