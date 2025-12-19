package ch.nolix.core.programcontrol.flowcontrol;

import ch.nolix.core.container.arraylist.ArrayList;
import ch.nolix.core.errorcontrol.logging.Logger;

/**
 * @author Silvan Wyss
 */
final class AsSoonAsNoMoreMethodTutorial {
  private AsSoonAsNoMoreMethodTutorial() {
  }

  public static void main(String[] args) {
    final var cats = ArrayList.withElements("Garfield", "Simba", "Smokey");

    FlowController
      .asSoonAsNoMore(cats::containsAny)
      .runInBackground(() -> Logger.logInfo("The couch is not scratched anymore!"));

    cats.clear();
  }
}
