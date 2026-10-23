/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.objectdata.adapter;

import ch.nolix.base.document.node.MutableNode;
import ch.nolix.base.errorcontrol.logging.Logger;
import ch.nolix.system.objectdata.model.AbstractEntity;
import ch.nolix.system.objectdata.model.EntityTypeSet;
import ch.nolix.system.objectdata.model.ValueField;

/**
 * @author Silvan Wyss
 */
final class NodeDataAdapterTutorial {
  private NodeDataAdapterTutorial() {
  }

  public static void main() {
    // Create nodeDatabase.
    final var nodeDatabase = MutableNode.createEmpty();

    // Create schema.
    final var entityTypeSet = EntityTypeSet.withEntityType(Person.class);

    // Create a NodeDataAdapter.
    final var nodeDataAdapter = //
    NodeDataAdapter.forNodeDatabase(nodeDatabase).withName("PersonDB").andSchema(entityTypeSet);

    // Create a first Entity.
    final var donaldDuck = new Person();
    donaldDuck.firstName.setValue("Donald");
    donaldDuck.lastName.setValue("Duck");

    // Create a second Entity.
    final var daisyDuck = new Person();
    daisyDuck.firstName.setValue("Daisy");
    daisyDuck.lastName.setValue("Duck");

    // Insert the created Entities into the NodeDataAdapter.
    nodeDataAdapter.insertEntity(daisyDuck).insertEntity(donaldDuck);

    // Let the NodeDataAdapter save its changes.
    nodeDataAdapter.saveChanges();

    // Let the NodeDataAdapter load the first Entity.
    final var loadedDonaldDuck = //
    nodeDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(donaldDuck.getId());

    // Let the NodeDataAdapter load the second Entity.
    final var loadedDaisyDuck = //
    nodeDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(daisyDuck.getId());

    // Log the loaded Entities.
    Logger.logInfo(loadedDonaldDuck.toString());
    Logger.logInfo(loadedDaisyDuck.toString());
  }

  private static final class Person extends AbstractEntity {
    private final ValueField<String> firstName = ValueField.withValueType(String.class);

    private final ValueField<String> lastName = ValueField.withValueType(String.class);

    @Override
    public String toString() {
      return (firstName.getStoredValue() + " " + lastName.getStoredValue());
    }
  }
}
