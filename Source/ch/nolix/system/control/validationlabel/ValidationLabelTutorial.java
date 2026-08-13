/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.control.validationlabel;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.base.validation.validator.Validator;
import ch.nolix.baseapi.errorcontrol.invalidargumentexception.UnrepresentingArgumentException;
import ch.nolix.baseapi.generalcatalog.variablenamecatalog.LowerCaseVariableNameCatalog;
import ch.nolix.system.control.button.Button;
import ch.nolix.system.control.label.Label;
import ch.nolix.system.control.textbox.Textbox;
import ch.nolix.system.control.verticalstack.VerticalStack;
import ch.nolix.system.webapplication.main.WebClientSession;

/**
 * @author Silvan Wyss
 */
final class ValidationLabelTutorial {
  private ValidationLabelTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("ValidationLabel tutorial", Session.class);

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
    private final Textbox numberTextbox = new Textbox();

    @Override
    protected void initialize() {
      // add a ValidationLabel to the GUI of the current Session
      getStoredGui().pushLayerWithRootControl(
        new VerticalStack()
          .addControls(
            new Label().setText("Enter a positive number:"),
            numberTextbox,
            new ValidationLabel(),
            new Button().setText("Ok").setLeftMouseButtonPressAction(this::enterPositiveNumber)));
    }

    private void enterPositiveNumber() {
      // get the input of the numberTextBox
      final var input = numberTextbox.getText();

      try {
        // parse the input to a number
        final var number = Integer.parseInt(input);

        // assert that the number is positive
        Validator.assertThat(number).thatIsNamed(LowerCaseVariableNameCatalog.NUMBER).isPositive();
      } catch (final NumberFormatException _) {
        // create and throws an UnrepresentingArgumentException for the input
        throw //
        UnrepresentingArgumentException.forArgumentAndArgumentNameAndType(
          input,
          LowerCaseVariableNameCatalog.INPUT,
          Integer.class);
      }
    }
  }
}
