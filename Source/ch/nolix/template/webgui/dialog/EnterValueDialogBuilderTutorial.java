package ch.nolix.template.webgui.dialog;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.errorcontrol.validator.Validator;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.coreapi.misc.variable.LowerCaseVariableCatalog;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.graphic.color.X11ColorCatalog;
import ch.nolix.system.webapplication.main.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.button.Button;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webgui.atomiccontrol.labelapi.ILabel;

final class EnterValueDialogBuilderTutorial {

  private EnterValueDialogBuilderTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "EnterValueDialogBuilder tutorial",
      Session.class);

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

    private final ILabel nameLabel = new Label().setText("?");

    @Override
    protected void initialize() {

      //Adds the nameLabel and a Button, that leads to a dialog to enter a name, to the GUI of the current Session.
      getStoredGui()
        .pushLayerWithRootControl(
          new VerticalStack()
            .addControl(
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

      //Asserts that the given name is not shorter than 4 characters.
      Validator.assertThat(name).thatIsNamed(LowerCaseVariableCatalog.NAME).isNotShorterThan(4);

      //Sets the given name to the nameLabel.
      nameLabel.setText(name);
    }
  }
}
