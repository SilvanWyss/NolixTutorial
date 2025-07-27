package ch.nolix.core.argumentcaptor.base;

import ch.nolix.core.errorcontrol.logging.Logger;

final class ArgumentCaptorTutorial {

  private ArgumentCaptorTutorial() {
  }

  public static void main(String[] args) {

    //Builds a Pet.
    final var garfield = Pet.build().withName("Garfield").withAgeInYears(10).withWeightInKilogram(20);

    //Logs the String representation of the Pet.
    Logger.logInfo(garfield.toString());
  }

  private static record Pet(String name, int ageInYears, int weightInKilogram) {

    public static PetBuilder build() {
      return new PetBuilder();
    }
  }

  private static final class PetBuilder extends WithNameCaptor<WithAgeInYearsCaptor<WithWeightInKilogramCaptor<Pet>>> {

    public PetBuilder() {

      super(new WithAgeInYearsCaptor<>(new WithWeightInKilogramCaptor<>()));

      setBuilder(this::build);
    }

    private Pet build() {
      return new Pet(getName(), nxtArgCpt().getAgeInYears(), nxtArgCpt().nxtArgCpt().getWeightInKilogram());
    }
  }

  private static class WithNameCaptor<N> extends ArgumentCaptor<String, N> {

    public WithNameCaptor(final N nextArgumentCaptor) {
      super(nextArgumentCaptor);
    }

    public final String getName() {
      return getStoredArgument();
    }

    public final N withName(final String name) {
      return setArgumentAndGetNext(name);
    }
  }

  private static class WithAgeInYearsCaptor<N> extends ArgumentCaptor<Integer, N> {

    public WithAgeInYearsCaptor(final N nextArgumentCaptor) {
      super(nextArgumentCaptor);
    }

    public final int getAgeInYears() {
      return getStoredArgument();
    }

    public final N withAgeInYears(final int ageInYears) {
      return setArgumentAndGetNext(ageInYears);
    }
  }

  private static class WithWeightInKilogramCaptor<N> extends ArgumentCaptor<Integer, N> {

    public final int getWeightInKilogram() {
      return getStoredArgument();
    }

    public final N withWeightInKilogram(final int ageInYears) {
      return setArgumentAndGetNext(ageInYears);
    }
  }
}
