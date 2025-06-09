package ch.nolix.systemtutorial.webguitutorial.atomiccontroltutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.button.Button;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

final class ButtonWithRoundCornersTutorial {

  private ButtonWithRoundCornersTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Button with round corners tutorial",
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

  private static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      //Creates a Button.
      final var button = new Button();

      //Configures the style of the Button.
      button
        .getStoredStyle()
        .setHeightForState(ControlState.BASE, 50)
        .setCornerRadiusForState(ControlState.BASE, 20);

      //Adds the Button to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(button);
    }
  }
}
