package ch.nolix.coretutorial.environmenttutorial.runningjartutorial;

import ch.nolix.core.environment.runningjar.RunningJar;
import ch.nolix.core.errorcontrol.logger.GlobalLogger;

public final class RunningJarTutorial {

  private RunningJarTutorial() {
  }

  public static void main(String[] args) {

    final var resourcePath = //
    "ch/nolix/coretutorial/environmenttutorial/runningjartutorialresource/willkommen_und_abschied.txt";

    GlobalLogger.logInfo(RunningJar.getResource(resourcePath));
  }
}
