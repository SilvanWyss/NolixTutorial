package ch.nolix.core.errorcontrol.validator;

final class IntegerValidationTutorial {

  private IntegerValidationTutorial() {
  }

  public static void main(String[] args) {

    //Lets the Validator assert that 5 is positive, what will not result in any complain.
    Validator.assertThat(5).isPositive();

    //Lets the Validator assert that 5 is negative, what will result in a NegativeArgumentException.
    Validator.assertThat(5).isNegative();
  }
}
