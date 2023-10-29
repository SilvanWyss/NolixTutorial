package ch.nolix.systemtutorial.webguitutorial.linearcontainertutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.system.webgui.linearcontainer.HorizontalStack;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class NestedHorizontalStackTutorial {

  private NestedHorizontalStackTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndContext(
      "Nested HorizontalStack tutorial",
      MainSession.class,
      new VoidObject());

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

      //Creates 3 HorizontalStacks with 2 Texts each.
      final var horizontalStack1 = new HorizontalStack().addControl(new Label().setText("A1"),
        new Label().setText("A2"));
      final var horizontalStack2 = new HorizontalStack().addControl(new Label().setText("B1"),
        new Label().setText("B2"));
      final var horizontalStack3 = new HorizontalStack().addControl(new Label().setText("C1"),
        new Label().setText("C2"));

      //Adds the HorizontalStacks to the VerticalStack.
      verticalStack.addControl(horizontalStack1, horizontalStack2, horizontalStack3);

      //Configures the style of the VerticalStack.
      verticalStack.editStyle(s -> s.setTextSizeForState(ControlState.BASE, 50));
      verticalStack.editStyle(s -> s.setChildControlMarginForState(ControlState.BASE, 20));

      //Configures the style of the HorizontalStacks.
      horizontalStack1.editStyle(s -> s.setChildControlMarginForState(ControlState.BASE, 20));
      horizontalStack2.editStyle(s -> s.setChildControlMarginForState(ControlState.BASE, 20));
      horizontalStack3.editStyle(s -> s.setChildControlMarginForState(ControlState.BASE, 20));

      //Adds the VerticalStack to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(verticalStack);
    }
  }
}
