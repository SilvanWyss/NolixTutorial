/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.control.uploader;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.errorcontrol.generalexception.GeneralException;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.control.button.Button;
import ch.nolix.system.control.imagecontrol.ImageControl;
import ch.nolix.system.control.validationlabel.ValidationLabel;
import ch.nolix.system.control.verticalstack.VerticalStack;
import ch.nolix.system.graphic.image.ImmutableImage;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.control.imagecontrol.IImageControl;
import ch.nolix.systemapi.control.uploader.IUploader;

/**
 * @author Silvan Wyss
 */
final class UploaderTutorial {
  private UploaderTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Uploader tutorial", Session.class);

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
    private final IImageControl imageControl = new ImageControl();

    private final IUploader uploader = new Uploader();

    @Override
    protected void initialize() {
      // add the Uploader to the GUI of the current Session
      getStoredGui()
        .pushLayerWithRootControl(
          new VerticalStack()
            .addControls(
              imageControl,
              new ValidationLabel(),
              uploader,
              new Button()
                .setText("Upload image")
                .setLeftMouseButtonPressAction(this::displayImage)));

      // configure the style of the imageControl
      imageControl.setMinWidth(200).setMinHeight(200).setMaxWidth(500).setMaxHeight(500);
    }

    private void displayImage() {
      // assert that the Uploader has a file
      if (!uploader.hasFile()) {
        throw GeneralException.withErrorMessage("No image selected.");
      }

      // create an image from the file of the Uploader
      final var image = ImmutableImage.fromBytes(uploader.getFile());

      // set the image to the ImageControl
      imageControl.setImage(image);
    }
  }
}
