/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.control.imagecontrol.ImageControl;
import ch.nolix.system.control.label.Label;
import ch.nolix.system.control.verticalstack.VerticalStack;
import ch.nolix.system.graphic.color.X11ColorCatalog;
import ch.nolix.system.graphic.image.ImmutableImage;
import ch.nolix.system.time.moment.Time;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.control.label.ILabel;
import ch.nolix.systemapi.graphic.image.IImage;
import ch.nolix.systemapi.time.timestructure.TimeZone;
import ch.nolix.systemapi.webgui.main.ControlState;

public final class PartialRefreshTutorial {
  private PartialRefreshTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Partial refresh tutorial", Session.class);

    // start a web browser that will connect to the Server
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    // close the Server as soon as it does not have a client connected any more
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class Session // NOSONAR: A single-file-tutorial can contain a larger static class.
  extends WebClientSession<Object> {
    private static final IImage IMAGE = ImmutableImage.fromResource("image/pilatus.jpg").withWidthAndHeight(1200, 600);

    private final ILabel timeLabel = new Label();

    private static String getCurrentTimeAsString() {
      // get the currentTime.
      final var currentTime = Time.ofNowAndTimeZone(TimeZone.UTC);

      // return the current time as String
      return getTimeAsString(currentTime);
    }

    private static String getTimeAsString(final Time time) {
      // formats the given time to a String
      return //
      String.format(
        "%02d:%02d:%02d:%d00",
        time.getHourOfDay(),
        time.getMinuteOfHour(),
        time.getSecondOfMinute(),
        time.getMillisecondOfSecond() / 100);
    }

    @Override
    protected void initialize() {
      // configure the style of the timeLabel
      timeLabel
        .getStoredStyle()
        .forStateSetTextSize(ControlState.BASE, 100)
        .forStateSetTextColor(ControlState.BASE, X11ColorCatalog.GREY);

      // add an ImageContorl and the timeLabel to the GUI of the current Session
      getStoredGui().pushLayerWithRootControl(
        new VerticalStack().addControls(new ImageControl().setImage(IMAGE), timeLabel));

      // update the timeLabel every 100 milliseconds.
      FlowController.runInBackground(
        () -> {
          // there have to be wait until the client has received the web page
          FlowController.waitForSeconds(1);

          FlowController
            .asLongAs(this::isAlive)
            .afterEveryMilliseconds(100)
            .runInBackground(this::updateTime);
        });
    }

    private void updateTime() {
      // set the current time to the timeLabel
      timeLabel.setText(getCurrentTimeAsString());

      // update the timeLable on the counterpart of the current Session
      updateControlOnCounterpart(timeLabel, false);
    }
  }
}
