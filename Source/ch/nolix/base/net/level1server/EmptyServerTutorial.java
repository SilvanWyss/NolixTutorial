/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.base.net.level1server;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;

/**
 * @author Silvan Wyss
 */
final class EmptyServerTutorial {
  private EmptyServerTutorial() {
  }

  public static void main() {
    // Create a Server.
    try (final var _ = Server.forHttpPort()) {
      // Start a web browser that will connect to the Server.
      ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

      // Wait 2 seconds.
      FlowController.waitForSeconds(2);
    }
  }
}
