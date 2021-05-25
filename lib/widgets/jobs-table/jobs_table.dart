import 'package:flutter/material.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/provider/item_provider.dart';
import 'package:provider/provider.dart';

//TODO - add filter to get specific type of jobs (completed, new, ongoing and etc.)
class JobsTable extends StatelessWidget {
  List<DataColumn> getHeaders() {
    return <DataColumn>[
      DataColumn(
        label: Text("ID"),
      ),
      DataColumn(
        label: Text("Customer"),
      ),
      DataColumn(
        label: Text("Phone Number"),
      ),
      DataColumn(
        label: Text("Model"),
      ),
      DataColumn(
        label: Text("Description"),
      ),
      DataColumn(
        label: Text("Status"),
      ),
      DataColumn(
        label: Text("Date Added"),
      ),
    ];
  }

  List<DataRow> getJobRows(provider) {
    return <DataRow>[
      //TODO - read items from the provider and map them with translateJobToRow
    ];
  }

  DataRow translateJobToRow(Job job) {
    //TODO - translate each Job to a row with 7 DataCell widgets
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<ItemProvider>(
        builder: (context, provider, child) => Container(
              child: DataTable(
                columns: getHeaders(),
                rows: getJobRows(provider),
              ),
            ));
  }
}
