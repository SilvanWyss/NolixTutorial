package ch.nolix.systemtutorial.objectschematutorial.schemaadaptertutorial;

import ch.nolix.coreapi.datamodelapi.fieldproperty.DataType;
import ch.nolix.system.objectschema.parameterizedfieldtype.ParameterizedBackReferenceType;
import ch.nolix.system.objectschema.parameterizedfieldtype.ParameterizedMultiReferenceType;
import ch.nolix.system.objectschema.parameterizedfieldtype.ParameterizedValueType;
import ch.nolix.system.objectschema.schema.Column;
import ch.nolix.system.objectschema.schema.Table;
import ch.nolix.system.objectschema.schemaadapter.MsSqlSchemaAdapterBuilder;

public final class MsSqlSchemaAdapterTutorial {

  private MsSqlSchemaAdapterTutorial() {
  }

  public static void main(String[] args) {

    //Creates msSqlSchemaAdapter.
    final var msSqlSchemaAdapter = //
    MsSqlSchemaAdapterBuilder.createMsSqlSchemaAdapter()
      .toLocalAddress()
      .andMsSqlPort()
      .toDatabase("CountryDB")
      .withLoginName("mssqluser")
      .andLoginPassword("mssql1234");

    //Creates cityTable.
    final var cityTable = //
    new Table("City")
      .addColumn(new Column("Name", ParameterizedValueType.forDataType(DataType.STRING)))
      .addColumn(new Column("Population", ParameterizedValueType.forDataType(DataType.STRING)));

    //Creates countryTable.
    final var countryTable = //
    new Table("Country").addColumn(new Column("Name", ParameterizedValueType.forDataType(DataType.STRING)));

    //Creates citiesColumn.
    final var citiesColumn = new Column("Cities", ParameterizedMultiReferenceType.forReferencedTable(cityTable));

    //Adds citiesColumn to the countryTable.
    countryTable.addColumn(citiesColumn);

    //Creates countryColumn.
    final var countryColumn = //
    new Column("Country", ParameterizedBackReferenceType.forBackReferencedColumn(citiesColumn));

    //Adds countryColumn to the cityTable. 
    cityTable.addColumn(countryColumn);

    //Adds the cityTable and countryTable to the msSqlSchemaAdapter.
    msSqlSchemaAdapter.addTable(cityTable).addTable(countryTable);

    //Lets the msSqlSchemaAdapter save its changes.
    msSqlSchemaAdapter.saveChanges();
  }
}
