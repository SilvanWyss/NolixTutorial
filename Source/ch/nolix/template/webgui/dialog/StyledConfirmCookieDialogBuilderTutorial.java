/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.template.webgui.dialog;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webgui.main.Layer;
import ch.nolix.template.webgui.style.StyleCatalog;

/**
 * @author Silvan Wyss
 */
final class StyledConfirmCookieDialogBuilderTutorial {
  private StyledConfirmCookieDialogBuilderTutorial() {
  }

  public static void main() {
    // Create a Server.
    final var server = Server.forHttpPort();

    // Add a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Styled ConfirmCookieDialogBuilder tutorial",
      Session.class);

    // Start a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    // Close the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class Session extends WebClientSession<Object> {
    @Override
    protected void initialize() {
      // Create confirmCookieDialog.
      final var confirmCookieDialogBuilder = new ConfirmCookieDialogBuilder();
      final var confirmCookieDialog = confirmCookieDialogBuilder.build();

      // Add an empty layer to the GUI of the current Session.
      getStoredGui().pushLayer(new Layer());

      // Add a new layer with the confirmCookieDialog to the GUI of the current Session.
      getStoredGui().pushLayer(confirmCookieDialog);

      // Set a style to the GUI of the current Session.
      getStoredGui().setStyle(StyleCatalog.DARK_EDGE_STYLE);
    }
  }
}
