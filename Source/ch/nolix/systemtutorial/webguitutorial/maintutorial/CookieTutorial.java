package ch.nolix.systemtutorial.webguitutorial.maintutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Button;
import ch.nolix.system.webgui.atomiccontrol.Textbox;
import ch.nolix.system.webgui.linearcontainer.HorizontalStack;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.ITextbox;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class CookieTutorial {

  private CookieTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Cookie tutorial", MainSession.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    GlobalSequencer
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class MainSession extends WebClientSession<Object> {

    private final ITextbox textbox = new Textbox().editStyle(ts -> ts.setBorderThicknessForState(ControlState.BASE, 1));

    @Override
    protected void initialize() {

      final var input = getStoredParentClient().getCookieValueByCookieNameOrNull("input");
      if (input != null) {
        textbox.setText(input);
      }

      getStoredGui().pushLayerWithRootControl(
        new HorizontalStack()
          .addControl(
            textbox,
            new Button()
              .setText("Save input in cookie")
              .setLeftMouseButtonPressAction(this::saveInputInCookie)));
    }

    private void saveInputInCookie() {

      getStoredParentClient().setOrAddCookieWithNameAndValue("input", textbox.getText());

      ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();
    }
  }
}
