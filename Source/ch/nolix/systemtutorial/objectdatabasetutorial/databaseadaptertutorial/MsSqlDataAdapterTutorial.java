package ch.nolix.systemtutorial.objectdatabasetutorial.databaseadaptertutorial;

import ch.nolix.core.errorcontrol.logging.GlobalLogger;
import ch.nolix.system.objectdata.data.Entity;
import ch.nolix.system.objectdata.data.Value;
import ch.nolix.system.objectdata.dataadapter.MsSqlDataAdapterBuilder;
import ch.nolix.system.objectdata.schema.Schema;

public final class MsSqlDataAdapterTutorial {

  private MsSqlDataAdapterTutorial() {
  }

  public static void main(String[] args) {

    final var schema = Schema.withEntityType(Person.class);

    final var msSqlDataAdapter = MsSqlDataAdapterBuilder.createMsSqlDataAdapter()
      .toLocalAddress()
      .andMsSqlPort()
      .andDatabase("TestDB")
      .withLoginName("sa")
      .andLoginPassword("sa1234")
      .andSchema(schema);

    final var donaldDuck = new Person();
    donaldDuck.firstName.setValue("Donald");
    donaldDuck.lastName.setValue("Duck");
    msSqlDataAdapter.insertEntity(donaldDuck);

    msSqlDataAdapter.saveChanges();

    final var loadedDonaldDuck = msSqlDataAdapter.getStoredTableByEntityType(Person.class)
      .getStoredEntityById(donaldDuck.getId());

    GlobalLogger.logInfo(loadedDonaldDuck.toString());
  }

  private static final class Person extends Entity {

    private final Value<String> firstName = Value.withValueType(String.class);
    private final Value<String> lastName = Value.withValueType(String.class);

    @Override
    public String toString() {
      return (firstName.getStoredValue() + " " + lastName.getStoredValue());
    }
  }
}
