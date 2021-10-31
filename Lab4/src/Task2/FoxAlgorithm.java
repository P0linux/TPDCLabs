package Task2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class FoxAlgorithm extends RecursiveTask<Matrix>
{
    private final Matrix firstMatrix;
    private final Matrix secondMatrix;
    private Matrix resultMatrix;

    private int threadCount;

    private int[][] matrixOfSizesI;
    private int[][] matrixOfSizesJ;
    private int step;

    public FoxAlgorithm(int threadCount, Matrix firstMatrix, Matrix secondMatrix)
    {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.threadCount = threadCount;

        this.resultMatrix = new Matrix(firstMatrix.size);

        multiple(this.firstMatrix, this.secondMatrix);
    }

    @Override
    protected Matrix compute()
    {
        List<RecursiveTask<HashMap<String, Object>>> tasks = new ArrayList<>();

        for (int l = 0; l < threadCount; l++) {
            for (int i = 0; i < threadCount; i++) {
                for (int j = 0; j < threadCount; j++) {
                    int stepI0 = matrixOfSizesI[i][j];
                    int stepJ0 = matrixOfSizesJ[i][j];

                    int stepI1 = matrixOfSizesI[i][(i + l) % threadCount];
                    int stepJ1 = matrixOfSizesJ[i][(i + l) % threadCount];

                    int stepI2 = matrixOfSizesI[(i + l) % threadCount][j];
                    int stepJ2 = matrixOfSizesJ[(i + l) % threadCount][j];

                    FoxTask task =
                            new FoxTask(
                                    copyIntBlock(firstMatrix, stepI1, stepJ1, step),
                                    copyIntBlock(secondMatrix, stepI2, stepJ2, step),
                                    stepI0,
                                    stepJ0);

                    tasks.add(task);
                    task.fork();
                }
            }
        }

        for (RecursiveTask<HashMap<String, Object>> task : tasks) {
            HashMap<String, Object> r = task.join();

            Matrix blockRes = (Matrix) r.get("blockRes");
            int stepI = (int) r.get("stepI");
            int stepJ = (int) r.get("stepJ");

            for (int i = 0; i < blockRes.size; i++) {
                for (int j = 0; j < blockRes.size; j++) {
                    resultMatrix.setValue(i + stepI, j + stepJ,
                            resultMatrix.getValue(i + stepI, j + stepJ) + blockRes.getValue(i, j));
                }
            }
        }

        return this.resultMatrix;
    }

    private void multiple(Matrix firstMatrix, Matrix secondMatrix)
    {
        if (firstMatrix.size != secondMatrix.size)
            throw new IllegalArgumentException("Matrices size must be the same");

        threadCount = Math.min(threadCount, firstMatrix.size);
        threadCount = findNearestDivider(threadCount, firstMatrix.size);
        step = firstMatrix.size / threadCount;

        matrixOfSizesI = new int[threadCount][threadCount];
        matrixOfSizesJ = new int[threadCount][threadCount];

        int stepI = 0;
        for (int i = 0; i < threadCount; i++) {
            int stepJ = 0;
            for (int j = 0; j < threadCount; j++) {
                matrixOfSizesI[i][j] = stepI;
                matrixOfSizesJ[i][j] = stepJ;
                stepJ += step;
            }
            stepI += step;
        }
    }

    private Matrix copyIntBlock(Matrix matrix, int i, int j, int size) {
        Matrix block = new Matrix(size);
        for (int k = 0; k < size; k++) {
            System.arraycopy(matrix.getRow(k + i), j, block.getRow(k), 0, size);
        }
        return block;
    }

    private int findNearestDivider(int s, int p) {
        int i = s;
        while (i > 1) {
            if (p % i == 0) break;
            if (i >= s) {
                i++;
            } else {
                i--;
            }
            if (i > Math.sqrt(p)) i = Math.min(s, p / s) - 1;
        }

        return i >= s ? i : i != 0 ? p / i : p;
    }
}
