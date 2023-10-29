package ch.nolix.systemtutorial.webguitutorial.maintutorial;

import ch.nolix.core.programatom.voidobject.VoidObject;
import ch.nolix.system.application.main.SecureServer;
import ch.nolix.systemtutorial.webguitutorial.maintutorial.PartialRefreshTutorial.MainSession;

public class SecurePartialRefreshTutorial {

  public static void main(String[] args) {

    //Creates a SecureServer.
    final var secureServer = SecureServer.forHttpsPortAndDomainAndSSLCertificateFromNolixConfiguration();

    //Adds a default Application to the SecureServer.
    secureServer.addDefaultApplicationWithNameAndInitialSessionClassAndContext(
      "Secure partial refresh tutorial",
      MainSession.class,
      new VoidObject());
  }
}
