package ch.nolix.coretutorial.programcontroltutorial.sequencertutorial;

import java.util.Random;

import ch.nolix.core.errorcontrol.logger.GlobalLogger;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;

public final class AsSoonAsMethodTutorial {

  private static final Random random = new Random();

  private AsSoonAsMethodTutorial() {
  }

  public static void main(String[] args) {
    GlobalSequencer
      .asSoonAs(() -> getRandomNumberBetween1And100() == 100)
      .runInBackground(() -> GlobalLogger.logInfo("Number 100 occured!"));
  }

  private static int getRandomNumberBetween1And100() {
    return (random.nextInt(100) + 1);
  }
}
