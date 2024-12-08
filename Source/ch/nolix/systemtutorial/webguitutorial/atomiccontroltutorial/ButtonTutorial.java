package ch.nolix.systemtutorial.webguitutorial.atomiccontroltutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Button;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.ILabel;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class ButtonTutorial {

  private ButtonTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Button tutorial", MainSession.class);

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

    private int count;

    private final ILabel countLabel = new Label().setText(String.valueOf(count));

    @Override
    protected void initialize() {

      //Creates rootControl.
      final var rootControl = //
      new VerticalStack()
        .addControl(
          countLabel,
          new Button().setText("Increment").setLeftMouseButtonPressAction(this::incrementCountAndUpdateCountLabel));

      //Configures the style if the rootControl.
      rootControl.editStyle(s -> s.setPaddingForState(ControlState.BASE, 50));

      //Adds the rootControl to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(rootControl);
    }

    private void incrementCountAndUpdateCountLabel() {

      //Increments the count.
      count++;

      //Updates the countLabel.
      countLabel.setText(String.valueOf(count));
    }
  }
}
