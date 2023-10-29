package ch.nolix.systemtutorial.webguitutorial.linearcontainertutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.system.webgui.linearcontainer.HorizontalStack;
import ch.nolix.systemapi.guiapi.structureproperty.VerticalContentAlignment;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class HorizontalStackWithBottomContentAlignmentTutorial {

  private HorizontalStackWithBottomContentAlignmentTutorial() {
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
    GlobalSequencer
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected).runInBackground(server::close);
  }

  private static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      //Creates a HorizontalStack.
      final var horizontalStack = new HorizontalStack();

      //Creates 4 Labels.
      final var label1 = new Label().setText("Ap");
      final var label2 = new Label().setText("Bp");
      final var label3 = new Label().setText("Cp");
      final var label4 = new Label().setText("Dp");

      //Adds the Labels to the HorizontalStack.
      horizontalStack.addControl(label1, label2, label3, label4);

      //Configures the style of the HorizontalStack.
      horizontalStack
        .setContentAlignment(VerticalContentAlignment.BOTTOM)
        .editStyle(s -> s.setChildControlMarginForState(ControlState.BASE, 20));

      //Configures the style of the Labels.
      label1.editStyle(s -> s.setTextSizeForState(ControlState.BASE, 50));
      label2.editStyle(s -> s.setTextSizeForState(ControlState.BASE, 20));
      label3.editStyle(s -> s.setTextSizeForState(ControlState.BASE, 20));
      label4.editStyle(s -> s.setTextSizeForState(ControlState.BASE, 20));

      //Adds the HorizontalStack to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(horizontalStack);
    }
  }
}
