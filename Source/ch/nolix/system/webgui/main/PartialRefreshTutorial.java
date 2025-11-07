package ch.nolix.system.webgui.main;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.graphic.color.X11ColorCatalog;
import ch.nolix.system.graphic.image.Image;
import ch.nolix.system.time.moment.Time;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webatomiccontrol.imagecontrol.ImageControl;
import ch.nolix.system.webatomiccontrol.label.Label;
import ch.nolix.system.webcontainercontrol.verticalstack.VerticalStack;
import ch.nolix.systemapi.graphic.image.IImage;
import ch.nolix.systemapi.webatomiccontrol.label.ILabel;
import ch.nolix.systemapi.webgui.main.ControlState;

public final class PartialRefreshTutorial {
  private PartialRefreshTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Partial refresh tutorial", Session.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class Session //NOSONAR: A single-file-tutorial can contain a larger static class.
  extends WebClientSession<Object> {
    private static final IImage IMAGE = Image.fromResource("image/pilatus.jpg").withWidthAndHeight(1200, 600);

    private final ILabel timeLabel = new Label();

    private static String getCurrentTimeAsString() {
      //Gets the currentTime.
      final var currentTime = Time.ofNow();

      //Returns the current time as String.
      return getTimeAsString(currentTime);
    }

    private static String getTimeAsString(final Time time) {
      //Formats the given time to a String.
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
      //Configures the style of the timeLabel.
      timeLabel
        .getStoredStyle()
        .setTextSizeForState(ControlState.BASE, 100)
        .setTextColorForState(ControlState.BASE, X11ColorCatalog.GREY);

      //Adds an ImageContorl and the timeLabel to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(
        new VerticalStack().addControls(new ImageControl().setImage(IMAGE), timeLabel));

      //Updates the timeLabel every 100 milliseconds.
      FlowController.runInBackground(
        () -> {
          //There have to be wait until the client has received the web page.
          FlowController.waitForSeconds(1);

          FlowController
            .asLongAs(this::isAlive)
            .afterEveryMilliseconds(100)
            .runInBackground(this::updateTime);
        });
    }

    private void updateTime() {
      //Sets the current time to the timeLabel.
      timeLabel.setText(getCurrentTimeAsString());

      //Updates the timeLable on the counterpart of the current Session.
      updateControlOnCounterpart(timeLabel, false);
    }
  }
}
