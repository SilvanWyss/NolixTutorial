package ch.nolix.core.errorcontrol.validator;

public final class IntegerValidationTutorial1 {

  private IntegerValidationTutorial1() {
  }

  public static void main(String[] args) {

    //Lets the Validator assert that 5 is positive, what will not result in any complain.
    Validator.assertThat(5).isPositive();

    //Lets the Validator assert that 5 is negative, what will result in a NegativeArgumentException.
    Validator.assertThat(5).isNegative();
  }
}
