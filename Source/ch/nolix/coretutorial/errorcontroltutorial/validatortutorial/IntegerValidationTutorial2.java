package ch.nolix.coretutorial.errorcontroltutorial.validatortutorial;

import ch.nolix.core.errorcontrol.validator.Validator;

public final class IntegerValidationTutorial2 {

  private IntegerValidationTutorial2() {
  }

  public static void main(String[] args) {

    //Lets the Validator assert that 5 is positive, what will not result in any complain.
    Validator.assertThat(5).thatIsNamed("size").isPositive();

    //Lets the Validator assert that 5 is negative, what will result in a NegativeArgumentException.
    Validator.assertThat(5).thatIsNamed("size").isNegative();
  }
}
