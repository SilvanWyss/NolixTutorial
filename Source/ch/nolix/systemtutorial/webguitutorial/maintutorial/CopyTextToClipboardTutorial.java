package ch.nolix.systemtutorial.webguitutorial.maintutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Button;
import ch.nolix.system.webgui.atomiccontrol.Textbox;
import ch.nolix.system.webgui.linearcontainer.HorizontalStack;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class CopyTextToClipboardTutorial {

  private CopyTextToClipboardTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndContext(
      "Copy text to clipboard tutorial",
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

      //Creates inputTextbox.
      final var inputTextbox = new Textbox();

      //Adds an initial text to the inputTextbox.
      inputTextbox.setText("Supercalifragilisticexpialigetisch");

      //Creates rootControl.
      final var rootControl = new HorizontalStack()
        .addControl(
          inputTextbox,
          new Button()
            .setText("Copy text")
            .setLeftMouseButtonPressAction(
              () -> getStoredGui().onFrontEnd().writeTextToClipboard(inputTextbox.getText())));

      //Configures the style of the rootControl.
      rootControl.editStyle(s -> s.setChildControlMarginForState(ControlState.BASE, 10));

      //Configures the style of the inputTextbox.
      inputTextbox.editStyle(s -> s.setWidthForState(ControlState.BASE, 500));

      //Adds the rootControl to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(rootControl);
    }
  }
}
