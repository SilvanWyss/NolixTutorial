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
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Link tutorial", Session.class);

    // start a web browser that will connect to the Server
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    // close the Server as soon as it does not have a client connected any more
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class Session extends WebClientSession<Object> {
    @Override
    protected void initialize() {
      // create a Link
      final var link = new Link().setUrlAndDisplayTextFromIt("https://nolix.ch").setTarget(LinkTarget.NEW_TAB);

      // add the Link to the GUI of the current Session
      getStoredGui().pushLayerWithRootControl(link);
    }
  }
}
