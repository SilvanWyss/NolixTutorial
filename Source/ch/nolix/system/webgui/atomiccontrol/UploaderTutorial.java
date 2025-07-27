package ch.nolix.system.webgui.atomiccontrol;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.errorcontrol.generalexception.GeneralException;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.graphic.image.Image;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.button.Button;
import ch.nolix.system.webgui.atomiccontrol.imagecontrol.ImageControl;
import ch.nolix.system.webgui.atomiccontrol.uploader.Uploader;
import ch.nolix.system.webgui.atomiccontrol.validationlabel.ValidationLabel;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webgui.atomiccontrol.imagecontrolapi.IImageControl;
import ch.nolix.systemapi.webgui.atomiccontrol.uploaderapi.IUploader;

final class UploaderTutorial {

  private UploaderTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Uploader tutorial", Session.class);

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

    private final IImageControl imageControl = new ImageControl();

    private final IUploader uploader = new Uploader();

    @Override
    protected void initialize() {

      //Adds the Uploader to the GUI of the current Session.
      getStoredGui()
        .pushLayerWithRootControl(
          new VerticalStack()
            .addControl(
              imageControl,
              new ValidationLabel(),
              uploader,
              new Button()
                .setText("Upload image")
                .setLeftMouseButtonPressAction(this::displayImage)));

      //Configures the style of the imageControl.
      imageControl.setMinWidth(200).setMinHeight(200).setMaxWidth(500).setMaxHeight(500);
    }

    private void displayImage() {

      //Asserts that the Uploader has a file.
      if (!uploader.hasFile()) {
        throw GeneralException.withErrorMessage("No image selected.");
      }

      //Creates an image from the file of the Uploader.
      final var image = Image.fromBytes(uploader.getFile());

      //Sets the image to the ImageControl.
      imageControl.setImage(image);
    }
  }
}
