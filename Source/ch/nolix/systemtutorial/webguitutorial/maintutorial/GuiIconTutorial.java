package ch.nolix.systemtutorial.webguitutorial.maintutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.graphic.image.Image;

public final class GuiIconTutorial {

  private GuiIconTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "GUI icon tutorial",
      MainSession.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    GlobalSequencer.waitForSeconds(2);
    GlobalSequencer.asSoonAsNoMore(server::hasClientConnected).runInBackground(server::close);
  }

  public static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {
      getStoredGui().setIcon(Image.fromResource("ch/nolix/systemtutorial/webguitutorial/resource/matterhorn.jpg"));
    }
  }
}
