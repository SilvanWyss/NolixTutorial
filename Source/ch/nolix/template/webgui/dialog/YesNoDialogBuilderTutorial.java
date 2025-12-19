package ch.nolix.template.webgui.dialog;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.graphic.color.X11ColorCatalog;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webatomiccontrol.button.Button;

/**
 * @author Silvan Wyss
 */
final class YesNoDialogBuilderTutorial {
  private YesNoDialogBuilderTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "YesNoDialogBuilder tutorial",
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
      //Adds a Button, that leads to a yes-no-dialog, to the GUI of the current Session.
      getStoredGui()
        .pushLayerWithRootControl(
          new Button()
            .setText("Go")
            .setLeftMouseButtonPressAction(
              () -> //
              getStoredGui()
                .pushLayer(
                  new YesNoDialogBuilder()
                    .setYesNoQuestion("Do you want to open nolix.ch?")
                    .setConfirmAction(() -> getStoredGui().onFrontEnd().openNewTabWithUrl("https://nolix.ch"))
                    .build()
                    .setBackgroundColor(X11ColorCatalog.WHITE))));
    }
  }
}
