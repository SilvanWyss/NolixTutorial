package ch.nolix.template.webgui.style;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.graphic.image.Image;
import ch.nolix.system.webgui.atomiccontrol.button.Button;
import ch.nolix.system.webgui.atomiccontrol.imagecontrol.ImageControl;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.system.webgui.atomiccontrol.link.Link;
import ch.nolix.system.webgui.atomiccontrol.textbox.Textbox;
import ch.nolix.system.webgui.container.Grid;
import ch.nolix.system.webgui.itemmenu.dropdownmenu.DropdownMenu;
import ch.nolix.system.webgui.linearcontainer.HorizontalStack;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.labelapi.LabelRole;
import ch.nolix.template.webgui.dialog.ShowValueDialogBuilder;

final class StyleCatalogueTutorial {

  private StyleCatalogueTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndContext(
      "StyleCatalogue tutorial",
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
          new VerticalStack()
            .addControl(
              new Label()
                .setRole(LabelRole.TITLE)
                .setText(getApplicationName()),
              new HorizontalStack()
                .addControl(
                  new Label()
                    .setText("Select style:"),
                  new DropdownMenu()
                    .addItemWithTextAndSelectAction("none", () -> getStoredGui().removeStyle())
                    .addItemWithTextAndSelectAction(
                      "Dark mode",
                      () -> getStoredGui().setStyle(StyleCatalog.DARK_STYLE))),
              new Grid()
                .insertTextAtRowAndColumn(1, 1, "Button")
                .insertControlAtRowAndColumn(1, 2, new Button())
                .insertTextAtRowAndColumn(2, 1, "Textbox")
                .insertControlAtRowAndColumn(2, 2, new Textbox())
                .insertTextAtRowAndColumn(3, 1, "Link")
                .insertControlAtRowAndColumn(
                  3,
                  2,
                  new Link()
                    .setDisplayText("nolix.ch")
                    .setUrl("https://nolix.ch"))
                .insertTextAtRowAndColumn(4, 1, "ImageControl")
                .insertControlAtRowAndColumn(
                  4,
                  2,
                  new ImageControl()
                    .setImage(
                      Image.fromResource(
                        "image/singer_building.jpg")))
                .insertTextAtRowAndColumn(5, 1, "Open show value dialog")
                .insertControlAtRowAndColumn(
                  5,
                  2,
                  new Button()
                    .setLeftMouseButtonPressAction(
                      () -> getStoredGui().pushLayer(new ShowValueDialogBuilder().build())))))
        .resetStyleRecursively();
    }
  }
}
