package ch.nolix.coretutorial.programcontroltutorial.sequencertutorial;

import ch.nolix.core.errorcontrol.logger.GlobalLogger;
import ch.nolix.core.math.algebra.Matrix;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.core.programcontrol.sequencer.ResultFuture;

/**
 * The {@link ResultFutureTutorial} is a tutorial for {@link ResultFuture}s. Of
 * the {@link ResultFutureTutorial} an instance cannot be created.
 * 
 * @author Silvan Wyss
 * @date 2017-10-05
 */
public final class ResultFutureTutorial {

  /**
   * Prevents that an instance of the {@link ResultFutureTutorial} can be created.
   */
  private ResultFutureTutorial() {
  }

  /**
   * Calculates the rank of a {@link Matrix} in background.
   * 
   * @param args
   */
  public static void main(String[] args) {

    final var matrix = Matrix.createIdendityMatrix(2000);

    final var resultFuture = GlobalSequencer.runInBackground(matrix::getRank);

    GlobalLogger.logInfo("Calculations are done in background.");
    GlobalLogger.logInfo("...");

    resultFuture.waitUntilIsFinished();

    GlobalLogger.logInfo("rank: " + resultFuture.getResult());
  }
}
