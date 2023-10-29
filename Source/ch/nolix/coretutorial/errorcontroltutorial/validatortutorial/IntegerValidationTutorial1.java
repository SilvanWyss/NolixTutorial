package ch.nolix.coretutorial.errorcontroltutorial.validatortutorial;

import ch.nolix.core.errorcontrol.validator.GlobalValidator;

/**
 * The {@link GlobalValidator} is a tutorial for how the {@link GlobalValidator}
 * validates an integer.
 * 
 * @author Silvan Wyss
 * @date 2016-12-01
 */
public final class IntegerValidationTutorial1 {

  /**
   * Prevents that an instance of the {@link IntegerValidationTutorial1} can be
   * created.
   */
  private IntegerValidationTutorial1() {
  }

  /**
   * Lets the {@link GlobalValidator} once assert that 5 is positive and once
   * assert that 5 is negative.
   * 
   * @param args
   */
  public static void main(String[] args) {

    //Lets the GlobalValidator assert that 5 is positive, what will not result in
    //any complain.
    GlobalValidator.assertThat(5).isPositive();

    //Lets the GlobalValidator assert that 5 is negative, what will result in a
    //NegativeArgumentException.
    GlobalValidator.assertThat(5).isNegative();
  }
}
