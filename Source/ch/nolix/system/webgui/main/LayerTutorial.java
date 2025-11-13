package ch.nolix.system.webgui.main;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.graphic.color.X11ColorCatalog;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webatomiccontrol.label.Label;
import ch.nolix.systemapi.webgui.main.ControlState;

final class LayerTutorial {
  private LayerTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Layer tutorial", Session.class);

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
      getStoredGui().setTitle("Layer tutorial");

      //Creates layer1Label.
      final var layer1Label = new Label().setText("Layer 1");

      //Creates layer2Label.
      final var layer2Label = new Label().setText("Layer 2");

      //Configures the style of layer1Label.
      layer1Label
        .getStoredStyle()
        .forStateSetTextSize(ControlState.BASE, 100)
        .forStateSetTextColor(ControlState.BASE, X11ColorCatalog.BLACK);

      //Configures the style of layer2Label.
      layer2Label
        .getStoredStyle()
        .forStateSetTextSize(ControlState.BASE, 200)
        .forStateSetTextColor(ControlState.BASE, X11ColorCatalog.GREY);

      //Adds a new layer with the layer1Label to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(layer1Label);

      //Adds a new layer with the layer2Label to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(layer2Label);
    }
  }
}
