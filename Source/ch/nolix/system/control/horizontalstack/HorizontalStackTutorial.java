/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.control.horizontalstack;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.control.label.Label;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.webgui.main.ControlState;

/**
 * @author Silvan Wyss
 */
final class HorizontalStackTutorial {
  private HorizontalStackTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("HorizontalStack tutorial", Session.class);

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
      // create a HorizontalStack
      final var horizontalStack = new HorizontalStack();

      // create and adds 4 Labels to the HorizontalStack
      horizontalStack.addControls(
        new Label().setText("A"),
        new Label().setText("B"),
        new Label().setText("C"),
        new Label().setText("D"));

      // configure the style of the HorizontalStack
      horizontalStack
        .getStoredStyle()
        .setChildControlMarginForState(ControlState.BASE, 50)
        .forStateSetTextSize(ControlState.BASE, 100);

      // add the HorizontalStack to the GUI of the current Session
      getStoredGui().pushLayerWithRootControl(horizontalStack);
    }
  }
}
