/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.objectdata.adapter;

import ch.nolix.base.errorcontrol.logging.Logger;
import ch.nolix.system.objectdata.model.Entity;
import ch.nolix.system.objectdata.model.EntityTypeSet;
import ch.nolix.system.objectdata.model.ValueField;

/**
 * @author Silvan Wyss
 */
final class MsSqlDataAdapterTutorial {
  private MsSqlDataAdapterTutorial() {
  }

  public static void main() {
    // create schema
    final var entityTypeSet = EntityTypeSet.withEntityType(Person.class);

    // create a MsSqlDataAdapter
    final var msSqlDataAdapter = //
    MsSqlDataAdapterBuilder
      .createMsSqlDataAdapter()
      .toLocalHost()
      .andMsSqlPort()
      .andDatabase("PersonDB")
      .withLoginName("mssqluser")
      .andPassword("mssql1234")
      .andSchema(entityTypeSet);

    // create a first Entity
    final var donaldDuck = new Person();
    donaldDuck.firstName.setValue("Donald");
    donaldDuck.lastName.setValue("Duck");

    // create a second Entity
    final var daisyDuck = new Person();
    daisyDuck.firstName.setValue("Daisy");
    daisyDuck.lastName.setValue("Duck");

    // insert the created Entities into the MsSqlDataAdapter
    msSqlDataAdapter.insertEntity(daisyDuck).insertEntity(donaldDuck);

    // let the MsSqlDataAdapter save its changes
    msSqlDataAdapter.saveChanges();

    // let the MsSqlDataAdapter load the first Entity
    final var loadedDonaldDuck = //
    msSqlDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(donaldDuck.getId());

    // let the MsSqlDataAdapter load the second Entity
    final var loadedDaisyDuck = //
    msSqlDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(daisyDuck.getId());

    // log the loaded Entities
    Logger.logInfo(loadedDonaldDuck.toString());
    Logger.logInfo(loadedDaisyDuck.toString());
  }

  private static final class Person extends Entity {
    private final ValueField<String> firstName = ValueField.withValueType(String.class);

    private final ValueField<String> lastName = ValueField.withValueType(String.class);

    @Override
    public String toString() {
      return (firstName.getStoredValue() + " " + lastName.getStoredValue());
    }
  }
}
