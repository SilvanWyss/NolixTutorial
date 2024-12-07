package ch.nolix.coretutorial.environmenttutorial.runningjartutorial;

import ch.nolix.core.environment.runningjar.RunningJar;
import ch.nolix.core.errorcontrol.logging.GlobalLogger;

public final class RunningJarTutorial {

  private RunningJarTutorial() {
  }

  public static void main(String[] args) {

    //Defines resourcePath.
    final var resourcePath = //
    "ch/nolix/coretutorial/environmenttutorial/runningjartutorialresource/willkommen_und_abschied.txt";

    //Gets resource from the resourcePath.
    final var resource = RunningJar.getResource(resourcePath);

    //Logs the resource.
    GlobalLogger.logInfo(resource);
  }
}
