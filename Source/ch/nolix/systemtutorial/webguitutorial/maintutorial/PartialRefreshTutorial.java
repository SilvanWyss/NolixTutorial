package ch.nolix.systemtutorial.webguitutorial.maintutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.graphic.color.Color;
import ch.nolix.system.graphic.image.Image;
import ch.nolix.system.time.moment.Time;
import ch.nolix.system.webgui.atomiccontrol.ImageControl;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.graphicapi.imageapi.IImage;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.ILabel;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

public final class PartialRefreshTutorial {

  private static final IImage IMAGE = Image.fromResource("ch/nolix/systemtutorial/webguitutorial/resource/pilatus.jpg")
    .withWidthAndHeight(1200, 600);

  private PartialRefreshTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Partial refresh tutorial",
      MainSession.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    GlobalSequencer
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  public static final class MainSession //NOSONAR: A single-file-tutorial is allowed to have a long static class.
  extends WebClientSession<Object> {

    private final ILabel timeLabel = new Label()
      .editStyle(
        s -> s
          .setTextSizeForState(ControlState.BASE, 50)
          .setTextColorForState(ControlState.BASE, Color.GREY));

    @Override
    protected void initialize() {

      getStoredGui()
        .pushLayerWithRootControl(
          new VerticalStack()
            .addControl(
              new ImageControl().setImage(IMAGE),
              timeLabel));

      GlobalSequencer
        .runInBackground(
          () -> {

            //We must wait until the client has received the first version of the page.
            GlobalSequencer.waitForMilliseconds(1000);

            GlobalSequencer
              .asLongAs(this::isAlive)
              .afterEveryMilliseconds(50)
              .runInBackground(this::updateTime);
          });
    }

    private void updateTime() {

      timeLabel.setText(getCurrentTimeText());

      refreshControl(timeLabel);
    }

    private String getCurrentTimeText() {
      return getTimeAsText(Time.ofNow());
    }

    private String getTimeAsText(final Time time) {
      return String.format(
        "%02d:%02d:%02d.%d",
        time.getHourOfDay(),
        time.getMinuteOfHour(),
        time.getSecondOfMinute(),
        time.getMillisecondOfSecond() / 100);
    }
  }
}
