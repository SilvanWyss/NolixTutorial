/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.system.application.main.SslServer;
import ch.nolix.system.webgui.atomiccontrol.button.ButtonTutorial;

/**
 * @author Silvan Wyss
 */
final class ButtonOnSslServerTutorial {
  private ButtonOnSslServerTutorial() {
  }

  public static void main(String[] args) {
    //Creates a SslServer.
    final var sslServer = SslServer.forHttpsPortAndDomainAndSSLCertificateFromNolixConfiguration();

    //Adds a default Application to the SslServer.
    sslServer.addDefaultApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Button tutorial",
      ButtonTutorial.Session.class);
  }
}
