/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.template.webapplication.session;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webatomiccontrol.button.Button;
import ch.nolix.system.webcontainercontrol.verticalstack.VerticalStack;

/**
 * @author Silvan Wyss
 */
final class UnderConstructionPageTutorial {
  private UnderConstructionPageTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Under construction page tutorial",
      Session.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class Session extends WebClientSession<Object> {
    @Override
    protected void initialize() {
      //Adds a Button that leads to an UnderConstructionPageSession to the GUI of the current Session.
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
