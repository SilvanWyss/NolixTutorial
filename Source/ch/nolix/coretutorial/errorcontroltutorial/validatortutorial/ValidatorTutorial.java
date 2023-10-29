package ch.nolix.coretutorial.errorcontroltutorial.validatortutorial;

//own imports
import ch.nolix.core.errorcontrol.invalidargumentexception.ArgumentIsNullException;
import ch.nolix.core.errorcontrol.invalidargumentexception.EmptyArgumentException;
import ch.nolix.core.errorcontrol.invalidargumentexception.NegativeArgumentException;
import ch.nolix.core.errorcontrol.logger.GlobalLogger;
import ch.nolix.core.errorcontrol.validator.GlobalValidator;

/**
 * The {@link ValidatorTutorial} is a tutorial for the {@link GlobalValidator}.
 * Of the {@link ValidatorTutorial} an instance cannot be created.
 * 
 * @author Silvan Wys
 * @date 2017-05-14
 */
public final class ValidatorTutorial {

  /**
   * Prevents that an instance of the {@link ValidatorTutorial} can be created.
   */
  private ValidatorTutorial() {
  }

  public static void main(String[] args) {
    printAmount("apple", 5);
    printAmount("banana", 10);
    printAmount("cake", 2);
  }

  /**
   * Prints out to the console the amount of the product with given productName.
   * 
   * @param productName
   * @param amount
   * @throws ArgumentIsNullException   if the given productName is null.
   * @throws EmptyArgumentException    if the given productName is empty.
   * @throws NegativeArgumentException if the given amount is negative.
   */
  private static void printAmount(final String productName, final int amount) {

    //Asserts that the given productName is not null or empty.
    GlobalValidator.assertThat(productName).thatIsNamed("product name").isNotEmpty();

    //Asserts that the given amount is not negative.
    GlobalValidator.assertThat(amount).thatIsNamed("amount").isNotNegative();

    GlobalLogger.logInfo(productName + ": " + amount + " pieces");
  }
}
