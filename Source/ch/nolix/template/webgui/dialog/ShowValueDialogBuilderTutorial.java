/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.template.webgui.dialog;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.baseapi.generalcatalog.variablenamecatalog.LowerCaseVariableNameCatalog;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.control.button.Button;
import ch.nolix.system.graphic.color.X11ColorCatalog;
import ch.nolix.system.time.moment.Time;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.time.timestructure.TimeZone;

/**
 * @author Silvan Wyss
 */
final class ShowValueDialogBuilderTutorial {
  private ShowValueDialogBuilderTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "ShowValueDialogBuilder tutorial",
      Session.class);

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
    private static String getDateAsStringFromTime(final Time time) {
      // formats the given time to a date String
      return String.format("%02d.%02d.%04d", time.getDayOfMonth(), time.getMonthOfYearAsInt(), time.getYear());
    }

    @Override
    protected void initialize() {
      // add a Button, that leads to a dialog to show the date, to the GUI of the current Session
      getStoredGui()
        .pushLayerWithRootControl(
          new Button().setText("Show date").setLeftMouseButtonPressAction(this::showDate));
    }

    private void showDate() {
      // get the current time
      final var currentTime = Time.ofNowAndTimeZone(TimeZone.UTC);

      // get a String with the date from the currentTime
      final var dateString = getDateAsStringFromTime(currentTime);

      // create a dialog that shows the dateString
      final var showDateDialog = //
      new ShowValueDialogBuilder()
        .setValueName(LowerCaseVariableNameCatalog.DATE)
        .setValue(dateString).build()
        .setBackgroundColor(X11ColorCatalog.WHITE);

      // add a new layer with the showDateDialog to the GUI of the current Session
      getStoredGui().pushLayer(showDateDialog);
    }
  }
}
