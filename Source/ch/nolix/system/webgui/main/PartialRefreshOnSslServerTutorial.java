/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.system.application.main.SslServer;

/**
 * @author Silvan Wyss
 */
final class PartialRefreshOnSslServerTutorial {
  private PartialRefreshOnSslServerTutorial() {
  }

  public static void main(String[] args) {
    //Creates a SslServer.
    final var sslServer = SslServer.forHttpsPortAndDomainAndSSLCertificateFromNolixConfiguration();

    //Adds a default Application to the SslServer.
    sslServer.addApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Partial refresh tutorial",
      PartialRefreshTutorial.Session.class);
  }
}
