package ch.nolix.systemtutorial.webguitutorial.maintutorial;

import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.system.application.main.SslServer;
import ch.nolix.systemtutorial.webguitutorial.maintutorial.PartialRefreshTutorial.MainSession;

public class SslPartialRefreshTutorial {

  public static void main(String[] args) {

    //Creates a SslServer.
    final var sslServer = SslServer.forHttpsPortAndDomainAndSSLCertificateFromNolixConfiguration();

    //Adds a default Application to the SslServer.
    sslServer.addDefaultApplicationWithNameAndInitialSessionClassAndContext(
      "Ssl partial refresh tutorial",
      MainSession.class,
      new VoidObject());
  }
}
