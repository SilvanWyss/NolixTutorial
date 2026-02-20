/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.tech.math.fractal;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webatomiccontrol.imagecontrol.ImageControl;

/**
 * @author Silvan Wyss
 */
final class DefaultFractalTutorial {
  private DefaultFractalTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Default fractal tutorial", Session.class);

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
      getStoredGui().pushLayerWithRootControl(
        new ImageControl().setImage(new FractalBuilder().build().startImageGeneration().getStoredImage()));

      FlowController
        .asLongAs(this::isAlive)
        .afterEverySecond()
        .runInBackground(this::refresh);
    }
  }
}
