package ch.nolix.systemtutorial.objectschematutorial.schemaadaptertutorial;

import ch.nolix.system.objectschema.parameterizedpropertytype.ParameterizedBackReferenceType;
import ch.nolix.system.objectschema.parameterizedpropertytype.ParameterizedMultiReferenceType;
import ch.nolix.system.objectschema.parameterizedpropertytype.ParameterizedValueType;
import ch.nolix.system.objectschema.schema.Column;
import ch.nolix.system.objectschema.schema.Table;
import ch.nolix.system.objectschema.schemaadapter.MsSqlSchemaAdapter;
import ch.nolix.systemapi.databaseapi.datatypeapi.DataType;

public final class MsSqlSchemaAdapterTutorial {

  private MsSqlSchemaAdapterTutorial() {
  }

  public static void main(String[] args) {
    try (
    final var databaseSchemaAdapter = MsSqlSchemaAdapter
      .toLocalhost()
      .andDefaultPort()
      .toDatabase("CountryDatabase")
      .usingLoginName("sa")
      .andLoginPassword("sa1234")) {

      final var cityTable = new Table("City")
        .addColumn(new Column("Name", new ParameterizedValueType<>(DataType.STRING)))
        .addColumn(new Column("Population", new ParameterizedValueType<>(DataType.STRING)));

      final var countryTable = new Table("Country")
        .addColumn(new Column("Name", new ParameterizedValueType<>(DataType.STRING)));

      final var citiesColumn = new Column("Cities", new ParameterizedMultiReferenceType(cityTable));
      countryTable.addColumn(citiesColumn);

      cityTable.addColumn(new Column("Country", new ParameterizedBackReferenceType(citiesColumn)));

      databaseSchemaAdapter.addTable(cityTable).addTable(countryTable).saveChanges();
    }
  }
}
