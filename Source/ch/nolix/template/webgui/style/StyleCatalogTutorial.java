package ch.nolix.template.webgui.style;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.graphic.image.Image;
import ch.nolix.system.time.moment.Time;
import ch.nolix.system.webapplication.main.WebClientSession;
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

final class StyleCatalogTutorial {

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("StyleCatalog tutorial", Session.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class Session //NOSONAR: A single-file-tutorial is allowed to have a medium-sized static class.
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
                  new Label().setText("Select style:"),
                  new DropdownMenu()
                    .addItemWithTextAndSelectAction("none", () -> getStoredGui().removeStyle())
                    .addItemWithTextAndSelectAction(
                      "Dark mode",
                      () -> getStoredGui().setStyle(StyleCatalog.DARK_STYLE))),
              new Grid()
                .insertTextAtRowAndColumn(1, 1, "Textbox")
                .insertControlAtRowAndColumn(1, 2, new Textbox())
                .insertTextAtRowAndColumn(2, 1, "Link")
                .insertControlAtRowAndColumn(2, 2, new Link().setDisplayText("nolix.ch").setUrl("https://nolix.ch"))
                .insertTextAtRowAndColumn(3, 1, "Button")
                .insertControlAtRowAndColumn(
                  3,
                  2,
                  new Button()
                    .setText("Show current year")
                    .setLeftMouseButtonPressAction(
                      () -> //
                      getStoredGui()
                        .pushLayer(
                          new ShowValueDialogBuilder()
                            .setValueName("Current year")
                            .setValue(String.valueOf(Time.ofNow().getYear()))
                            .build())))
                .insertTextAtRowAndColumn(4, 1, "ImageControl")
                .insertControlAtRowAndColumn(
                  4,
                  2,
                  new ImageControl().setImage(Image.fromResource("image/singer_building.jpg")))))
        .resetStyleRecursively();
    }
  }

  private StyleCatalogTutorial() {
  }
}
