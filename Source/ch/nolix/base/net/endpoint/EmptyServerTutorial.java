/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.base.net.endpoint;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;

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
