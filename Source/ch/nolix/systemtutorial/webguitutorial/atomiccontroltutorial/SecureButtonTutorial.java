package ch.nolix.systemtutorial.webguitutorial.atomiccontroltutorial;

import ch.nolix.system.application.main.SecureServer;
import ch.nolix.system.application.webapplication.WebClientSession;
import ch.nolix.system.webgui.atomiccontrol.Button;
import ch.nolix.system.webgui.atomiccontrol.Label;
import ch.nolix.system.webgui.linearcontainer.VerticalStack;
import ch.nolix.systemapi.webguiapi.atomiccontrolapi.ILabel;

/**
 * The {@link SecureButtonTutorial} does the same as the {@link ButtonTutorial},
 * but it is configured for a productive environment. That means there is
 * required a {@link SecureServer}, a domain and a valid SSL certificate. A
 * {@link SecureServer} is a secure web-socket server.
 * 
 * @author Silvan Wyss
 * @date 2023-05-07
 */
public final class SecureButtonTutorial {

  private SecureButtonTutorial() {
  }

  public static void main(String[] args) {

    //Creates a Server.
    final var server = SecureServer.forHttpsPortAndDomainAndSSLCertificateFromNolixConfiguration();

    //Adds a default Application to the Server.
    server.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext("Button tutorial", MainSession.class);
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
