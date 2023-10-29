package ch.nolix.techtutorial.mathtutorial.bigdecimalmathtutorial;

import java.util.function.IntConsumer;

import ch.nolix.core.errorcontrol.logger.GlobalLogger;
import ch.nolix.tech.math.bigdecimalmath.ComplexNumber;
import ch.nolix.tech.math.bigdecimalmath.ComplexSequenceDefinedBy1Predecessor;

public final class ComplexSequenceTutorial {

  private ComplexSequenceTutorial() {
  }

  public static void main(String[] args) {

    final var complexSequence = new ComplexSequenceDefinedBy1Predecessor(
      new ComplexNumber(0.0, 0.0),
      p -> p.getPower2().getSum(new ComplexNumber(0.0, 1.0)));

    final IntConsumer printFunction = (int i) -> GlobalLogger
      .logInfo("a(" + i + ") = " + complexSequence.getValueAt1BasedIndex(i));

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
