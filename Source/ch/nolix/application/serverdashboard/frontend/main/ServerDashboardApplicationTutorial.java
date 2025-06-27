package ch.nolix.application.serverdashboard.frontend.main;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webgui.atomiccontrol.ImageControlTutorial;
import ch.nolix.system.webgui.main.HelloWorldTutorial;
import ch.nolix.system.webgui.main.PartialRefreshTutorial;

public final class ServerDashboardApplicationTutorial {

  private ServerDashboardApplicationTutorial() {
  }

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
      "ImageControl tutorial",
      ImageControlTutorial.MainSession.class);
    server.addApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Partial refresh tutorial",
      PartialRefreshTutorial.MainSession.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();
  }
}
