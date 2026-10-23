/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.control.textbox;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.webapplication.main.WebClientSession;

/**
 * @author Silvan Wyss
 */
final class TextboxTutorial {
  private TextboxTutorial() {
  }

  public static void main() {
    // Create a Server.
    final var server = Server.forHttpPort();

    // Add a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Textbox tutorial", Session.class);

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
      // Create a Textbox.
      final var textBox = new Textbox();

      // Add the Textbox to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(textBox);
    }
  }
}
