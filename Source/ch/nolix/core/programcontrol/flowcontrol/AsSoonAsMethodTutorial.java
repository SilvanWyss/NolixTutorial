package ch.nolix.core.programcontrol.flowcontrol;

import java.util.Random;

import ch.nolix.core.errorcontrol.logging.Logger;

final class AsSoonAsMethodTutorial {

  public static void main(String[] args) {

    final var startTime = System.currentTimeMillis();

    FlowController
      .asSoonAs(() -> getRandomNumberBetween1And50() == 50)
      .runInBackground(
        () -> Logger.logInfo("Number occured in " + (System.currentTimeMillis() - startTime) + " milliseconds!"));
  }

  private static final Random RANDOM = new Random();

  private static int getRandomNumberBetween1And50() {
    return (RANDOM.nextInt(50) + 1);
  }

  private AsSoonAsMethodTutorial() {
  }
}
