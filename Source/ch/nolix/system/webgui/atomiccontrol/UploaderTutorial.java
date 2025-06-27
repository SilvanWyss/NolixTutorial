package ch.nolix.system.webgui.atomiccontrol;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.errorcontrol.generalexception.GeneralException;
import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.graphic.color.X11ColorCatalog;
import ch.nolix.system.graphic.image.Image;
import ch.nolix.system.webgui.atomiccontrol.button.Button;
import ch.nolix.system.webgui.atomiccontrol.imagecontrol.ImageControl;
import ch.nolix.system.webgui.atomiccontrol.uploader.Uploader;
import ch.nolix.system.webgui.atomiccontrol.validationlabel.ValidationLabel;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.imagecontrolapi.IImageControl;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.uploaderapi.IUploader;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

final class UploaderTutorial {

  private UploaderTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndContext(
      "Uploader tutorial",
      MainSession.class,
      new VoidObject());

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class MainSession extends WebClientSession<Object> { //NOSONAR: MainSession is a tutorial class.

    private final IImageControl imageControl = new ImageControl();

    private final IUploader uploader = new Uploader();

    @Override
    protected void initialize() {

      //Adds the Uploader to the GUI of the current MainSession.
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
      imageControl.editStyle(
        s -> s
          .setWidthForState(ControlState.BASE, 500)
          .setHeightForState(ControlState.BASE, 300)
          .setBackgroundColorForState(ControlState.BASE, X11ColorCatalog.GREY));
    }

    private void displayImage() {

      if (!uploader.hasFile()) {
        throw GeneralException.withErrorMessage("No image selected.");
      }

      final var image = Image.fromBytes(uploader.getFile());

      imageControl.setImage(image);
    }
  }
}
