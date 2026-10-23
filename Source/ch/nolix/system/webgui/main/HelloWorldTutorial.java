/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.control.label.Label;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.webgui.webguiproperty.ControlState;

/**
 * @author Silvan Wyss
 */
final class HelloWorldTutorial {
  private HelloWorldTutorial() {
  }

  public static void main() {
    // Create a Server.
    final var server = Server.forHttpPort();

    // Add a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Hello World tutorial", Session.class);

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
      // Create a Label.
      final var label = new Label().setText("Hello World!");

      // Configure the style of the Label.
      label.getStoredStyle().forStateSetTextSize(ControlState.BASE, 100);

      // Add the Label to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(label);
    }
  }
}
