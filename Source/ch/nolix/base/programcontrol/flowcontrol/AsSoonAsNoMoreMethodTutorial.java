/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.base.programcontrol.flowcontrol;

import ch.nolix.base.container.arraylist.ArrayList;
import ch.nolix.base.errorcontrol.logging.Logger;

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
