/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.template.webapplication.session;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.control.button.Button;
import ch.nolix.system.control.verticalstack.VerticalStack;
import ch.nolix.system.webapplication.main.WebClientSession;

/**
 * @author Silvan Wyss
 */
final class UnderConstructionPageTutorial {
  private UnderConstructionPageTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Under construction page tutorial",
      Session.class);

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
      // add a Button that leads to an UnderConstructionPageSession to the GUI of the current Session
      getStoredGui()
        .pushLayerWithRootControl(
          new VerticalStack()
            .addControl(
              new Button()
                .setText("Go")
                .setLeftMouseButtonPressAction(() -> push(new UnderConstructionPageSession()))));
    }
  }
}
