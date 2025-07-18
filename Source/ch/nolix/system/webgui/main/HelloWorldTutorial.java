package ch.nolix.system.webgui.main;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class HelloWorldTutorial {

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Hello World tutorial", Session.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class Session extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      //Creates a Label.
      final var label = new Label().setText("Hello World!");

      //Configures the style of the Label.
      label.getStoredStyle().setTextSizeForState(ControlState.BASE, 100);

      //Adds the Label to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(label);
    }
  }

  private HelloWorldTutorial() {
  }
}
