package ch.nolix.templatetutorial.webguitutorial.dialogtutorial;

import ch.nolix.core.environment.localcomputer.ShellProvider;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;
import ch.nolix.system.application.main.Server;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.element.stylebuilder.DeepSelectingStyleBuilder;
import ch.nolix.system.element.stylebuilder.StyleBuilder;
import ch.nolix.system.webgui.atomiccontrol.Button;
import ch.nolix.system.webgui.main.Layer;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.ButtonRole;
import ch.nolix.systemapi.webguiapi.basecontainerapi.ContainerRole;
import ch.nolix.template.webgui.dialog.YesNoDialogBuilder;

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
    GlobalSequencer
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
        new StyleBuilder()
          .addSubStyle(
            new DeepSelectingStyleBuilder()
              .setSelectorType(Button.class)
              .addAttachingAttribute(
                "MinWidth(200)",
                "CursorIcon(Hand)",
                "BaseBackground(Color(SkyBlue))",
                "HoverBackground(Color(Blue))",
                "BaseTextSize(30)")
              .build(),
            new DeepSelectingStyleBuilder()
              .setSelectorType(Layer.class)
              .addAttachingAttribute("Background(Color(White))")
              .build(),
            new DeepSelectingStyleBuilder()
              .addSelectorRole(ContainerRole.DIALOG_CONTAINER)
              .addAttachingAttribute(
                "BaseBackground(Color(Lavender))")
              .addSubStyle(
                new DeepSelectingStyleBuilder()
                  .addSelectorRole(ButtonRole.CONFIRM_BUTTON)
                  .addAttachingAttribute("BaseBackground(Color(LightGreen))",
                    "HoverBackground(Color(Green))")
                  .build(),
                new DeepSelectingStyleBuilder()
                  .addSelectorRole(ButtonRole.CANCEL_BUTTON)
                  .addAttachingAttribute(
                    "CursorIcon(Hand)",
                    "BaseBackground(Color(Salmon))",
                    "HoverBackground(Color(Red))")
                  .build())
              .build())
          .build());
    }
  }
}
