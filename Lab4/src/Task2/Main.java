package Task2;

import java.util.concurrent.ForkJoinPool;

public class Main
{
    private static int size = 2000;

    public static void main(String[] args)
    {
        execute(false);
    }

    public static void execute(boolean printMatrices)
    {
        Matrix firstMatrix = new Matrix(size);
        Matrix secondMatrix = new Matrix(size);

        firstMatrix.seedData();
        secondMatrix.seedData();

        int threadCount = Runtime.getRuntime().availableProcessors();

        long currentTime = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        var resultMatrix = forkJoinPool.invoke(new FoxAlgorithm(threadCount, firstMatrix, secondMatrix));
        currentTime = System.nanoTime() - currentTime;

        if (printMatrices) resultMatrix.display();

        System.out.println("Time for Fox Algorithm: " + currentTime / 1_000_000);
        System.out.println();

        System.out.println(
                "Speed up (Fox / FoxForkJoin): " + (double) 4138 / (currentTime / 1_000_000));
    }
}
