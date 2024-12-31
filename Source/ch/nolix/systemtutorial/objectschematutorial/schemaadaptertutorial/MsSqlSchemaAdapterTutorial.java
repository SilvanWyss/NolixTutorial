package ch.nolix.systemtutorial.objectschematutorial.schemaadaptertutorial;

import ch.nolix.coreapi.datamodelapi.fieldproperty.DataType;
import ch.nolix.system.objectschema.adapter.MsSqlSchemaAdapterBuilder;
import ch.nolix.system.objectschema.model.BackReferenceModel;
import ch.nolix.system.objectschema.model.Column;
import ch.nolix.system.objectschema.model.MultiReferenceModel;
import ch.nolix.system.objectschema.model.Table;
import ch.nolix.system.objectschema.model.ValueModel;

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
    Table.withName("City")
      .addColumn(new Column("Name", ValueModel.forDataType(DataType.STRING)))
      .addColumn(new Column("Population", ValueModel.forDataType(DataType.STRING)));

    //Creates countryTable.
    final var countryTable = //
    Table.withName("Country").addColumn(new Column("Name", ValueModel.forDataType(DataType.STRING)));

    //Creates citiesColumn.
    final var citiesColumn = new Column("Cities", MultiReferenceModel.forReferencedTable(cityTable));

    //Adds the citiesColumn to the countryTable.
    countryTable.addColumn(citiesColumn);

    //Creates countryColumn.
    final var countryColumn = //
    new Column("Country", BackReferenceModel.forBackReferencedColumn(citiesColumn));

    //Adds countryColumn to the cityTable. 
    cityTable.addColumn(countryColumn);

    //Adds the cityTable and countryTable to the msSqlSchemaAdapter.
    msSqlSchemaAdapter.addTable(cityTable).addTable(countryTable);

    //Lets the msSqlSchemaAdapter save its changes.
    msSqlSchemaAdapter.saveChanges();
  }
}
