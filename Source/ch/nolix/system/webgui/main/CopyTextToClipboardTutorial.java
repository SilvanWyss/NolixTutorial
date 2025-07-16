package ch.nolix.system.webgui.main;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.button.Button;
import ch.nolix.system.webgui.atomiccontrol.textbox.Textbox;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

final class CopyTextToClipboardTutorial {

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
      inputTextbox.getStoredStyle().setWidthForState(ControlState.BASE, 500);

      //Adds an initial text to the inputTextbox.
      inputTextbox.setText("Supercalifragilisticexpialigetisch");

      //Adds the inputTextbox to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(
        new VerticalStack()
          .addControl(
            inputTextbox,
            new Button()
              .setText("Copy text")
              .setLeftMouseButtonPressAction(
                () -> getStoredGui().onFrontEnd().writeTextToClipboard(inputTextbox.getText()))));
    }
  }

  private CopyTextToClipboardTutorial() {
  }
}
