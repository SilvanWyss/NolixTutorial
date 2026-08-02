/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.objectschema.adapter;

import ch.nolix.base.datastructure.immutablelist.ImmutableList;
import ch.nolix.baseapi.datamodel.fieldproperty.DataType;
import ch.nolix.system.objectschema.model.Column;
import ch.nolix.system.objectschema.model.Table;
import ch.nolix.systemapi.midschema.fieldproperty.FieldType;

/**
 * @author Silvan Wyss
 */
final class MsSqlSchemaAdapterTutorial {
  private MsSqlSchemaAdapterTutorial() {
  }

  public static void main() {
    // create a MsSqlSchemaAdapter
    final var msSqlSchemaAdapter = //
    MsSqlSchemaAdapterBuilder.createMsSqlSchemaAdapter()
      .toLocalHost()
      .andMsSqlPort()
      .toDatabase("CountryDB")
      .withLoginName("mssqluser")
      .andPassword("mssql1234");

    // create cityTable
    final var cityTable = //
    Table
      .withName("City")
      .addColumn(
        Column.withIdAndNameAndContentModel(
          "1",
          "Name",
          FieldType.VALUE_FIELD,
          DataType.STRING,
          ImmutableList.createEmpty(),
          ImmutableList.createEmpty()))
      .addColumn(
        Column.withIdAndNameAndContentModel(
          "2",
          "Population",
          FieldType.VALUE_FIELD,
          DataType.STRING,
          ImmutableList.createEmpty(),
          ImmutableList.createEmpty()));

    // create countryTable
    final var countryTable = //
    Table.withName("Country").addColumn(
      Column.withIdAndNameAndContentModel(
        "3",
        "Name",
        FieldType.VALUE_FIELD,
        DataType.STRING,
        ImmutableList.createEmpty(),
        ImmutableList.createEmpty()));

    // create citiesColumn
    final var citiesColumn = //
    Column.withIdAndNameAndContentModel(
      "4",
      "Cities",
      FieldType.MULTI_REFERENCE,
      DataType.STRING,
      ImmutableList.withElement(cityTable),
      ImmutableList.createEmpty());

    // add the citiesColumn to the countryTable
    countryTable.addColumn(citiesColumn);

    // create countryColumn.
    final var countryColumn = //
    Column.withIdAndNameAndContentModel(
      "5",
      "Country",
      FieldType.BACK_REFERENCE,
      DataType.STRING,
      ImmutableList.createEmpty(),
      ImmutableList.withElement(citiesColumn));

    // add countryColumn to the cityTable
    cityTable.addColumn(countryColumn);

    // add the cityTable and countryTable to the MsSqlSchemaAdapter
    msSqlSchemaAdapter.addTable(cityTable).addTable(countryTable);

    // let the MsSqlSchemaAdapter save its changes
    msSqlSchemaAdapter.saveChanges();
  }
}
