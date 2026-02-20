/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.base.programcontrol.flowcontrol;

import ch.nolix.base.errorcontrol.logging.Logger;
import ch.nolix.base.math.algebra.Matrix;

/**
 * @author Silvan Wyss
 */
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
