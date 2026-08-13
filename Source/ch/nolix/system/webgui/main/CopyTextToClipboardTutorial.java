/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.control.button.Button;
import ch.nolix.system.control.textbox.Textbox;
import ch.nolix.system.control.verticalstack.VerticalStack;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.webgui.webguiproperty.ControlState;

/**
 * @author Silvan Wyss
 */
final class CopyTextToClipboardTutorial {
  private CopyTextToClipboardTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Copy text to clipboard tutorial",
      Session.class);

    // start a web browser that will connect to the Server
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    // close the Server as soon as it does not have a client connected any more
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class Session extends WebClientSession<Object> {
    @Override
    protected void initialize() {
      // create inputTextbox.
      final var inputTextbox = new Textbox();

      // configure the style of the inputTextbox
      inputTextbox.getStoredStyle().forStateSetWidth(ControlState.BASE, 500);

      // add an initial text to the inputTextbox
      inputTextbox.setText("Supercalifragilisticexpialigetisch");

      // add the inputTextbox to the GUI of the current Session
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
