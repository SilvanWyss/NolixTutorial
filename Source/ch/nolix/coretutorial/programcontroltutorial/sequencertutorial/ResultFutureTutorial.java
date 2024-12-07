package ch.nolix.coretutorial.programcontroltutorial.sequencertutorial;

import ch.nolix.core.errorcontrol.logging.GlobalLogger;
import ch.nolix.core.math.algebra.Matrix;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;

public final class ResultFutureTutorial {

  private ResultFutureTutorial() {
  }

  public static void main(String[] args) {

    final var matrix = Matrix.createIdendityMatrix(2000);
    final var resultFuture = GlobalSequencer.runInBackground(matrix::getRank);

    GlobalLogger.logInfo("Calculations are done in background.");
    GlobalLogger.logInfo("...");
    resultFuture.waitUntilIsFinished();
    GlobalLogger.logInfo("matrix rank: " + resultFuture.getResult());
  }
}
