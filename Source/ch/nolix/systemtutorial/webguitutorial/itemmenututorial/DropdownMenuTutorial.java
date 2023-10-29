package ch.nolix.systemtutorial.webguitutorial.itemmenututorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.itemmenu.DropdownMenu;

public final class DropdownMenuTutorial {

  private DropdownMenuTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "DropdownMenu tutorial",
      MainSession.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    GlobalSequencer
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      //Creates a DropdownMenu.
      final var dropdownMenu = new DropdownMenu()
        .addItemWithText(
          "red",
          "blue",
          "green",
          "yellow",
          "orange",
          "purple");

      //Adds the DropdownMenu to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(dropdownMenu);
    }
  }
}
