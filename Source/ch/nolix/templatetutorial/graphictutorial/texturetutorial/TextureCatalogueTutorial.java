package ch.nolix.templatetutorial.graphictutorial.texturetutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.imagecontrol.ImageControl;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.system.webgui.linearcontainer.FloatContainer;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;
import ch.nolix.template.graphic.texture.TextureCatalog;

public final class TextureCatalogueTutorial {

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Hello World GUI tutorial",
      MainSession.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class MainSession //NOSONAR: A single-file-tutorial is allowed to have a long static class.
  extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      //Creates textures.
      final var concreteTexture = TextureCatalog.CONCRETE_TEXTURE.toScaledImage(10);
      final var fireWoodTexture = TextureCatalog.FIR_WOOD_TEXTURE.toScaledImage(10);
      final var juteTexture = TextureCatalog.JUTE_TEXTURE.toScaledImage(10);
      final var parchmentTexture = TextureCatalog.PARCHMENT_TEXTURE.toScaledImage(10);
      final var whiteMarbleTexture = TextureCatalog.WHITE_MARBLE_TEXTURE.toScaledImage(10);

      //Adds the textures to the GUI of the current MainSession.
      getStoredGui()
        .setTitle("TextureCatalogue Tutorial")
        .pushLayerWithRootControl(
          new FloatContainer()
            .setMaxWidth(1000)
            .editStyle(
              s -> s.setPaddingForState(ControlState.BASE, 20).setChildControlMarginForState(ControlState.BASE,
                20))
            .addControl(
              new VerticalStack()
                .addControl(
                  new Label()
                    .setText("Concrete"),
                  new ImageControl()
                    .setImage(concreteTexture)),
              new VerticalStack()
                .addControl(
                  new Label()
                    .setText("Fir Wood"),
                  new ImageControl()
                    .setImage(fireWoodTexture)),
              new VerticalStack()
                .addControl(
                  new Label()
                    .setText("Jute"),
                  new ImageControl()
                    .setImage(juteTexture)),
              new VerticalStack()
                .addControl(
                  new Label()
                    .setText("Parchment"),
                  new ImageControl()
                    .setImage(parchmentTexture)),
              new VerticalStack()
                .addControl(
                  new Label()
                    .setText("White Marble"),
                  new ImageControl()
                    .setImage(whiteMarbleTexture))));
    }
  }
}
