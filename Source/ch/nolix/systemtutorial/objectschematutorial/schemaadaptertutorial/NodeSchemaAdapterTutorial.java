package ch.nolix.systemtutorial.objectschematutorial.schemaadaptertutorial;

import ch.nolix.core.document.node.MutableNode;
import ch.nolix.core.errorcontrol.logger.GlobalLogger;
import ch.nolix.system.objectschema.parameterizedpropertytype.ParameterizedBackReferenceType;
import ch.nolix.system.objectschema.parameterizedpropertytype.ParameterizedMultiReferenceType;
import ch.nolix.system.objectschema.parameterizedpropertytype.ParameterizedValueType;
import ch.nolix.system.objectschema.schema.Column;
import ch.nolix.system.objectschema.schema.Table;
import ch.nolix.system.objectschema.schemaadapter.NodeSchemaAdapter;
import ch.nolix.systemapi.entitypropertyapi.datatypeapi.DataType;

public final class NodeSchemaAdapterTutorial {

  private NodeSchemaAdapterTutorial() {
  }

  public static void main(String[] args) {

    final var database = new MutableNode();

    try (final var nodeDatabaseSchemaAdapter = NodeSchemaAdapter.forDatabaseNode("CountryDatabase", database)) {

      final var cityTable = new Table("City")
        .addColumn(new Column("Name", ParameterizedValueType.forDataType(DataType.STRING)))
        .addColumn(new Column("Population", ParameterizedValueType.forDataType(DataType.INTEGER_4BYTE)));

      final var countryTable = new Table("Country")
        .addColumn(new Column("Name", ParameterizedValueType.forDataType(DataType.STRING)));

      final var citiesColumn = new Column("Cities", ParameterizedMultiReferenceType.forReferencedTable(cityTable));
      countryTable.addColumn(citiesColumn);
      cityTable.addColumn(new Column("Country", ParameterizedBackReferenceType.forBackReferencedColumn(citiesColumn)));

      nodeDatabaseSchemaAdapter.addTable(cityTable).addTable(countryTable).saveChanges();

      GlobalLogger.logInfo(database.toFormattedString());
    }
  }
}
