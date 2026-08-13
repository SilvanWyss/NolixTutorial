/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.tech.math.fractal;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.control.imagecontrol.ImageControl;
import ch.nolix.system.webapplication.main.WebClientSession;

/**
 * @author Silvan Wyss
 */
final class DefaultFractalTutorial {
  private DefaultFractalTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Default fractal tutorial", Session.class);

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
      getStoredGui().pushLayerWithRootControl(
        new ImageControl().setImage(new FractalBuilder().build().startImageGeneration().getStoredImage()));

      FlowController
        .asLongAs(this::isAlive)
        .afterEverySecond()
        .runInBackground(this::refresh);
    }
  }
}
