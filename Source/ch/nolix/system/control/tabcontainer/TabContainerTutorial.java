/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.control.tabcontainer;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.control.label.Label;
import ch.nolix.system.webapplication.main.WebClientSession;

/**
 * @author Silvan Wyss
 */
final class TabContainerTutorial {
  private TabContainerTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("TabContainer tutorial", Session.class);

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
      // create a TabContainer
      final var tabContainer = new TabContainer();

      // create and adds 4 Tabs to the TabContainer
      tabContainer.addTabs(
        new TabContainerTab().setHeader("Header 1").setRootControl(new Label().setText("Content 1")),
        new TabContainerTab().setHeader("Header 2").setRootControl(new Label().setText("Content 2")),
        new TabContainerTab().setHeader("Header 3").setRootControl(new Label().setText("Content 3")),
        new TabContainerTab().setHeader("Header 4").setRootControl(new Label().setText("Content 4")));

      // add the TabContainer to the GUI of the current Session
      getStoredGui().pushLayerWithRootControl(tabContainer);
    }
  }
}
