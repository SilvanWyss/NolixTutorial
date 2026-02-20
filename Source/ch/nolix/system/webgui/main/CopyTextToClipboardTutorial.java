/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webatomiccontrol.button.Button;
import ch.nolix.system.webatomiccontrol.textbox.Textbox;
import ch.nolix.system.webcontainercontrol.verticalstack.VerticalStack;
import ch.nolix.systemapi.webgui.main.ControlState;

/**
 * @author Silvan Wyss
 */
final class CopyTextToClipboardTutorial {
  private CopyTextToClipboardTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Copy text to clipboard tutorial",
      Session.class);

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
      //Creates inputTextbox.
      final var inputTextbox = new Textbox();

      //Configures the style of the inputTextbox.
      inputTextbox.getStoredStyle().forStateSetWidth(ControlState.BASE, 500);

      //Adds an initial text to the inputTextbox.
      inputTextbox.setText("Supercalifragilisticexpialigetisch");

      //Adds the inputTextbox to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(
        new VerticalStack()
          .addControls(
            inputTextbox,
            new Button()
              .setText("Copy text")
              .setLeftMouseButtonPressAction(
                () -> getStoredGui().onFrontEnd().writeTextToClipboard(inputTextbox.getText()))));
    }
  }
}
