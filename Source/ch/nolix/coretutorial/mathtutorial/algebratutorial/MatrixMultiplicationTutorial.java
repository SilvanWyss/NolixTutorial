package ch.nolix.coretutorial.mathtutorial.algebratutorial;

import ch.nolix.core.errorcontrol.logging.GlobalLogger;
import ch.nolix.core.math.algebra.Matrix;

public final class MatrixMultiplicationTutorial {

  private MatrixMultiplicationTutorial() {
  }

  public static void main(String[] args) {

    //Creates matrix1.
    final var matrix1 = //
    Matrix.createMatrixWithRowCountAndColumnCount(3, 3).setValues(2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 1.0, 2.0, 3.0);

    //Creates matrix2.
    final var matrix2 = //
    Matrix.createMatrixWithRowCountAndColumnCount(3, 3).setValues(4.0, 5.0, 6.0, 1.0, 2.0, 3.0, 2.0, 3.0, 4.0);

    //Calculates the productMatrix of matrix1 and matrix2.
    final var productMatrix = matrix1.getProduct(matrix2);

    //Logs matrix1, matrix2 and the productMatrix.
    GlobalLogger.logInfo(matrix1 + " * " + matrix2 + " = " + productMatrix);
  }
}
