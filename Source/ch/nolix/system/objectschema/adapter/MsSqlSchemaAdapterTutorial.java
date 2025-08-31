package ch.nolix.system.objectschema.adapter;

import ch.nolix.coreapi.datamodel.fieldproperty.DataType;
import ch.nolix.system.objectschema.model.BackReferenceModel;
import ch.nolix.system.objectschema.model.Column;
import ch.nolix.system.objectschema.model.MultiReferenceModel;
import ch.nolix.system.objectschema.model.Table;
import ch.nolix.system.objectschema.model.ValueModel;

final class MsSqlSchemaAdapterTutorial {

  private MsSqlSchemaAdapterTutorial() {
  }

  public static void main(String[] args) {

    //Creates a MsSqlSchemaAdapter.
    final var msSqlSchemaAdapter = //
    MsSqlSchemaAdapterBuilder.createMsSqlSchemaAdapter()
      .toLocalAddress()
      .andMsSqlPort()
      .toDatabase("CountryDB")
      .withLoginName("mssqluser")
      .andLoginPassword("mssql1234");

    //Creates cityTable.
    final var cityTable = //
    Table
      .withName("City")
      .addColumn(new Column("Name", ValueModel.forDataType(DataType.STRING)))
      .addColumn(new Column("Population", ValueModel.forDataType(DataType.STRING)));

    //Creates countryTable.
    final var countryTable = //
    Table.withName("Country").addColumn(new Column("Name", ValueModel.forDataType(DataType.STRING)));

    //Creates citiesColumn.
    final var citiesColumn = new Column("Cities", MultiReferenceModel.forReferenceableTable(cityTable));

    //Adds the citiesColumn to the countryTable.
    countryTable.addColumn(citiesColumn);

    //Creates countryColumn.
    final var countryColumn = //
    new Column("Country", BackReferenceModel.forBackReferencedColumn(citiesColumn));

    //Adds countryColumn to the cityTable. 
    cityTable.addColumn(countryColumn);

    //Adds the cityTable and countryTable to the MsSqlSchemaAdapter.
    msSqlSchemaAdapter.addTable(cityTable).addTable(countryTable);

    //Lets the MsSqlSchemaAdapter save its changes.
    msSqlSchemaAdapter.saveChanges();
  }
}
