package ch.nolix.coretutorial.errorcontroltutorial.validatortutorial;

import ch.nolix.core.errorcontrol.logging.GlobalLogger;
import ch.nolix.core.errorcontrol.validator.GlobalValidator;
import ch.nolix.core.programcontrol.sequencer.GlobalSequencer;

public final class GlobalValidatorTutorial {

  private GlobalValidatorTutorial() {
  }

  public static void main(String[] args) {

    printAmount("apple", 2);
    printAmount("banana", 5);

    GlobalSequencer.runInEnclosedMode(() -> printAmount(" ", 20));
    GlobalSequencer.runInEnclosedMode(() -> printAmount("bred", -1));

    printAmount("cake", 10);
    printAmount("steak", 15);
  }

  private static void printAmount(final String productName, final int amount) {

    //Asserts that the given productName is not null or blank.
    GlobalValidator.assertThat(productName).thatIsNamed("product name").isNotBlank();

    //Asserts that the given amount is not negative.
    GlobalValidator.assertThat(amount).thatIsNamed("amount").isNotNegative();

    //Logs the given productName and the given amount.
    GlobalLogger.logInfo(productName + ": " + amount + " pieces");
  }
}
