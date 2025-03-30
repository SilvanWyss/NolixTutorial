package ch.nolix.coretutorial.programcontroltutorial.sequencertutorial;

import ch.nolix.core.errorcontrol.logging.Logger;
import ch.nolix.core.math.algebra.Matrix;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;

public final class ResultFutureTutorial {

  private ResultFutureTutorial() {
  }

  public static void main(String[] args) {

    final var matrix = Matrix.createIdendityMatrix(2000);
    final var resultFuture = FlowController.runInBackground(matrix::getRank);

    Logger.logInfo("Calculations are done in background.");
    Logger.logInfo("...");
    resultFuture.waitUntilIsFinished();
    Logger.logInfo("matrix rank: " + resultFuture.getResult());
  }
}
