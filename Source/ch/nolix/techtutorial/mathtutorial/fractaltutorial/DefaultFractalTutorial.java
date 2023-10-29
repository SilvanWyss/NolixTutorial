package ch.nolix.techtutorial.mathtutorial.fractaltutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.ImageControl;
import ch.nolix.tech.math.fractal.FractalBuilder;

public final class DefaultFractalTutorial {

  private DefaultFractalTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndContext(
      "Default fractal tutorial",
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

  private static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      getStoredGui()
        .pushLayerWithRootControl(
          new ImageControl().setImage(new FractalBuilder().build().startImageGeneration().getStoredImage()));

      GlobalSequencer
        .asLongAs(this::isAlive)
        .afterEverySecond()
        .runInBackground(this::refresh);
    }
  }
}
