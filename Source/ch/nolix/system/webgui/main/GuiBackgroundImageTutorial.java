/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.graphic.image.ImmutableImage;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.gui.guiproperty.ImageApplication;

/**
 * @author Silvan Wyss
 */
final class GuiBackgroundImageTutorial {
  private GuiBackgroundImageTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Background Image tutorial",
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
      // load an Image
      final var image = ImmutableImage.fromResource("image/pilatus.jpg");

      // set the Image as background image to the GUI of the current Session
      getStoredGui().setBackgroundImage(image, ImageApplication.SCALE_TO_FRAME);
    }
  }
}
