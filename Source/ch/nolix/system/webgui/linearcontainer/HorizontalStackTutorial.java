package ch.nolix.system.webgui.linearcontainer;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

final class HorizontalStackTutorial {

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("HorizontalStack tutorial", Session.class);

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

      //Creates a HorizontalStack.
      final var horizontalStack = new HorizontalStack();

      //Creates and adds 4 Labels to the HorizontalStack.
      horizontalStack.addControl(
        new Label().setText("A"),
        new Label().setText("B"),
        new Label().setText("C"),
        new Label().setText("D"));

      //Configures the style of the HorizontalStack.
      horizontalStack
        .getStoredStyle()
        .setChildControlMarginForState(ControlState.BASE, 50)
        .setTextSizeForState(ControlState.BASE, 100);

      //Adds the HorizontalStack to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(horizontalStack);
    }
  }

  private HorizontalStackTutorial() {
  }
}
