package ch.nolix.system.webgui.linearcontainer;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webatomiccontrol.label.Label;
import ch.nolix.system.webcontainercontrol.verticalstack.VerticalStack;
import ch.nolix.systemapi.webgui.main.ControlState;

final class VerticalStackTutorial {
  private VerticalStackTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("VerticalStack tutorial", Session.class);

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
      //Creates a VerticalStack.
      final var verticalStack = new VerticalStack();

      //Creates and adds 4 Labels to the VerticalStack.
      verticalStack.addControls(
        new Label().setText("A"),
        new Label().setText("B"),
        new Label().setText("C"),
        new Label().setText("D"));

      //Configures the style of the VerticalStack.
      verticalStack
        .getStoredStyle()
        .setChildControlMarginForState(ControlState.BASE, 50)
        .forStateSetTextSize(ControlState.BASE, 100);

      //Adds the VerticalStack to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(verticalStack);
    }
  }
}
