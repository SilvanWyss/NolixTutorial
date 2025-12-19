package ch.nolix.tech.math.fractal;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.graphic.color.Color;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webatomiccontrol.imagecontrol.ImageControl;
import ch.nolix.tech.math.bigdecimalmath.ComplexNumber;
import ch.nolix.tech.math.bigdecimalmath.ComplexSequenceDefinedBy1Predecessor;

/**
 * @author Silvan Wyss
 */
final class MandelbrotFractalTutorial {
  private MandelbrotFractalTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Mandelbrot fractal tutorial",
      Session.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class Session //NOSONAR: A single-file-tutorial can contain a larger static class.
  extends WebClientSession<Object> {
    @Override
    protected void initialize() {
      getStoredGui()
        .pushLayerWithRootControl(
          new ImageControl()
            .setImage(
              new FractalBuilder()
                .setRealComponentInterval(-2.0, 1.0)
                .setImaginaryComponentInterval(-1.5, 1.5)
                .setWidthInPixel(500)
                .setHeightInPixel(500)
                .setSequenceCreator(
                  z -> //
                  ComplexSequenceDefinedBy1Predecessor.withFirstValueAndNextValueFunction(
                    new ComplexNumber(0.0, 0.0),
                    p -> p.getPower2().getSum(z)))
                .setMinMagnitudeForDivergence(10.0)
                .setMaxIterationCount(50)
                .setColorFunction(
                  i -> Color.withRedValueAndGreenValueAndBlueValue((2 * i) % 256, (3 * i) % 256,
                    (4 * i) % 256))
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
