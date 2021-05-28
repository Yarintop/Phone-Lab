import 'package:flutter/material.dart';
import 'package:myapp/models/part.dart';
import 'package:myapp/providers/item_provider.dart';
import 'package:provider/provider.dart';

class PartsTable extends StatelessWidget {
  List<DataColumn> getHeaders() {
    return <DataColumn>[
      // DataColumn(
      // label: Text("ID"),
      // ),
      DataColumn(
        label: Text("ID"),
      ),
      DataColumn(
        label: Text("Name"),
      ),
      DataColumn(
        label: Text("Is Inventory"),
      ),
      DataColumn(
        label: Text("Price"),
        // ),
        // DataColumn(
        // label: Text("Quality"),
      ),
    ];
  }

  List<DataRow> getJobRows(BuildContext context, ItemProvider provider) {
    int index = 0;
    return provider.parts.map((Part e) => translatePartToRow(context, provider, e, index: index++)).toList();
  }

  DataRow translatePartToRow(BuildContext context, ItemProvider provider, Part part, {int index}) {
    return DataRow(
      color: MaterialStateColor.resolveWith((states) => index % 2 == 0 ? Colors.blueGrey[50] : Colors.blueGrey[100]),
      onSelectChanged: (bool selected) {
        // Allows to open the menu of job details when pressing on the row instead of a specific cell
        if (selected) return; //generatePartDialog(context, part); // TODO: Create generatePartDialog function
      },
      cells: [
        // DataCell(Text(job.id)),
        DataCell(Text(part.id.substring(0, 10))),
        DataCell(Text(part.name)),
        DataCell(Text(part.active ? "Yes" : "No")),
        DataCell(Text(part.price)),
        // DataCell(Text(part.quality)),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<ItemProvider>(
      builder: (context, provider, child) => Container(
        child: DataTable(
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(5),
            border: Border.all(
              color: Colors.black,
              width: 1,
            ),
          ),
          headingRowColor: MaterialStateColor.resolveWith((states) => Colors.blueGrey[200]),
          showCheckboxColumn: false, // To hide the checkboxes that are being put when selecting by rows
          columns: getHeaders(),
          rows: getJobRows(context, provider),
        ),
      ),
    );
  }
}
