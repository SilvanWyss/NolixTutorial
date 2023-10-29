package ch.nolix.systemtutorial.webguitutorial.atomiccontroltutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.errorcontrol.validator.GlobalValidator;
import ch.nolix.core.programatom.name.LowerCaseCatalogue;
import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Button;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.system.webgui.atomiccontrol.Textbox;
import ch.nolix.system.webgui.atomiccontrol.ValidationLabel;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;

public final class ValidationLabelTutorial {

  private ValidationLabelTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndContext(
      "ValidationLabel tutorial",
      MainSession.class,
      new VoidObject());

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

    private final Textbox positiveNumberTextbox = new Textbox();

    @Override
    protected void initialize() {
      getStoredGui().pushLayerWithRootControl(
        new VerticalStack()
          .addControl(
            new Label().setText("Enter a positive number"),
            new ValidationLabel(),
            positiveNumberTextbox,
            new Button().setText("Ok").setLeftMouseButtonPressAction(this::enterPositiveNumber)));
    }

    private void enterPositiveNumber() {

      final var number = Integer.parseInt(positiveNumberTextbox.getText());

      GlobalValidator.assertThat(number).thatIsNamed(LowerCaseCatalogue.NUMBER).isPositive();
    }
  }
}
