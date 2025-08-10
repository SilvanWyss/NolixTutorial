package ch.nolix.system.webgui.atomiccontrol;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.button.Button;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.gui.box.HorizontalContentAlignment;
import ch.nolix.systemapi.webgui.atomiccontrol.labelapi.ILabel;
import ch.nolix.systemapi.webgui.main.ControlState;

public final class ButtonTutorial {

  private ButtonTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Button tutorial", Session.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class Session extends WebClientSession<Object> {

    private static final int START_COUNT = 0;

    private int count = START_COUNT;

    private final ILabel countLabel = new Label().setText(String.valueOf(count));

    @Override
    protected void initialize() {

      //Creates incrementButton.
      final var incrementButton = //
      new Button().setText("Increment").setLeftMouseButtonPressAction(this::incrementCountAndUpdateCountLabel);

      //Configures the style of the countLabel.
      countLabel.getStoredStyle().setTextSizeForState(ControlState.BASE, 100);

      //Creates a VerticalStack with the countLabel and the incrementButton.
      final var verticalStack = new VerticalStack().addControl(countLabel, incrementButton);

      //Configures the style of the verticalStack.
      verticalStack.setContentAlignment(HorizontalContentAlignment.CENTER);

      //Adds the VerticalStack to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(verticalStack);
    }

    private void incrementCountAndUpdateCountLabel() {

      //Increments the count.
      count++;

      //Updates the countLabel.
      countLabel.setText(String.valueOf(count));
    }
  }
}
