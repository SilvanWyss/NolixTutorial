/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.control.link;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.control.link.LinkTarget;

/**
 * @author Silvan Wyss
 */
final class LinkTutorial {
  private LinkTutorial() {
  }

  public static void main() {
    // Create a Server.
    final var server = Server.forHttpPort();

    // Add a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Link tutorial", Session.class);

    // Start a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    // Close the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class Session extends WebClientSession<Object> {
    @Override
    protected void initialize() {
      // Create a Link.
      final var link = new Link().setUrlAndDisplayTextFromIt("https://nolix.ch").setTarget(LinkTarget.NEW_TAB);

      // Add the Link to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(link);
    }
  }
}
