package ch.nolix.core.programcontrol.flowcontrol;

import ch.nolix.core.container.arraylist.ArrayList;
import ch.nolix.core.errorcontrol.logging.Logger;

final class AsSoonAsNoMoreMethodTutorial {

  private AsSoonAsNoMoreMethodTutorial() {
  }

  public static void main(String[] args) {

    final var cats = ArrayList.withElement("Garfield", "Simba", "Smokey");

    FlowController
      .asSoonAsNoMore(cats::containsAny)
      .runInBackground(() -> Logger.logInfo("Couch is not scratched anymore!"));

    cats.clear();
  }
}
