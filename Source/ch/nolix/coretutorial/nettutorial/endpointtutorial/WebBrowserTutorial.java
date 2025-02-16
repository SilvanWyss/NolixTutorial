package ch.nolix.coretutorial.nettutorial.endpointtutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.net.endpoint.Server;
import ch.nolix.core.programcontrol.flowcontrol.GlobalFlowController;

public final class WebBrowserTutorial {

  private WebBrowserTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    try (final var server = Server.forHttpPort()) {

      //Starts a web browser that will connect to the Server.
      ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

      //Closes the Server after 2 seconds.
      GlobalFlowController.waitForSeconds(2);
    }
  }
}
