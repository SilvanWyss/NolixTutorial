/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.control.label.Label;
import ch.nolix.system.control.verticalstack.VerticalStack;
import ch.nolix.system.webapplication.main.WebClientSession;

/**
 * @author Silvan Wyss
 */
final class UrlParameterTutorial {
  private UrlParameterTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("URL parameter tutorial", Session.class);

    // start a web browser that will connect to the Server
    ShellProvider.startDefaultWebBrowserOpeningUrl("http://127.0.0.1/?param1=5000&param2=60000");

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
      // get the Url parameters param1 and param2 from the Url
      final var param1 = getStoredParentClient().getOptionalUrlParameterValueByUrlParameterName("param1");
      final var param2 = getStoredParentClient().getOptionalUrlParameterValueByUrlParameterName("param2");

      // display the Url parameters param1 and param2 on the GUI of the current Session
      getStoredGui()
        .pushLayerWithRootControl(
          new VerticalStack()
            .addControls(
              new Label()
                .setText("URL parameter param1: " + param1.orElseThrow()),
              new Label()
                .setText("URL parameter param2: " + param2.orElseThrow())));
    }
  }
}
