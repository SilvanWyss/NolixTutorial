/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.objectdata.adapter;

import ch.nolix.core.document.node.MutableNode;
import ch.nolix.core.errorcontrol.logging.Logger;
import ch.nolix.system.objectdata.model.Entity;
import ch.nolix.system.objectdata.model.EntityTypeSet;
import ch.nolix.system.objectdata.model.ValueField;

/**
 * @author Silvan Wyss
 */
final class NodeDataAdapterTutorial {
  private NodeDataAdapterTutorial() {
  }

  public static void main(String[] args) {
    //Creates nodeDatabase.
    final var nodeDatabase = MutableNode.createEmpty();

    //Creates schema.
    final var entityTypeSet = EntityTypeSet.withEntityType(Person.class);

    //Creates a NodeDataAdapter.
    final var nodeDataAdapter = //
    NodeDataAdapter.forNodeDatabase(nodeDatabase).withName("PersonDB").andSchema(entityTypeSet);

    //Creates a first Entity.
    final var donaldDuck = new Person();
    donaldDuck.firstName.setValue("Donald");
    donaldDuck.lastName.setValue("Duck");

    //Creates a second Entity.
    final var daisyDuck = new Person();
    daisyDuck.firstName.setValue("Daisy");
    daisyDuck.lastName.setValue("Duck");

    //Inserts the created Entities into the NodeDataAdapter.
    nodeDataAdapter.insertEntity(daisyDuck).insertEntity(donaldDuck);

    //Lets the NodeDataAdapter save its changes.
    nodeDataAdapter.saveChanges();

    //Lets the NodeDataAdapter load the first Entity.
    final var loadedDonaldDuck = //
    nodeDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(donaldDuck.getId());

    //Lets the NodeDataAdapter load the second Entity.
    final var loadedDaisyDuck = //
    nodeDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(daisyDuck.getId());

    //Logs the loaded Entities.
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
