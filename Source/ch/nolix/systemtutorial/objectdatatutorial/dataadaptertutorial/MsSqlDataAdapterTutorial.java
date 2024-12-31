package ch.nolix.systemtutorial.objectdatatutorial.dataadaptertutorial;

import ch.nolix.core.errorcontrol.logging.GlobalLogger;
import ch.nolix.system.objectdata.adapter.MsSqlDataAdapterBuilder;
import ch.nolix.system.objectdata.model.Entity;
import ch.nolix.system.objectdata.model.Value;
import ch.nolix.system.objectdata.schemamodel.Schema;

public final class MsSqlDataAdapterTutorial {

  private MsSqlDataAdapterTutorial() {
  }

  public static void main(String[] args) {

    //Creates schema.
    final var schema = Schema.withEntityType(Person.class);

    //Creates msSqlDataAdapter.
    final var msSqlDataAdapter = //
    MsSqlDataAdapterBuilder
      .createMsSqlDataAdapter()
      .toLocalAddress()
      .andMsSqlPort()
      .andDatabase("PersonDB")
      .withLoginName("mssqluser")
      .andLoginPassword("mssql1234")
      .andSchema(schema);

    //Creates a first Entity.
    final var donaldDuck = new Person();
    donaldDuck.firstName.setValue("Donald");
    donaldDuck.lastName.setValue("Duck");

    //Creates a second Entity.
    final var daisyDuck = new Person();
    daisyDuck.firstName.setValue("Daisy");
    daisyDuck.lastName.setValue("Duck");

    //Inserts the created Entities into the msSqlDataAdapter.
    msSqlDataAdapter.insertEntity(daisyDuck, donaldDuck);

    //Lets the msSqlDataAdapter save its changes.
    msSqlDataAdapter.saveChanges();

    //Lets the msSqlDataAdapter load the first Entity.
    final var loadedDonaldDuck = //
    msSqlDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(donaldDuck.getId());

    //Lets the msSqlDataAdapter load the second Entity.
    final var loadedDaisyDuck = //
    msSqlDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(daisyDuck.getId());

    //Logs the loaded Entities.
    GlobalLogger.logInfo(loadedDonaldDuck.toString());
    GlobalLogger.logInfo(loadedDaisyDuck.toString());
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
