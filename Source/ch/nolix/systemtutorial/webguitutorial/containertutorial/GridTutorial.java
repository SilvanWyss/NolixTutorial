package ch.nolix.systemtutorial.webguitutorial.containertutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.system.webgui.container.Grid;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class GridTutorial {

  private GridTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Grid tutorial",
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

  private static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      //Creates a Grid.
      final var grid = new Grid();

      //Inserts Controls into the Grid.
      grid
        .insertControlAtRowAndColumn(1, 1, new Label().setText("A"))
        .insertControlAtRowAndColumn(1, 3, new Label().setText("B"))
        .insertControlAtRowAndColumn(2, 2, new Label().setText("C"))
        .insertControlAtRowAndColumn(2, 4, new Label().setText("D"))
        .insertControlAtRowAndColumn(3, 1, new Label().setText("E"))
        .insertControlAtRowAndColumn(3, 3, new Label().setText("F"))
        .insertControlAtRowAndColumn(4, 2, new Label().setText("G"))
        .insertControlAtRowAndColumn(4, 4, new Label().setText("H"));

      //Configures the style of the Grid.
      grid
        .getStoredStyle()
        .setTextSizeForState(ControlState.BASE, 50);

      //Adds the Grid to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(grid);
    }
  }
}
