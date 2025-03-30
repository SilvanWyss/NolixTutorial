package ch.nolix.coretutorial.programcontroltutorial.sequencertutorial;

import java.util.Random;

import ch.nolix.core.errorcontrol.logging.Logger;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;

public final class AsSoonAsMethodTutorial {

  private static final Random random = new Random();

  private AsSoonAsMethodTutorial() {
  }

  public static void main(String[] args) {

    final var startTime = System.currentTimeMillis();

    FlowController
      .asSoonAs(() -> getRandomNumberBetween1And100() == 50)
      .runInBackground(
        () -> //
        Logger.logInfo("Number 50 occured after " + (System.currentTimeMillis() - startTime) + " milliseconds!") //
      );

  }

  private static int getRandomNumberBetween1And100() {
    return (random.nextInt(50) + 1);
  }
}
