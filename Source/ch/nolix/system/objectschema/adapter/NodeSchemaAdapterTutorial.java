/*
 * Copyright © by Silvan Wyss. All rights reserved.
 */
package ch.nolix.system.objectschema.adapter;

import ch.nolix.base.container.immutablelist.ImmutableList;
import ch.nolix.base.document.node.MutableNode;
import ch.nolix.base.errorcontrol.logging.Logger;
import ch.nolix.baseapi.datamodel.fieldproperty.DataType;
import ch.nolix.system.objectschema.model.Column;
import ch.nolix.system.objectschema.model.Table;
import ch.nolix.systemapi.midschema.fieldproperty.FieldType;

/**
 * @author Silvan Wyss
 */
final class NodeSchemaAdapterTutorial {
  private NodeSchemaAdapterTutorial() {
  }

  public static void main() {
    //Creates nodeDatabase.
    final var nodeDatabase = MutableNode.createEmpty();

    //Creates a NodeSchemaAdapter.
    final var nodeSchemaAdapter = NodeSchemaAdapter.forNodeDatabase("CountryDB", nodeDatabase);

    //Creates cityTable.
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

    //Creates countryTable.
    final var countryTable = //
    Table.withName("Country")
      .addColumn(
        Column.withIdAndNameAndContentModel(
          "3",
          "Name",
          FieldType.VALUE_FIELD,
          DataType.STRING,
          ImmutableList.createEmpty(),
          ImmutableList.createEmpty()));

    //Creates citiesColumn.
    final var citiesColumn = //
    Column.withIdAndNameAndContentModel(
      "4",
      "Cities",
      FieldType.MULTI_REFERENCE,
      DataType.STRING,
      ImmutableList.withElement(cityTable),
      ImmutableList.createEmpty());

    //Adds the citiesColumn to the countryTable.
    countryTable.addColumn(citiesColumn);

    //Creates countryColumn.
    final var countryColumn = //
    Column.withIdAndNameAndContentModel(
      "5",
      "Country",
      FieldType.BACK_REFERENCE,
      DataType.STRING,
      ImmutableList.createEmpty(),
      ImmutableList.withElement(citiesColumn));

    //Adds countryColumn to the cityTable. 
    cityTable.addColumn(countryColumn);

    //Adds the cityTable and countryTable to the NodeSchemaAdapter.
    nodeSchemaAdapter.addTable(cityTable).addTable(countryTable);

    //Lets the NodeSchemaAdapter save its changes.
    nodeSchemaAdapter.saveChanges();

    //Logs the nodeDatabase.
    Logger.logInfo(nodeDatabase.toFormattedString());
  }
}
