package com.company;

import java.util.Arrays;

public class Methods{
    private static final int SIZE = 10000000;
    private static int HALF = SIZE / 2;

    /*arr[i] = (float) (arr[i] * Math.sin(0.2f + arr[i] / 5) * Math.cos(0.2f + arr[i] / 5) * Math.cos(0.4f + arr[i] / 2));
*/
    public void firstMethod() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время работы первого метода = " + (System.currentTimeMillis() - a));
    }

    public void secondMethod() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        float[] arr1 = new float[HALF];
        float[] arr2 = new float[HALF];
        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, arr1, 0, HALF);
        System.arraycopy(arr, HALF, arr2, 0, HALF);

        Thread rc1 = new Thread(new RunnableClass(arr1, 0));
        Thread rc2 = new Thread(new RunnableClass(arr2, HALF));
        rc1.start();
        rc2.start();

        try {
            rc1.join();
            rc2.join();
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);
        System.out.println("Время работы второго метода = " + (System.currentTimeMillis() - a));
    }
}
class RunnableClass implements Runnable{
    private float[] array;
    private int shift;

     RunnableClass(float[] array, int shift){
        this.array = array;
        this.shift = shift;
    }
    @Override
    public void run(){
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (float) (array[i] * Math.sin(0.2f + (i+shift) / 5) * Math.cos(0.2f + (i+shift) / 5) * Math.cos(0.4f + (i+shift) / 2));
        }
    }
}
