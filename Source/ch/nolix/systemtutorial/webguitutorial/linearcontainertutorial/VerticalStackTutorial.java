package ch.nolix.systemtutorial.webguitutorial.linearcontainertutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class VerticalStackTutorial {

  private VerticalStackTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "VerticalStack tutorial",
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

  private static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      //Creates a VerticalStack.
      final var verticalStack = new VerticalStack();

      //Creates 4 Labels.
      final var label1 = new Label().setText("A");
      final var label2 = new Label().setText("B");
      final var label3 = new Label().setText("C");
      final var label4 = new Label().setText("D");

      //Adds the Labels to the VerticalStack.
      verticalStack.addControl(label1, label2, label3, label4);

      //Configures the style of the VerticalStack.
      verticalStack.getStoredStyle().setChildControlMarginForState(ControlState.BASE, 20);

      //Configures the style of the Labels.
      label1.getStoredStyle().setTextSizeForState(ControlState.BASE, 50);
      label2.getStoredStyle().setTextSizeForState(ControlState.BASE, 50);
      label3.getStoredStyle().setTextSizeForState(ControlState.BASE, 50);
      label4.getStoredStyle().setTextSizeForState(ControlState.BASE, 50);

      //Adds the VerticalStack to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(verticalStack);
    }
  }
}
