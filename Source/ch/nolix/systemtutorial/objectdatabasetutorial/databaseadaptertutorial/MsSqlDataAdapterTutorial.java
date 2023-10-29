package ch.nolix.systemtutorial.objectdatabasetutorial.databaseadaptertutorial;

import ch.nolix.core.errorcontrol.logger.GlobalLogger;
import ch.nolix.system.objectdatabase.dataadapter.MsSqlDataAdapter;
import ch.nolix.system.objectdatabase.database.Entity;
import ch.nolix.system.objectdatabase.database.Value;
import ch.nolix.system.objectdatabase.schema.Schema;

public final class MsSqlDataAdapterTutorial {

  private MsSqlDataAdapterTutorial() {
  }

  public static void main(String[] args) {

    final var schema = Schema.withEntityType(Person.class);

    final var msSqlDataAdapter = MsSqlDataAdapter
      .toLocalHost()
      .andDefaultPort()
      .toDatabase("TestDB")
      .usingLoginName("sa")
      .andLoginPassword("sa1234")
      .andSchema(schema);

    final var donaldDuck = new Person();
    donaldDuck.firstName.setValue("Donald");
    donaldDuck.lastName.setValue("Duck");
    msSqlDataAdapter.insert(donaldDuck);

    msSqlDataAdapter.saveChanges();

    final var loadedDonaldDuck = msSqlDataAdapter.getStoredTableByEntityType(Person.class)
      .getStoredEntityById(donaldDuck.getId());

    GlobalLogger.logInfo(loadedDonaldDuck.toString());
  }

  private static final class Person extends Entity {

    private final Value<String> firstName = new Value<>();
    private final Value<String> lastName = new Value<>();

    @Override
    public String toString() {
      return (firstName.getStoredValue() + " " + lastName.getStoredValue());
    }
  }
}
