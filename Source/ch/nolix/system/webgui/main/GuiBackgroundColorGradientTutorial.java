package ch.nolix.system.webgui.main;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.graphic.color.X11ColorCatalog;
import ch.nolix.system.gui.colorgradient.ColorGradient;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.gui.box.Direction;

/**
 * @author Silvan Wyss
 */
final class GuiBackgroundColorGradientTutorial {
  private GuiBackgroundColorGradientTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Background ColorGradient tutorial",
      Session.class);

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
      //Creates a ColorGradient.
      final var colorGradient = //
      ColorGradient.withDirectionAndColors(Direction.VERTICAL, X11ColorCatalog.SKY_BLUE, X11ColorCatalog.WHITE);

      //Sets the ColorGradient as background ColorGradient to the GUI of the current Session.
      getStoredGui().setBackgroundColorGradient(colorGradient);
    }
  }
}
