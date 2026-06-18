/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.tech.math.bigdecimalmath;

import java.util.function.IntConsumer;

import ch.nolix.base.errorcontrol.logging.Logger;

/**
 * @author Silvan Wyss
 */
final class ComplexSequenceTutorial {
  private ComplexSequenceTutorial() {
  }

  public static void main() {
    final var complexSequence = //
    ComplexSequenceDefinedBy1Predecessor.withFirstValueAndNextValueFunction(
      ComplexNumber.withRealComponentAndImaginaryComponent(0.0, 0.0),
      p -> p.getPower2().getSum(ComplexNumber.withRealComponentAndImaginaryComponent(0.0, 1.0)));

    final IntConsumer printFunction = //
    (int i) -> Logger.logInfo("a(" + i + ") = " + complexSequence.getValueAtOneBasedIndex(i));

    printFunction.accept(1);
    printFunction.accept(2);
    printFunction.accept(3);
    printFunction.accept(4);
    printFunction.accept(5);
    printFunction.accept(10);
    printFunction.accept(100);
    printFunction.accept(1000);
  }
}
