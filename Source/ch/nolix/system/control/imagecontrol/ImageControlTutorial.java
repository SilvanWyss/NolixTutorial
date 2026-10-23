/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.control.imagecontrol;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.graphic.image.MutableImage;
import ch.nolix.system.webapplication.main.WebClientSession;

/**
 * @author Silvan Wyss
 */
final class ImageControlTutorial {
  private ImageControlTutorial() {
  }

  public static void main() {
    // Create a Server.
    final var server = Server.forHttpPort();

    // Add a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("ImageControl tutorial", Session.class);

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
      // Load an image.
      final var image = MutableImage.fromResource("image/singer_building.jpg");

      // Create an ImageControl with the image.
      final var imageControl = new ImageControl().setImage(image);

      // Add the ImageControl to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(imageControl);
    }
  }
}
