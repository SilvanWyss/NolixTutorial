package ch.nolix.system.webgui.main;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

final class UrlParameterTutorial {

  private UrlParameterTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "URL parameter tutorial",
      MainSession.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningUrl("http://127.0.0.1/?parameter1=5000&parameter2=60000");

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      final var localUrlParameter1 = getStoredParentClient()
        .getOptionalUrlParameterValueByUrlParameterName("parameter1");

      final var localUrlParameter2 = getStoredParentClient()
        .getOptionalUrlParameterValueByUrlParameterName("parameter2");

      getStoredGui()
        .pushLayerWithRootControl(
          new VerticalStack()
            .addControl(
              new Label()
                .setText("URL parameter 1: " + localUrlParameter1.orElseThrow()),
              new Label()
                .setText("URL parameter 2: " + localUrlParameter2.orElseThrow()))
            .editStyle(s -> s.setChildControlMarginForState(ControlState.BASE, 50)));
    }
  }
}
