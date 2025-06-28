package ch.nolix.system.webgui.main;

import ch.nolix.system.application.main.SslServer;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.button.Button;
import ch.nolix.system.webgui.atomiccontrol.label.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.guiapi.contentalignmentproperty.HorizontalContentAlignment;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.labelapi.ILabel;
import ch.nolix.systemapi.webguiapi.mainapi.ControlState;

final class ButtonOnSslServerTutorial {

  public static void main(String[] args) {

    //Creates a SslServer.
    final var sslServer = SslServer.forHttpsPortAndDomainAndSSLCertificateFromNolixConfiguration();

    //Adds a default Application to the SslServer.
    sslServer.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Button tutorial", Session.class);
  }

  private static final class Session extends WebClientSession<Object> {

    private int count;

    private final ILabel countLabel = new Label().setText(String.valueOf(count));

    @Override
    protected void initialize() {

      //Creates incrementButton.
      final var incrementButton = //
      new Button().setText("Increment").setLeftMouseButtonPressAction(this::incrementCountAndUpdateCountLabel);

      //Configures the style of the countLabel.
      countLabel.getStoredStyle().setTextSizeForState(ControlState.BASE, 100);

      //Creates a VerticalStack with the countLabel and the incrementButton.
      final var verticalStack = new VerticalStack().addControl(countLabel, incrementButton);

      //Configures the style of the verticalStack.
      verticalStack.setContentAlignment(HorizontalContentAlignment.CENTER);

      //Adds the VerticalStack to the GUI of the current Session.
      getStoredGui().pushLayerWithRootControl(verticalStack);
    }

    private void incrementCountAndUpdateCountLabel() {

      //Increments the count.
      count++;

      //Updates the countLabel.
      countLabel.setText(String.valueOf(count));
    }
  }

  private ButtonOnSslServerTutorial() {
  }
}
