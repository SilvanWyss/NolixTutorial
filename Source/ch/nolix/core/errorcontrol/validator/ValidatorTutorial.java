package ch.nolix.core.errorcontrol.validator;

import ch.nolix.core.errorcontrol.logging.Logger;
import ch.nolix.core.programcontrol.flowcontrol.FlowController;

final class ValidatorTutorial {

  private ValidatorTutorial() {
  }

  public static void main(String[] args) {

    printAmount("apple", 2);
    printAmount("banana", 5);

    FlowController.runInEnclosedMode(() -> printAmount(" ", 20));
    FlowController.runInEnclosedMode(() -> printAmount("bred", -1));

    printAmount("cake", 10);
    printAmount("steak", 15);
  }

  private static void printAmount(final String productName, final int amount) {

    //Asserts that the given productName is not null or blank.
    Validator.assertThat(productName).thatIsNamed("product name").isNotBlank();

    //Asserts that the given amount is not negative.
    Validator.assertThat(amount).thatIsNamed("amount").isNotNegative();

    //Logs the given productName and the given amount.
    Logger.logInfo(productName + ": " + amount + " pieces");
  }
}
