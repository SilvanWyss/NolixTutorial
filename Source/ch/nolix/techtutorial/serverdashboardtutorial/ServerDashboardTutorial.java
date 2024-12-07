package ch.nolix.techtutorial.serverdashboardtutorial;

import ch.nolix.application.serverdashboard.frontend.main.ServerDashboardApplication;
import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebApplicationService;
import ch.nolix.system.gui.iconresource.IconCatalogue;
import ch.nolix.systemtutorial.webguitutorial.atomiccontroltutorial.ImageControlTutorial;
import ch.nolix.systemtutorial.webguitutorial.maintutorial.HelloWorldGuiTutorial;
import ch.nolix.systemtutorial.webguitutorial.maintutorial.PartialRefreshTutorial;

public final class ServerDashboardTutorial {

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Creates a ServerDashboardApplication for the Server.
    final var serverDashboardApplication = ServerDashboardApplication.forServer(server);

    //Adds the ServerDashboardApplication as default Application to the Server.
    server.addDefaultApplication(serverDashboardApplication);

    //Adds further Applications to the Server.
    server.addApplicationWithNameAndInitialSessionClassAndContext(
      "Hello World GUI tutorial",
      HelloWorldGuiTutorial.MainSession.class,
      new WebApplicationService().setApplicationLogo(IconCatalogue.NOLIX_ICON));
    server.addApplicationWithNameAndInitialSessionClassAndContext(
      "ImageControl tutorial",
      ImageControlTutorial.MainSession.class,
      new VoidObject());
    server.addApplicationWithNameAndInitialSessionClassAndContext(
      "Partial refresh tutorial",
      PartialRefreshTutorial.MainSession.class,
      new VoidObject());

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();
  }
}
