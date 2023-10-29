package ch.nolix.systemtutorial.webguitutorial.maintutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class UrlParameterTutorial {

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
    GlobalSequencer
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      final var localUrlParameter1 = getStoredParentClient().getUrlParameterValueByUrlParameterNameOrNull("parameter1");

      final var localUrlParameter2 = getStoredParentClient().getUrlParameterValueByUrlParameterNameOrNull("parameter2");

      getStoredGui()
        .pushLayerWithRootControl(
          new VerticalStack()
            .addControl(
              new Label()
                .setText("URL parameter 1: " + localUrlParameter1),
              new Label()
                .setText("URL parameter 2: " + localUrlParameter2))
            .editStyle(s -> s.setChildControlMarginForState(ControlState.BASE, 50)));
    }
  }
}
