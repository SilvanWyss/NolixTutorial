/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.control.button;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.control.label.Label;
import ch.nolix.system.control.verticalstack.VerticalStack;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.control.label.ILabel;
import ch.nolix.systemapi.gui.guiproperty.HorizontalContentAlignment;
import ch.nolix.systemapi.webgui.webguiproperty.ControlState;

public final class ButtonTutorial {
  private ButtonTutorial() {
  }

  public static void main() {
    // Create a Server.
    final var server = Server.forHttpPort();

    // Add a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Button tutorial", Session.class);

    // Start a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    // Close the Server as soon as it does not have a client connected any more.
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
      // Create incrementButton.
      final var incrementButton = //
      new Button().setText("Increment").setLeftMouseButtonPressAction(this::incrementCountAndUpdateCountLabel);

      // Configure the style of the countLabel.
      countLabel.getStoredStyle().forStateSetTextSize(ControlState.BASE, 100);

      // Create a VerticalStack with the countLabel and the incrementButton.
      final var verticalStack = new VerticalStack().addControls(countLabel, incrementButton);

      // Configure the style of the verticalStack.
      verticalStack.setContentAlignment(HorizontalContentAlignment.CENTER);

      // Add the VerticalStack to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(verticalStack);
    }

    private void incrementCountAndUpdateCountLabel() {
      // Increment the count.
      count++;

      // Update the countLabel.
      countLabel.setText(String.valueOf(count));
    }
  }
}
