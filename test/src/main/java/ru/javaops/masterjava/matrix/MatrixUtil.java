package ru.javaops.masterjava.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

    public static double[][] concurrentMultiply(double[][] matrixA, double[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
        final int matrixSize = matrixA.length;
        final double[][] matrixC = new double[matrixSize][matrixSize];
        final double[][] matrixBB = new double[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrixBB[i][j] = matrixB[j][i];
            }
        }

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < matrixSize; i++) {
            final int row = i;
            tasks.add(() -> {
                int columnC = 0;
                for (int row2 = 0; row2 < matrixSize; row2++) {
                    int result = 0;
                    for (int column = 0; column < matrixSize; column++) {
                        result += matrixA[row][column] * matrixBB[row2][column];
                    }
                    matrixC[row][columnC] = result;
                    columnC++;
                }
                return null;
            });
        }
        executor.invokeAll(tasks);
        return matrixC;
    }

    public static double[][] singleThreadMultiply(double[][] matrixA, double[][] matrixB) {
        final int matrixSize = matrixA.length;
        final double[][] matrixC = new double[matrixSize][matrixSize];

        for (int col = 0; col < matrixSize; col++) {
            final double[] columnB = new double[matrixSize];
            for (int k = 0; k < matrixSize; k++) {
                columnB[k] = matrixB[k][col];
            }

            for (int row = 0; row < matrixSize; row++) {
                int sum = 0;
                final double[] rowA = matrixA[row];
                for (int k = 0; k < matrixSize; k++) {
                    sum += rowA[k] * columnB[k];
                }
                matrixC[row][col] = sum;
            }
        }
        return matrixC;
    }

    public static double[][] create(int size) {
        double[][] matrix = new double[size][size];
        Random rn = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextDouble();
            }
        }
        return matrix;
    }

    public static boolean compare(double[][] matrixA, double[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
