package ch.nolix.system.webgui.main;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;

final class UrlParameterTutorial {

  private UrlParameterTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("URL parameter tutorial", Session.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningUrl("http://127.0.0.1/?param1=5000&param2=60000");

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

      //Gets the Url parameters param1 and param2 from the Url.
      final var param1 = getStoredParentClient().getOptionalUrlParameterValueByUrlParameterName("param1");
      final var param2 = getStoredParentClient().getOptionalUrlParameterValueByUrlParameterName("param2");

      //Display the Url parameters param1 and param2 on the GUI of the current Session.
      getStoredGui()
        .pushLayerWithRootControl(
          new VerticalStack()
            .addControl(
              new Label()
                .setText("URL parameter param1: " + param1.orElseThrow()),
              new Label()
                .setText("URL parameter param2: " + param2.orElseThrow())));
    }
  }
}
