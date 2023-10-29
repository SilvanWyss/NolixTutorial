package ch.nolix.coretutorial.buildertutorial.maintutorial;

import ch.nolix.core.builder.main.ArgumentCapturer;
import ch.nolix.core.errorcontrol.logger.GlobalLogger;

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
  WithNameCapturer<WithAgeInYearsCapturer<WithWeightInKilogramCapturer<Pet>>> {

    public PetBuilder() {

      super(new WithAgeInYearsCapturer<>(new WithWeightInKilogramCapturer<>(null)));

      setBuilder(this::buildPet);
    }

    private Pet buildPet() {
      return new Pet(getName(), next().getAgeInYears(), next().next().getWeightInKilogram());
    }
  }

  private static class WithNameCapturer<N> extends ArgumentCapturer<String, N> {

    public WithNameCapturer(final N nextArgumentCapturer) {
      super(nextArgumentCapturer);
    }

    public final String getName() {
      return getStoredArgument();
    }

    public final N withName(final String name) {
      return setArgumentAndGetNext(name);
    }
  }

  private static class WithAgeInYearsCapturer<N> extends ArgumentCapturer<Integer, N> {

    public WithAgeInYearsCapturer(final N nextArgumentCapturer) {
      super(nextArgumentCapturer);
    }

    public final int getAgeInYears() {
      return getStoredArgument();
    }

    public final N withAgeInYears(final int ageInYears) {
      return setArgumentAndGetNext(ageInYears);
    }
  }

  private static class WithWeightInKilogramCapturer<N> extends ArgumentCapturer<Integer, N> {

    public WithWeightInKilogramCapturer(final N nextArgumentCapturer) {
      super(nextArgumentCapturer);
    }

    public final int getWeightInKilogram() {
      return getStoredArgument();
    }

    public final N withWeightInKilogram(final int ageInYears) {
      return setArgumentAndGetNext(ageInYears);
    }
  }
}
