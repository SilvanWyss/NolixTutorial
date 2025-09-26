package ch.nolix.system.objectschema.adapter;

import ch.nolix.core.container.immutablelist.ImmutableList;
import ch.nolix.coreapi.datamodel.fieldproperty.DataType;
import ch.nolix.system.objectschema.model.Column;
import ch.nolix.system.objectschema.model.Table;
import ch.nolix.systemapi.midschema.fieldproperty.FieldType;

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
      .addColumn(
        new Column(
          "Name",
          FieldType.VALUE_FIELD,
          DataType.STRING,
          ImmutableList.createEmpty(),
          ImmutableList.createEmpty()))
      .addColumn(
        new Column(
          "Population",
          FieldType.VALUE_FIELD,
          DataType.STRING,
          ImmutableList.createEmpty(),
          ImmutableList.createEmpty()));

    //Creates countryTable.
    final var countryTable = //
    Table.withName("Country").addColumn(
      new Column(
        "Name",
        FieldType.VALUE_FIELD,
        DataType.STRING,
        ImmutableList.createEmpty(),
        ImmutableList.createEmpty()));

    //Creates citiesColumn.
    final var citiesColumn = //
    new Column(
      "Cities",
      FieldType.MULTI_REFERENCE,
      DataType.STRING,
      ImmutableList.withElement(cityTable),
      ImmutableList.createEmpty());

    //Adds the citiesColumn to the countryTable.
    countryTable.addColumn(citiesColumn);

    //Creates countryColumn.
    final var countryColumn = //
    new Column(
      "Country",
      FieldType.BACK_REFERENCE,
      DataType.STRING,
      ImmutableList.createEmpty(),
      ImmutableList.withElement(citiesColumn));

    //Adds countryColumn to the cityTable. 
    cityTable.addColumn(countryColumn);

    //Adds the cityTable and countryTable to the MsSqlSchemaAdapter.
    msSqlSchemaAdapter.addTable(cityTable).addTable(countryTable);

    //Lets the MsSqlSchemaAdapter save its changes.
    msSqlSchemaAdapter.saveChanges();
  }
}
