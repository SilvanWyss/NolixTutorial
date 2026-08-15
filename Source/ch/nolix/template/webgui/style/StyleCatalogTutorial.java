/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.template.webgui.style;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.control.button.Button;
import ch.nolix.system.control.dropdownmenu.DropdownMenu;
import ch.nolix.system.control.grid.Grid;
import ch.nolix.system.control.horizontalstack.HorizontalStack;
import ch.nolix.system.control.imagecontrol.ImageControl;
import ch.nolix.system.control.label.Label;
import ch.nolix.system.control.link.Link;
import ch.nolix.system.control.textbox.Textbox;
import ch.nolix.system.control.verticalstack.VerticalStack;
import ch.nolix.system.graphic.image.ImmutableImage;
import ch.nolix.system.time.main.Time;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.control.label.LabelRole;
import ch.nolix.systemapi.time.main.TimeZone;
import ch.nolix.template.webgui.dialog.ShowValueDialogBuilder;

/**
 * @author Silvan Wyss
 */
final class StyleCatalogTutorial {
  private StyleCatalogTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("StyleCatalog tutorial", Session.class);

    // start a web browser that will connect to the Server
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    // close the Server as soon as it does not have a client connected any more
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class Session // NOSONAR: A single-file-tutorial can contain a larger static class.
  extends WebClientSession<Object> {
    @Override
    protected void initialize() {
      getStoredGui()
        .pushLayerWithRootControl(
          new VerticalStack()
            .addControls(
              new Label()
                .setRole(LabelRole.TITLE)
                .setText(getApplicationName()),
              new HorizontalStack()
                .addControls(
                  new HorizontalStack()
                    .addControls(
                      new Label().setText("Select style:"),
                      new DropdownMenu()
                        .addItemWithTextAndSelectAction("none", () -> getStoredGui().removeStyle())
                        .addItemWithTextAndSelectAction(
                          "Dark edge style",
                          () -> getStoredGui().setStyle(StyleCatalog.DARK_EDGE_STYLE))
                        .addItemWithTextAndSelectAction(
                          "Parchment edge style",
                          () -> getStoredGui().setStyle(StyleCatalog.PARCHMENT_EDGE_STYLE))),
                  new Grid()
                    .insertTextAtRowAndColumn(1, 1, "Textbox:")
                    .insertControlAtRowAndColumn(1, 2, new Textbox())
                    .insertTextAtRowAndColumn(2, 1, "Link:")
                    .insertControlAtRowAndColumn(2, 2, new Link().setDisplayText("nolix.ch").setUrl("https://nolix.ch"))
                    .insertTextAtRowAndColumn(3, 1, "Button:")
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
                                .setValue(String.valueOf(Time.ofNowAndTimeZone(TimeZone.UTC).getYear()))
                                .build())))
                    .insertTextAtRowAndColumn(4, 1, "ImageControl:")
                    .insertControlAtRowAndColumn(
                      4,
                      2,
                      new ImageControl().setImage(ImmutableImage.fromResource("image/singer_building.jpg"))))))
        .resetStyleRecursively();
    }
  }
}
