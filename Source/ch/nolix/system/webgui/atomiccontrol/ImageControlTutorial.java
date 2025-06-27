package ch.nolix.system.webgui.atomiccontrol;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.graphic.image.MutableImage;
import ch.nolix.system.webgui.atomiccontrol.imagecontrol.ImageControl;
import ch.nolix.system.webgui.container.SingleContainer;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class ImageControlTutorial {

  private ImageControlTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "ImageControl tutorial",
      MainSession.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      //Loads an Image.
      final var image = MutableImage.fromResource("image/singer_building.jpg");

      //Creates an ImageControl with the Image.
      final var imageControl = new ImageControl().setImage(image);

      //Adds the ImageControl to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(
        new SingleContainer().setControl(imageControl).editStyle(s -> s.setPaddingForState(ControlState.BASE, 50)));
    }
  }
}
