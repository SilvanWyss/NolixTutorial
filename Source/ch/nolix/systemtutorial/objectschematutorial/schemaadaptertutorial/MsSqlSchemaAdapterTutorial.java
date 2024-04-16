package ch.nolix.systemtutorial.objectschematutorial.schemaadaptertutorial;

import ch.nolix.system.objectschema.parameterizedfieldtype.ParameterizedBackReferenceType;
import ch.nolix.system.objectschema.parameterizedfieldtype.ParameterizedMultiReferenceType;
import ch.nolix.system.objectschema.parameterizedfieldtype.ParameterizedValueType;
import ch.nolix.system.objectschema.schema.Column;
import ch.nolix.system.objectschema.schema.Table;
import ch.nolix.system.objectschema.schemaadapter.MsSqlSchemaAdapterBuilder;
import ch.nolix.systemapi.fieldapi.datatypeapi.DataType;

public final class MsSqlSchemaAdapterTutorial {

  private MsSqlSchemaAdapterTutorial() {
  }

  public static void main(String[] args) {
    try (

    final var databaseSchemaAdapter = //
    MsSqlSchemaAdapterBuilder.createMsSqlSchemaAdapter()
      .toLocalAddress()
      .andMsSqlPort()
      .toDatabase("CountryDatabase")
      .withLoginName("sa")
      .andLoginPassword("sa1234")) {

      final var cityTable = new Table("City")
        .addColumn(new Column("Name", ParameterizedValueType.forDataType(DataType.STRING)))
        .addColumn(new Column("Population", ParameterizedValueType.forDataType(DataType.STRING)));

      final var countryTable = new Table("Country")
        .addColumn(new Column("Name", ParameterizedValueType.forDataType(DataType.STRING)));

      final var citiesColumn = new Column("Cities", ParameterizedMultiReferenceType.forReferencedTable(cityTable));
      countryTable.addColumn(citiesColumn);

      cityTable.addColumn(new Column("Country", ParameterizedBackReferenceType.forBackReferencedColumn(citiesColumn)));

      databaseSchemaAdapter.addTable(cityTable).addTable(countryTable).saveChanges();
    }
  }
}
