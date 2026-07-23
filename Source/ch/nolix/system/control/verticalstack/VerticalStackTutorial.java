/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.control.verticalstack;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.control.label.Label;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.webgui.main.ControlState;

/**
 * @author Silvan Wyss
 */
final class VerticalStackTutorial {
  private VerticalStackTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("VerticalStack tutorial", Session.class);

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
      // create a VerticalStack.
      final var verticalStack = new VerticalStack();

      // create and adds 4 Labels to the VerticalStack
      verticalStack.addControls(
        new Label().setText("A"),
        new Label().setText("B"),
        new Label().setText("C"),
        new Label().setText("D"));

      // configure the style of the VerticalStack
      verticalStack
        .getStoredStyle()
        .setChildControlMarginForState(ControlState.BASE, 50)
        .forStateSetTextSize(ControlState.BASE, 100);

      // add the VerticalStack to the GUI of the current Session
      getStoredGui().pushLayerWithRootControl(verticalStack);
    }
  }
}
