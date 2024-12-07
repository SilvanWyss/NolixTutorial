package ch.nolix.coretutorial.errorcontroltutorial.validatortutorial;

import ch.nolix.core.errorcontrol.validator.GlobalValidator;

public final class IntegerValidationTutorial2 {

  private IntegerValidationTutorial2() {
  }

  public static void main(String[] args) {

    //Lets the GlobalValidator assert that 5 is positive, what will not result in any complain.
    GlobalValidator.assertThat(5).thatIsNamed("size").isPositive();

    //Lets the GlobalValidator assert that 5 is negative, what will result in a NegativeArgumentException.
    GlobalValidator.assertThat(5).thatIsNamed("size").isNegative();
  }
}
