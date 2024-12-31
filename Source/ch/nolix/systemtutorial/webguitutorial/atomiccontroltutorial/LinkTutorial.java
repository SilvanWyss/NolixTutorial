package ch.nolix.systemtutorial.webguitutorial.atomiccontroltutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.coreapi.webapi.webproperty.LinkTarget;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.link.Link;
import ch.nolix.system.webgui.container.SingleContainer;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class LinkTutorial {

  private LinkTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Link tutorial", MainSession.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    GlobalSequencer
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      //Creates a Link.
      final var link = new Link().setUrlAndDisplayTextFromIt("https://nolix.ch").setTarget(LinkTarget.NEW_TAB);

      //Adds the Link to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(
        new SingleContainer().setControl(link).editStyle(s -> s.setPaddingForState(ControlState.BASE, 50)));
    }
  }
}
