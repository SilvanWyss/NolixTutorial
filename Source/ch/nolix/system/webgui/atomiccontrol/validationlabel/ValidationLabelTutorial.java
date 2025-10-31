package ch.nolix.system.webgui.atomiccontrol.validationlabel;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.errorcontrol.invalidargumentexception.UnrepresentingArgumentException;
import ch.nolix.core.errorcontrol.validator.Validator;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.coreapi.misc.variable.LowerCaseVariableCatalog;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.button.Button;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.system.webgui.atomiccontrol.textbox.Textbox;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;

final class ValidationLabelTutorial {
  private ValidationLabelTutorial() {
  }

  public static void main(String[] args) {
    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("ValidationLabel tutorial", Session.class);

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
    private final Textbox numberTextbox = new Textbox();

    @Override
    protected void initialize() {
      //Adds a ValidationLabel to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(
        new VerticalStack()
          .addControl(
            new Label().setText("Enter a positive number:"),
            numberTextbox,
            new ValidationLabel(),
            new Button().setText("Ok").setLeftMouseButtonPressAction(this::enterPositiveNumber)));
    }

    private void enterPositiveNumber() {
      //Gets the input of the numberTextBox.
      final var input = numberTextbox.getText();

      try {
        //Parses the input to a number.
        final var number = Integer.parseInt(input);

        //Asserts that the number is positive.
        Validator.assertThat(number).thatIsNamed(LowerCaseVariableCatalog.NUMBER).isPositive();
      } catch (final NumberFormatException _) {
        //Creates and throws an UnrepresentingArgumentException for the input.
        throw //
        UnrepresentingArgumentException.forArgumentAndArgumentNameAndType(
          input,
          LowerCaseVariableCatalog.INPUT,
          Integer.class);
      }
    }
  }
}
