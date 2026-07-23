/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.control.itemmenu;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.control.dropdownmenu.DropdownMenu;
import ch.nolix.system.webapplication.main.WebClientSession;

/**
 * @author Silvan Wyss
 */
final class DropdownMenuTutorial {
  private DropdownMenuTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("DropdownMenu tutorial", Session.class);

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
      // create a DropdownMenu
      final var dropdownMenu = new DropdownMenu().addItems("red", "blue", "green", "yellow", "orange", "purple");

      // add the DropdownMenu to the GUI of the current Session
      getStoredGui().pushLayerWithRootControl(dropdownMenu);
    }
  }
}
