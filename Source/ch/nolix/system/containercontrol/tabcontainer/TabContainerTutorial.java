/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.containercontrol.tabcontainer;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.atomiccontrol.label.Label;
import ch.nolix.system.webapplication.main.WebClientSession;

/**
 * @author Silvan Wyss
 */
final class TabContainerTutorial {
  private TabContainerTutorial() {
  }

  public static void main() {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("TabContainer tutorial", Session.class);

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
      //Creates a TabContainer.
      final var tabContainer = new TabContainer();

      //Creates and adds 4 Tabs to the TabContainer.
      tabContainer.addTabs(
        new TabContainerTab().setHeader("Header 1").setRootControl(new Label().setText("Content 1")),
        new TabContainerTab().setHeader("Header 2").setRootControl(new Label().setText("Content 2")),
        new TabContainerTab().setHeader("Header 3").setRootControl(new Label().setText("Content 3")),
        new TabContainerTab().setHeader("Header 4").setRootControl(new Label().setText("Content 4")));

      //Adds the TabContainer to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(tabContainer);
    }
  }
}
