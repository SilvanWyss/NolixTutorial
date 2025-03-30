package ch.nolix.templatetutorial.webguitutorial.dialogtutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.errorcontrol.validator.Validator;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.coreapi.programatomapi.variableapi.LowerCaseVariableCatalog;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.button.Button;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.labelapi.ILabel;
import ch.nolix.template.webgui.dialog.EnterValueDialogBuilder;

public final class EnterValueDialogBuilderTutorial {

  private EnterValueDialogBuilderTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "EnterValueDialogBuilder tutorial",
      MainSession.class);

    //Starts a web browser that will connect to the Server.
    ShellProvider.startDefaultWebBrowserOpeningLoopBackAddress();

    //Closes the Server as soon as it does not have a client connected any more.
    FlowController
      .waitForSeconds(2)
      .andThen()
      .asSoonAsNoMore(server::hasClientConnected)
      .runInBackground(server::close);
  }

  private static final class MainSession //NOSONAR: A single-file-tutorial is allowed to have a long static class.
  extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      final var nameLabel = new Label().setText("Mister ?");

      getStoredGui().pushLayerWithRootControl(
        new VerticalStack()
          .addControl(
            nameLabel,
            new Button()
              .setText("Edit your name")
              .setLeftMouseButtonPressAction(
                () -> getStoredGui().pushLayer(
                  new EnterValueDialogBuilder()
                    .setInfoText("Enter your name")
                    .setOriginalValue(nameLabel.getText())
                    .setValueTaker(n -> setNewName(n, nameLabel))
                    .build()))));
    }

    private void setNewName(final String name, final ILabel nameLabel) {

      Validator
        .assertThat(name)
        .thatIsNamed(LowerCaseVariableCatalog.NAME)
        .isNotShorterThan(4);

      nameLabel.setText(name);
    }
  }
}
