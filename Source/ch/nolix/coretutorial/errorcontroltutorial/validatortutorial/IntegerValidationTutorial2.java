package ch.nolix.coretutorial.errorcontroltutorial.validatortutorial;

//own imports
import ch.nolix.core.errorcontrol.validator.GlobalValidator;

/**
 * This class is a tutorial how the zeta validator validates an integer.
 * 
 * @author Silvan Wyss
 * @date 2016-12-01
 */
public final class IntegerValidationTutorial2 {

  /**
   * Prevents that an instance of the {@link IntegerValidationTutorial2} can be
   * created.
   */
  private IntegerValidationTutorial2() {
  }

  /**
   * Lets the zeta validator suppose once that 5 is positive and once suppose that
   * 5 is negative. This is done that the error message of a probable thrown
   * exception contains the variable name.
   * 
   * @param args
   */
  public static void main(String[] args) {

    //Supposes that 5 is positive, what makes that the zeta validator does not
    //complain.
    GlobalValidator.assertThat(5).thatIsNamed("size").isPositive();

    //Supposes that 5 is negative, what makes that the zeta validator throws a
    //NegativeArgumentException.
    GlobalValidator.assertThat(5).thatIsNamed("size").isNegative();
  }
}
