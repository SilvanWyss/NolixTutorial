package ch.nolix.application.serverdashboard.frontend.main;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webgui.atomiccontrol.UploaderTutorial;
import ch.nolix.system.webgui.main.HelloWorldTutorial;
import ch.nolix.system.webgui.main.PartialRefreshTutorial;

final class ServerDashboardApplicationTutorial {

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Creates a ServerDashboardApplication for the Server.
    final var serverDashboardApplication = ServerDashboardApplication.forServer(server);

    //Adds the ServerDashboardApplication as default Application to the Server.
    server.addDefaultApplication(serverDashboardApplication);

    //Creates and adds more Applications to the Server.
    server.addApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Hello World tutorial",
      HelloWorldTutorial.Session.class);
    server.addApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Partial refresh tutorial",
      PartialRefreshTutorial.Session.class);
    server.addApplicationWithNameAndInitialSessionClassAndVoidContext(
      "ImageControl tutorial",
      UploaderTutorial.Session.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();
  }

  private ServerDashboardApplicationTutorial() {
  }
}
