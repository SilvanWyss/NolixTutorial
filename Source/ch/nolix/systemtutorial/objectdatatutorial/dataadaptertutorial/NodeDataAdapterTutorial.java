package ch.nolix.systemtutorial.objectdatatutorial.dataadaptertutorial;

import ch.nolix.core.document.node.MutableNode;
import ch.nolix.core.errorcontrol.logging.GlobalLogger;
import ch.nolix.system.objectdata.data.Entity;
import ch.nolix.system.objectdata.data.Value;
import ch.nolix.system.objectdata.dataadapter.NodeDataAdapter;
import ch.nolix.system.objectdata.schema.Schema;

public final class NodeDataAdapterTutorial {

  private NodeDataAdapterTutorial() {
  }

  public static void main(String[] args) {

    //Creates nodeDatabase.
    final var nodeDatabase = MutableNode.createEmpty();

    //Creates schema.
    final var schema = Schema.withEntityType(Person.class);

    //Creates nodeDataAdapter.
    final var nodeDataAdapter = NodeDataAdapter.forNodeDatabase(nodeDatabase).withName("PersonDB").andSchema(schema);

    //Creates a first Entity.
    final var donaldDuck = new Person();
    donaldDuck.firstName.setValue("Donald");
    donaldDuck.lastName.setValue("Duck");

    //Creates a second Entity.
    final var daisyDuck = new Person();
    daisyDuck.firstName.setValue("Daisy");
    daisyDuck.lastName.setValue("Duck");

    //Inserts the created Entities into the nodeDataAdapter.
    nodeDataAdapter.insertEntity(daisyDuck, donaldDuck);

    //Lets the nodeDataAdapter save its changes.
    nodeDataAdapter.saveChanges();

    //Lets the nodeDataAdapter load the first Entity.
    final var loadedDonaldDuck = //
    nodeDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(donaldDuck.getId());

    //Lets the nodeDataAdapter load the second Entity.
    final var loadedDaisyDuck = //
    nodeDataAdapter.getStoredTableByEntityType(Person.class).getStoredEntityById(daisyDuck.getId());

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
