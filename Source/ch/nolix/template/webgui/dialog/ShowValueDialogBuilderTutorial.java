/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.template.webgui.dialog;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.coreapi.misc.variable.LowerCaseVariableCatalog;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.graphic.color.X11ColorCatalog;
import ch.nolix.system.time.moment.Time;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webatomiccontrol.button.Button;

/**
 * @author Silvan Wyss
 */
final class ShowValueDialogBuilderTutorial {
  private ShowValueDialogBuilderTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "ShowValueDialogBuilder tutorial",
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
    private static String getDateAsStringFromTime(final Time time) {
      //Formats the given time to a date String.
      return String.format("%02d.%02d.%04d", time.getDayOfMonth(), time.getMonthOfYearAsInt(), time.getYear());
    }

    @Override
    protected void initialize() {
      //Adds a Button, that leads to a dialog to show the date, to the GUI of the current Session.
      getStoredGui()
        .pushLayerWithRootControl(
          new Button().setText("Show date").setLeftMouseButtonPressAction(this::showDate));
    }

    private void showDate() {
      //Gets the current time.
      final var currentTime = Time.ofNow();

      //Gets a String with the date from the currentTime.
      final var dateString = getDateAsStringFromTime(currentTime);

      //Creates a dialog that shows the dateString.
      final var showDateDialog = //
      new ShowValueDialogBuilder()
        .setValueName(LowerCaseVariableCatalog.DATE)
        .setValue(dateString).build()
        .setBackgroundColor(X11ColorCatalog.WHITE);

      //Adds a new layer with the showDateDialog to the GUI of the current Session.
      getStoredGui().pushLayer(showDateDialog);
    }
  }
}
