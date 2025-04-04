package ch.nolix.techtutorial.mathtutorial.fractaltutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.graphic.color.Color;
import ch.nolix.system.webgui.atomiccontrol.imagecontrol.ImageControl;
import ch.nolix.tech.math.bigdecimalmath.ComplexNumber;
import ch.nolix.tech.math.bigdecimalmath.ComplexSequenceDefinedBy1Predecessor;
import ch.nolix.tech.math.fractal.FractalBuilder;

public final class CustomFractalTutorial {

  private CustomFractalTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndContext(
      "Custom fractal tutorial",
      MainSession.class,
      new VoidObject());

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class MainSession //NOSONAR: A single-file-tutorial is allowed to have a long static class.
  extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      getStoredGui()
        .pushLayerWithRootControl(
          new ImageControl()
            .setImage(
              new FractalBuilder()
                .setRealComponentInterval(-1.5, 1.5)
                .setImaginaryComponentInterval(-1.5, 1.5)
                .setWidthInPixel(500)
                .setHeightInPixel(500)
                .setSequenceCreator(
                  z -> new ComplexSequenceDefinedBy1Predecessor(
                    new ComplexNumber(0.0, 0.0),
                    p -> p.getPower4().getSum(z)))
                .setMinMagnitudeForDivergence(10.0)
                .setMaxIterationCount(50)
                .setColorFunction(
                  i -> Color.withRedValueAndGreenValueAndBlueValue((2 * i) % 256, (10 * i) % 256,
                    (3 * i) % 256))
                .setDecimalPlaces(10)
                .build()
                .startImageGeneration()
                .getStoredImage()));

      FlowController
        .asLongAs(this::isAlive)
        .afterEverySecond()
        .runInBackground(this::refresh);
    }
  }
}
