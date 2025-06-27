package ch.nolix.system.webgui.linearcontainer;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class HorizontalStackTutorial {

  private HorizontalStackTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "HorizontalStack tutorial",
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

      //Creates a HorizontalStack.
      final var horizontalStack = new HorizontalStack();

      //Creates 4 Labels.
      final var label1 = new Label().setText("A");
      final var label2 = new Label().setText("B");
      final var label3 = new Label().setText("C");
      final var label4 = new Label().setText("D");

      //Adds the Labels to the HorizontalStack.
      horizontalStack.addControl(label1, label2, label3, label4);

      //Configures the style of the HorizontalStack.
      horizontalStack.getStoredStyle().setChildControlMarginForState(ControlState.BASE, 20);

      //Configures the style of the Labels.
      label1.getStoredStyle().setTextSizeForState(ControlState.BASE, 50);
      label2.getStoredStyle().setTextSizeForState(ControlState.BASE, 50);
      label3.getStoredStyle().setTextSizeForState(ControlState.BASE, 50);
      label4.getStoredStyle().setTextSizeForState(ControlState.BASE, 50);

      //Adds the HorizontalStack to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(horizontalStack);
    }
  }
}
