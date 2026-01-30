/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.core.environment.runningjar;

import ch.nolix.core.errorcontrol.logging.Logger;

/**
 * @author Silvan Wyss
 */
final class RunningJarTutorial {
  private RunningJarTutorial() {
  }

  public static void main(String[] args) {
    //Defines resourcePath.
    final var RESOURCE_PATH = "text/willkommen_und_abschied.txt";

    //Gets the resource from the resourcePath.
    final var resource = RunningJar.getResource(RESOURCE_PATH);

    //Logs the resource.
    Logger.logInfo(resource);
  }
}
