/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.control.button.Button;
import ch.nolix.system.control.horizontalstack.HorizontalStack;
import ch.nolix.system.control.textbox.Textbox;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.control.textbox.ITextbox;
import ch.nolix.systemapi.webgui.webguiproperty.ControlState;

/**
 * @author Silvan Wyss
 */
final class CookieTutorial {
  private CookieTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Cookie tutorial", Session.class);

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
    private final ITextbox textbox = new Textbox().editStyle(ts -> ts.forStateSetBorderThickness(ControlState.BASE, 1));

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
