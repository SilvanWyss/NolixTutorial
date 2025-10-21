package ch.nolix.core.programcontrol.flowcontrol;

import ch.nolix.core.errorcontrol.logging.Logger;
import ch.nolix.core.math.algebra.Matrix;

final class ResultFutureTutorial {
  private ResultFutureTutorial() {
  }

  public static void main(String[] args) {
    final var matrix = Matrix.createIdendityMatrixWithLength(3000);
    final var resultFuture = FlowController.runInBackground(matrix::getRank);

    Logger.logInfo("Calculations are made in background.");
    resultFuture.waitUntilIsFinished();
    Logger.logInfo("matrix rank: " + resultFuture.getResult());
  }
}
