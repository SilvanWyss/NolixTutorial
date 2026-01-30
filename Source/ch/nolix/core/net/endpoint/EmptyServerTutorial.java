/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.core.net.endpoint;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;

/**
 * @author Silvan Wyss
 */
final class EmptyServerTutorial {
  private EmptyServerTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    try (final var _ = Server.forHttpPort()) {
      //Starts a web browser that will connect to the Server.
      ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

      //Waits 2 seconds.
      FlowController.waitForSeconds(2);
    }
  }
}
