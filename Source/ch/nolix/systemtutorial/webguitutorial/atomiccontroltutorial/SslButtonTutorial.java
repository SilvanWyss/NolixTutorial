package ch.nolix.systemtutorial.webguitutorial.atomiccontroltutorial;

import ch.nolix.system.application.main.SslServer;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Button;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.ILabel;

public final class SslButtonTutorial {

  private SslButtonTutorial() {
  }

  public static void main(String[] args) {

    //Creates a SslServer.
    final var sslServer = SslServer.forHttpsPortAndDomainAndSSLCertificateFromNolixConfiguration();

    //Adds a default Application to the SslServer.
    sslServer.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Button tutorial", MainSession.class);
  }

  private static final class MainSession extends WebClientSession<Object> {

    private int count;

    private final ILabel countLabel = new Label().setText(String.valueOf(count));

    @Override
    protected void initialize() {

      //Creates rootControl.
      final var rootControl = //
      new VerticalStack()
        .addControl(
          countLabel,
          new Button().setText("Increment").setLeftMouseButtonPressAction(this::incrementCountAndUpdateCountLabel));

      //Adds the rootControl to the GUI of the current MainSession.
      getStoredGui().pushLayerWithRootControl(rootControl);
    }

    private void incrementCountAndUpdateCountLabel() {

      //Increments the count.
      count++;

      //Updates the countLabel.
      countLabel.setText(String.valueOf(count));
    }
  }
}
