package ch.nolix.system.webgui.main;

import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.system.application.main.SslServer;
import ch.nolix.system.webgui.main.PartialRefreshTutorial.Session;

public class SslPartialRefreshTutorial {

  public static void main(String[] args) {

    //Creates a SslServer.
    final var sslServer = SslServer.forHttpsPortAndDomainAndSSLCertificateFromNolixConfiguration();

    //Adds a default Application to the SslServer.
    sslServer.addDefaultApplicationWithNameAndInitialSessionClassAndContext(
      "Ssl partial refresh tutorial",
      Session.class,
      new VoidObject());
  }
}
