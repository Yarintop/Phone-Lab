import 'package:flutter/material.dart';
import 'package:myapp/models/part.dart';
import 'package:myapp/providers/item_provider.dart';
import 'package:myapp/providers/operation_provider.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/widgets/popups/part_popup/part_detail.dart';
import 'package:provider/provider.dart';

class PartsTable extends StatelessWidget {
  void generatePartDialog(BuildContext context, Part part, int count) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text("Part Details"),
        content: PartDetail(part: part, count: count),
      ),
    );
  }

  List<DataColumn> getHeaders() {
    return <DataColumn>[
      DataColumn(
        label: Text("ID"),
      ),
      DataColumn(
        label: Text("Name"),
      ),
      DataColumn(
        label: Text("In Inventory"),
      ),
      DataColumn(
        label: Text("Price"),
      ),
    ];
  }

  List<DataRow> getJobRows(
    BuildContext context,
    ItemProvider itemProvider,
    OperationProvider operationProvider,
    UserProvider userProvider,
  ) {
    int index = 0;
    return itemProvider.parts
        .map((Part e) => translatePartToRow(context, userProvider, operationProvider, e, index: index++))
        .toList();
  }

  DataRow translatePartToRow(
      BuildContext context, UserProvider userProvider, OperationProvider operationProvider, Part part,
      {int index}) {
    return DataRow(
      color: MaterialStateColor.resolveWith((states) => index % 2 == 0 ? Colors.blueGrey[50] : Colors.blueGrey[100]),
      onSelectChanged: (bool selected) async {
        if (selected) {
          int count = await operationProvider.getPartCount(part, userProvider.loggedInUser);
          generatePartDialog(context, part, count);
        }
      },
      cells: [
        // DataCell(Text(job.id)),
        DataCell(Text(part.id.substring(0, 10))),
        DataCell(Text(part.name)),
        DataCell(
          Text(
            part.active ? "Yes" : "No",
            style: TextStyle(
              color: part.active ? Colors.green : Colors.red,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
        DataCell(Text(part.price.toString())),
        // DataCell(Text(part.quality)),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Consumer3<ItemProvider, OperationProvider, UserProvider>(
      builder: (context, itemProvider, operationProvider, userProvider, child) => Container(
        height: 650,
        width: 550,
        child: ListView(
          scrollDirection: Axis.vertical,
          children: [
            DataTable(
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
              rows: getJobRows(context, itemProvider, operationProvider, userProvider),
            ),
          ],
        ),
      ),
    );
  }
}
