package ch.nolix.system.objectdata.adapter;

import ch.nolix.core.errorcontrol.logging.Logger;
import ch.nolix.system.objectdata.model.Entity;
import ch.nolix.system.objectdata.model.EntityTypeSet;
import ch.nolix.system.objectdata.model.Value;

final class MsSqlDataAdapterTutorial {

  public static void main(String[] args) {

    //Creates schema.
    final var entityTypeSet = EntityTypeSet.withEntityType(Person.class);

    //Creates a MsSqlDataAdapter.
    final var msSqlDataAdapter = //
    MsSqlDataAdapterBuilder
      .createMsSqlDataAdapter()
      .toLocalAddress()
      .andMsSqlPort()
      .andDatabase("PersonDB")
      .withLoginName("mssqluser")
      .andLoginPassword("mssql1234")
      .andSchema(entityTypeSet);

    //Creates a first Entity.
    final var donaldDuck = new Person();
    donaldDuck.firstName.setValue("Donald");
    donaldDuck.lastName.setValue("Duck");

    //Creates a second Entity.
    final var daisyDuck = new Person();
    daisyDuck.firstName.setValue("Daisy");
    daisyDuck.lastName.setValue("Duck");

    //Inserts the created Entities into the MsSqlDataAdapter.
    msSqlDataAdapter.insertEntity(daisyDuck, donaldDuck);

    //Lets the MsSqlDataAdapter save its changes.
    msSqlDataAdapter.saveChanges();

    //Lets the MsSqlDataAdapter load the first Entity.
    final var loadedDonaldDuck = //
    msSqlDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(donaldDuck.getId());

    //Lets the MsSqlDataAdapter load the second Entity.
    final var loadedDaisyDuck = //
    msSqlDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(daisyDuck.getId());

    //Logs the loaded Entities.
    Logger.logInfo(loadedDonaldDuck.toString());
    Logger.logInfo(loadedDaisyDuck.toString());
  }

  private static final class Person extends Entity {

    private final Value<String> firstName = Value.withValueType(String.class);

    private final Value<String> lastName = Value.withValueType(String.class);

    @Override
    public String toString() {
      return (firstName.getStoredValue() + " " + lastName.getStoredValue());
    }
  }

  private MsSqlDataAdapterTutorial() {
  }
}
