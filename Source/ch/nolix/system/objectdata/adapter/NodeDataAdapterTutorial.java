/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.objectdata.adapter;

import ch.nolix.base.document.node.MutableNode;
import ch.nolix.base.errorcontrol.logging.Logger;
import ch.nolix.system.objectdata.model.Entity;
import ch.nolix.system.objectdata.model.EntityTypeSet;
import ch.nolix.system.objectdata.model.ValueField;

/**
 * @author Silvan Wyss
 */
final class NodeDataAdapterTutorial {
  private NodeDataAdapterTutorial() {
  }

  public static void main() {
    // create nodeDatabase.
    final var nodeDatabase = MutableNode.createEmpty();

    // create schema
    final var entityTypeSet = EntityTypeSet.withEntityType(Person.class);

    // create a NodeDataAdapter
    final var nodeDataAdapter = //
    NodeDataAdapter.forNodeDatabase(nodeDatabase).withName("PersonDB").andSchema(entityTypeSet);

    // create a first Entity
    final var donaldDuck = new Person();
    donaldDuck.firstName.setValue("Donald");
    donaldDuck.lastName.setValue("Duck");

    // create a second Entity
    final var daisyDuck = new Person();
    daisyDuck.firstName.setValue("Daisy");
    daisyDuck.lastName.setValue("Duck");

    // insert the created Entities into the NodeDataAdapter
    nodeDataAdapter.insertEntity(daisyDuck).insertEntity(donaldDuck);

    // let the NodeDataAdapter save its changes
    nodeDataAdapter.saveChanges();

    // let the NodeDataAdapter load the first Entity
    final var loadedDonaldDuck = //
    nodeDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(donaldDuck.getId());

    // let the NodeDataAdapter load the second Entity
    final var loadedDaisyDuck = //
    nodeDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(daisyDuck.getId());

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
