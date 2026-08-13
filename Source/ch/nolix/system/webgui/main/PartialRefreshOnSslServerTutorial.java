/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.webgui.main;

import ch.nolix.base.net.clientserver.SslServer;

/**
 * @author Silvan Wyss
 */
final class PartialRefreshOnSslServerTutorial {
  private PartialRefreshOnSslServerTutorial() {
  }

  public static void main() {
    // create a SslServer
    final var sslServer = SslServer.forHttpsPortAndDomainAndSSLCertificateFromNolixConfiguration();

    // add a default Application to the SslServer
    sslServer.addApplicationWithNameAndInitialSessionClassAndVoidContext(
      "Partial refresh tutorial",
      PartialRefreshTutorial.Session.class);
  }
}
