package ch.nolix.coretutorial.programcontroltutorial.sequencertutorial;

import ch.nolix.core.container.arraylist.ArrayList;
import ch.nolix.core.errorcontrol.logging.GlobalLogger;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;

public final class AsSoonAsNoMoreMethodTutorial {

  private AsSoonAsNoMoreMethodTutorial() {
  }

  public static void main(String[] args) {

    final var cats = ArrayList.withElement("Garfield", "Simba", "Smokey");

    GlobalSequencer
      .asSoonAsNoMore(cats::containsAny)
      .runInBackground(() -> GlobalLogger.logInfo("Couch is not scratched anymore!"));

    cats.clear();
  }
}
