package ch.nolix.systemtutorial.webguitutorial.atomiccontroltutorial;

import ch.nolix.system.application.main.SslServer;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Button;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.ILabel;

/**
 * The {@link SslButtonTutorial} does the same as the {@link ButtonTutorial},
 * but it is configured for a productive environment. That means there is
 * required a {@link SslServer}, a domain and a valid SSL certificate. A
 * {@link SslServer} is a secure web socket server.
 * 
 * @author Silvan Wyss
 * @date 2023-05-07
 */
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
      getStoredGui().pushLayerWithRootControl(
        new VerticalStack()
          .addControl(
            countLabel,
            new Button().setText("Increment").setLeftMouseButtonPressAction(this::incrementCount)));
    }

    private void incrementCount() {
      count++;
      countLabel.setText(String.valueOf(count));
      refresh();
    }
  }
}
