package ch.nolix.system.webgui.main;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webatomiccontrol.button.Button;
import ch.nolix.system.webatomiccontrol.textbox.Textbox;
import ch.nolix.system.webcontainercontrol.horizontalstack.HorizontalStack;
import ch.nolix.systemapi.webatomiccontrol.textbox.ITextbox;
import ch.nolix.systemapi.webgui.main.ControlState;

final class CookieTutorial {
  private CookieTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Cookie tutorial", Session.class);

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
    private final ITextbox textbox = new Textbox().editStyle(ts -> ts.setBorderThicknessForState(ControlState.BASE, 1));

    @Override
    protected void initialize() {
      final var cookieValue = getStoredParentClient().getOptionalCookieValueByCookieName("myCookie");

      if (cookieValue.isPresent()) {
        textbox.setText(cookieValue.get());
      }

      getStoredGui().pushLayerWithRootControl(
        new HorizontalStack()
          .addControls(
            textbox,
            new Button()
              .setText("Save text in cookie")
              .setLeftMouseButtonPressAction(this::saveTextInCookie)));
    }

    private void saveTextInCookie() {
      getStoredParentClient().setOrAddCookieWithNameAndValue("myCookie", textbox.getText());

      ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();
    }
  }
}
