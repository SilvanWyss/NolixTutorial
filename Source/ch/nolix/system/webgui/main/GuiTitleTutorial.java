/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webapplication.main.WebClientSession;

/**
 * @author Silvan Wyss
 */
final class GuiTitleTutorial {
  private GuiTitleTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("GUI title tutorial", Session.class);

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
      //Sets the title of the GUI of the current Session.
      getStoredGui().setTitle("---Demo---");
    }
  }
}
