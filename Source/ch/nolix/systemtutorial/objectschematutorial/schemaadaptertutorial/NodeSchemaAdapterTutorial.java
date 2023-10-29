package ch.nolix.systemtutorial.objectschematutorial.schemaadaptertutorial;

//own imports
import ch.nolix.core.document.node.MutableNode;
import ch.nolix.core.errorcontrol.logger.GlobalLogger;
import ch.nolix.system.objectschema.parameterizedpropertytype.ParameterizedBackReferenceType;
import ch.nolix.system.objectschema.parameterizedpropertytype.ParameterizedMultiReferenceType;
import ch.nolix.system.objectschema.parameterizedpropertytype.ParameterizedValueType;
import ch.nolix.system.objectschema.schema.Column;
import ch.nolix.system.objectschema.schema.Table;
import ch.nolix.system.objectschema.schemaadapter.NodeSchemaAdapter;
import ch.nolix.systemapi.databaseapi.datatypeapi.DataType;

public final class NodeSchemaAdapterTutorial {

  private NodeSchemaAdapterTutorial() {
  }

  public static void main(String[] args) {

    final var database = new MutableNode();

    try (final var nodeDatabaseSchemaAdapter = NodeSchemaAdapter.forDatabaseNode("CountryDatabase", database)) {

      final var cityTable = new Table("City")
        .addColumn(new Column("Name", new ParameterizedValueType<>(DataType.STRING)))
        .addColumn(new Column("Population", new ParameterizedValueType<>(DataType.INTEGER_4BYTE)));

      final var countryTable = new Table("Country")
        .addColumn(new Column("Name", new ParameterizedValueType<>(DataType.STRING)));

      final var citiesColumn = new Column("Cities", new ParameterizedMultiReferenceType(cityTable));
      countryTable.addColumn(citiesColumn);
      cityTable.addColumn(new Column("Country", new ParameterizedBackReferenceType(citiesColumn)));

      nodeDatabaseSchemaAdapter.addTable(cityTable).addTable(countryTable).saveChanges();

      GlobalLogger.logInfo(database.toFormattedString());
    }
  }
}
