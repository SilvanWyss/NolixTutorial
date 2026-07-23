/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.system.application.main.SslServer;
import ch.nolix.system.control.button.ButtonTutorial;

/**
 * @author Silvan Wyss
 */
final class ButtonOnSslServerTutorial {
  private ButtonOnSslServerTutorial() {
  }

  public static void main() {
    // create a SslServer
    final var sslServer = SslServer.forHttpsPortAndDomainAndSSLCertificateFromNolixConfiguration();

    // add a default Application to the SslServer
    sslServer.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Button tutorial",
      ButtonTutorial.Session.class);
  }
}
