package ch.nolix.template.webgui.dialog;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.element.style.DeepSelectingStyle;
import ch.nolix.system.element.style.Style;
import ch.nolix.system.webgui.atomiccontrol.button.Button;
import ch.nolix.system.webgui.main.Layer;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.buttonapi.ButtonRole;
import ch.nolix.systemapi.webguiapi.basecontainerapi.ContainerRole;

public final class YesNoDialogBuilderTutorial {

  private YesNoDialogBuilderTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = Server.forHttpPort();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "YesNoDialogBuilder tutorial",
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

  public static final class MainSession //NOSONAR: A single-file-tutorial is allowed to have a long static class.
  extends WebClientSession<Object> {

    @Override
    protected void initialize() {

      //Adds a Button, that can open a YesNoDialog, to the GUI of the current
      //MainSession.
      getStoredGui().pushLayerWithRootControl(
        new Button()
          .setText("Click me")
          .setLeftMouseButtonPressAction(
            () -> getStoredGui()
              .pushLayer(
                new YesNoDialogBuilder()
                  .setYesNoQuestion("Do you want to redirect to nolix.ch?")
                  .setConfirmAction(() -> getStoredGui().onFrontEnd().openNewTabWithUrl("nolix.ch"))
                  .build())));

      //Creates and adds a Style to the GUI of the current MainSession.
      getStoredGui().setStyle(
        new Style()
          .withSubStyle(
            new DeepSelectingStyle()
              .withSelectorType(Button.class)
              .withAttachingAttribute(
                "MinWidth(200)",
                "CursorIcon(Hand)",
                "BaseBackground(Color(SkyBlue))",
                "HoverBackground(Color(Blue))",
                "BaseTextSize(30)"),
            new DeepSelectingStyle()
              .withSelectorType(Layer.class)
              .withAttachingAttribute("Background(Color(White))"),
            new DeepSelectingStyle()
              .withSelectorRole(ContainerRole.DIALOG_CONTAINER)
              .withAttachingAttribute(
                "BaseBackground(Color(Lavender))")
              .withSubStyle(
                new DeepSelectingStyle()
                  .withSelectorRole(ButtonRole.CONFIRM_BUTTON)
                  .withAttachingAttribute("BaseBackground(Color(LightGreen))",
                    "HoverBackground(Color(Green))"),
                new DeepSelectingStyle()
                  .withSelectorRole(ButtonRole.CANCEL_BUTTON)
                  .withAttachingAttribute(
                    "CursorIcon(Hand)",
                    "BaseBackground(Color(Salmon))",
                    "HoverBackground(Color(Red))"))));
    }
  }
}
