package Task2;

import java.util.HashMap;
import java.util.concurrent.RecursiveTask;

public class FoxTask extends RecursiveTask<HashMap<String, Object>>
{
    private final Matrix firstMatrix;
    private final Matrix secondMatrix;

    private int stepI;
    private int stepJ;

    public FoxTask(Matrix firstMatrix, Matrix secondMatrix, int stepI, int stepJ) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;

        this.stepI = stepI;
        this.stepJ = stepJ;
    }


    private Matrix multiplyBlock() {
        Matrix blockRes = new Matrix(firstMatrix.size);
        for (int i = 0; i < firstMatrix.size; i++) {
            for (int j = 0; j < secondMatrix.size; j++) {
                for (int k = 0; k < firstMatrix.size; k++) {
                    blockRes.plusAssign(i, j, firstMatrix.getValue(i, k) * secondMatrix.getValue(k, j));
                }
            }
        }
        return blockRes;
    }

    @Override
    protected HashMap<String, Object> compute() {
        Matrix blockResult = multiplyBlock();

        HashMap<String, Object> result = new HashMap<>();
        result.put("blockRes", blockResult);
        result.put("stepI", stepI);
        result.put("stepJ", stepJ);

        return result;
    }
}
