package ch.nolix.coretutorial.buildertutorial.maintutorial;

import ch.nolix.core.argumentcaptor.base.ArgumentCaptor;
import ch.nolix.core.errorcontrol.logging.GlobalLogger;

public class BuilderTutorial {

  public static void main(String[] args) {

    final var garfield = Pet.build().withName("Garfield").withAgeInYears(10).withWeightInKilogram(20);

    GlobalLogger.logInfo(garfield.toString());
  }

  private static record Pet(String name, int ageInYears, int weightInKilogram) {

    public static PetBuilder build() {
      return new PetBuilder();
    }
  }

  private static class PetBuilder
  extends
  WithNameCaptor<WithAgeInYearsCaptor<WithWeightInKilogramCaptor<Pet>>> {

    public PetBuilder() {

      super(new WithAgeInYearsCaptor<>(new WithWeightInKilogramCaptor<>()));

      setBuilder(this::buildPet);
    }

    private Pet buildPet() {
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
