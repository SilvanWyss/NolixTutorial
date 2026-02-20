/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.base.errorcontrol.validator;

/**
 * @author Silvan Wyss
 */
final class StringValidationTutorial {
  private StringValidationTutorial() {
  }

  public static void main(String[] args) {
    /*
     * Lets the Validator assert that the String 'Hello World!' is not null and not
     * empty, what will not result in any complain.
     */
    Validator.assertThat("Hello World!").isNotEmpty();

    /*
     * Lets the Validator assert that he String 'Hello World!' is not longer than 10
     * characters, what will result in an InvalidArgumentException.
     */
    Validator.assertThat("Hello World!").isNotLongerThan(10);
  }
}
