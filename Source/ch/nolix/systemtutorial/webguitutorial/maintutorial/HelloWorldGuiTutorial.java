package ch.nolix.systemtutorial.webguitutorial.maintutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class HelloWorldGuiTutorial {

  private HelloWorldGuiTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndContext(
      "Hello World GUI tutorial",
      MainSession.class,
      new VoidObject());

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

      //Creates Label.
      final var label = new Label().setText("Hello World!");

      //Configures the style of the Label.
      label.getStoredStyle().setTextSizeForState(ControlState.BASE, 50);

      //Adds the Label to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(label);
    }
  }
}
