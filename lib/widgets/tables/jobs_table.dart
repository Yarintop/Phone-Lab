import 'package:flutter/material.dart';
import 'package:myapp/constants/job_specific.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/provider/item_provider.dart';
import 'package:myapp/widgets/popups/job_detail_alert.dart';
import 'package:provider/provider.dart';

//TODO - add filter to get specific type of jobs (completed, new, ongoing and etc.)
class JobsTable extends StatelessWidget {
  Future<dynamic> generateJobDialog(BuildContext context, Job job) {
    return showDialog<String>(
      context: context,
      builder: (context) => AlertDialog(
        title: Text("Job Details"),
        content: JobDetail(job: job),
      ),
    );
  }

  List<DataColumn> getHeaders() {
    return <DataColumn>[
      // DataColumn(
      // label: Text("ID"),
      // ),
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

  List<DataRow> getJobRows(BuildContext context, ItemProvider provider) {
    return provider.jobs
        .map((Job e) => translateJobToRow(context, provider, e))
        .toList();
    //TODO - read items from the provider and map them with translateJobToRow
  }

  DataRow translateJobToRow(
      BuildContext context, ItemProvider provider, Job job) {
    return DataRow(
      onSelectChanged: (bool selected) { // Allows to open the menu of job details when pressing on the row instead of a specific cell
        if (selected) generateJobDialog(context, job);
      },
      cells: [
        // DataCell(Text(job.id)),
        DataCell(Text(job.customer)),
        DataCell(Text(job.phoneNumber)),
        DataCell(Text(job.phoneModel)),
        DataCell(Text(job.jobDescription)),
        DataCell(Text(
            job.status == null ? Progress.UNDEFINED.value : job.status.value)),
        DataCell(Text(job.createdTimestamp.toString())),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<ItemProvider>(
        builder: (context, provider, child) => Container(
              child: DataTable(
                showCheckboxColumn: false, // To hide the checkboxes that are being put when selecting by rows
                columns: getHeaders(),
                rows: getJobRows(context, provider),
              ),
            ));
  }
}
