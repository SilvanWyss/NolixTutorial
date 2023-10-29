package ch.nolix.systemtutorial.webguitutorial.atomiccontroltutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.graphic.color.Color;
import ch.nolix.system.graphic.image.MutableImage;
import ch.nolix.system.webgui.atomiccontrol.ImageControl;
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
    GlobalSequencer
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      //Loads an Image.
      final var image = MutableImage
        .fromResource("ch/nolix/systemtutorial/webguitutorial/resource/singer_building.jpg");

      //Creates an ImageControl with the Image.
      final var imageControl = new ImageControl().setImage(image);

      //Configures the style of the ImageControl.
      imageControl.getStoredStyle()
        .setBorderThicknessForState(ControlState.BASE, 5)
        .setBackgroundColorForState(ControlState.BASE, Color.LAVENDER)
        .setPaddingForState(ControlState.BASE, 5);

      //Adds the ImageControl to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(imageControl);
    }
  }
}
