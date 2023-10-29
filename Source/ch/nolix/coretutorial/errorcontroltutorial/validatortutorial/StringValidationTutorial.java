package ch.nolix.coretutorial.errorcontroltutorial.validatortutorial;

//own imports
import ch.nolix.core.errorcontrol.validator.GlobalValidator;

/**
 * The {@link StringValidationTutorial} is a tutorial for the
 * {@link GlobalValidator}.
 * 
 * @author Silvan
 * @date 2017-03-05
 */
public final class StringValidationTutorial {

  /**
   * Prevents that an instance of the {@link GlobalValidator} can be created.
   */
  private StringValidationTutorial() {
  }

  /**
   * Lets the {@link GlobalValidator} validate the maximum length of a
   * {@link String}.
   * 
   * @param args
   */
  public static void main(String[] args) {

    //Lets the validator validate that the string 'Hello World!' is not null and
    //not empty.
    GlobalValidator.assertThat("Hello World!").isNotEmpty();

    //Lets the validator validate that he string 'Hello World!' has the max length
    //20.
    GlobalValidator.assertThat("Hello World!").isNotLongerThan(12);
  }
}
