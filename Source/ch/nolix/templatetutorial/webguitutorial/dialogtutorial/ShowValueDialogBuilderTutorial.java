package ch.nolix.templatetutorial.webguitutorial.dialogtutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programatom.name.LowerCaseCatalogue;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.time.moment.Time;
import ch.nolix.system.webgui.atomiccontrol.Button;
import ch.nolix.template.webgui.dialog.ShowValueDialogBuilder;

public final class ShowValueDialogBuilderTutorial {

  private ShowValueDialogBuilderTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "ShowValueDialogBuilder tutorial",
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

  private static final class MainSession extends WebClientSession<Object> {

    @Override
    protected void initialize() {
      getStoredGui().pushLayerWithRootControl(
        new Button()
          .setText("Show date")
          .setLeftMouseButtonPressAction(this::showDate));
    }

    private void showDate() {

      final var time = Time.ofNow();

      final var dateString = String.format("%02d.%02d.%04d", time.getDayOfMonth(), time.getMonthOfYearAsInt(),
        time.getYearAsInt());

      final var showDateDialog = new ShowValueDialogBuilder().setValueName(LowerCaseCatalogue.DATE).setValue(dateString)
        .build();

      getStoredGui().pushLayer(showDateDialog);
    }
  }
}
