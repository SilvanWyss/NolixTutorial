/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.template.webgui.dialog;

import ch.nolix.base.environment.localcomputer.ShellProvider;
import ch.nolix.base.net.clientserver.Server;
import ch.nolix.base.programcontrol.flowcontrol.FlowController;
import ch.nolix.base.validation.validator.Validator;
import ch.nolix.baseapi.generalcatalog.variablenamecatalog.LowerCaseVariableNameCatalog;
import ch.nolix.system.control.button.Button;
import ch.nolix.system.control.label.Label;
import ch.nolix.system.control.verticalstack.VerticalStack;
import ch.nolix.system.graphic.color.X11ColorCatalog;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.systemapi.control.label.ILabel;

/**
 * @author Silvan Wyss
 */
final class EnterValueDialogBuilderTutorial {
  private EnterValueDialogBuilderTutorial() {
  }

  public static void main() {
    // create a Server
    final var server = Server.forHttpPort();

    // add a default Application to the Server
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "EnterValueDialogBuilder tutorial",
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
    private final ILabel nameLabel = new Label().setText("?");

    @Override
    protected void initialize() {
      // add the nameLabel and a Button, that leads to a dialog to enter a name, to the GUI of the current Session
      getStoredGui()
        .pushLayerWithRootControl(
          new VerticalStack()
            .addControls(
              new Label().setText("You are:"),
              nameLabel,
              new Button()
                .setText("Edit your name")
                .setLeftMouseButtonPressAction(
                  () -> //
                  getStoredGui()
                    .pushLayer(
                      new EnterValueDialogBuilder()
                        .setInfoText("Enter your name")
                        .setOriginalValue(nameLabel.getText())
                        .setValueTaker(this::setNameInNameLabel)
                        .build()
                        .setBackgroundColor(X11ColorCatalog.WHITE)))));
    }

    private void setNameInNameLabel(final String name) {
      // assert that the given name is not shorter than 4 characters
      Validator.assertThat(name).thatIsNamed(LowerCaseVariableNameCatalog.NAME).isNotShorterThan(4);

      // set the given name to the nameLabel
      nameLabel.setText(name);
    }
  }
}
