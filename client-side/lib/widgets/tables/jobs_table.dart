import 'package:flutter/material.dart';
import 'package:myapp/constants/job_specific.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/providers/item_provider.dart';
import 'package:myapp/providers/operation_provider.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/routes/routes.dart';
import 'package:myapp/widgets/popups/job_popup/job_detail_alert.dart';
import 'package:myapp/widgets/popups/snackbar/snack_error.dart';
import 'package:provider/provider.dart';

class JobsTable extends StatelessWidget {
  final JobFilter filter;

  JobsTable(this.filter);

  Future<dynamic> generateJobDialog(BuildContext context, Job job, {Function onSaved}) {
    return showDialog<String>(
      context: context,
      builder: (context) => AlertDialog(
        backgroundColor: Colors.blueGrey[50],
        title: Text("Job Details"),
        content: JobDetail(
          job: job,
          onSaved: onSaved,
        ),
      ),
    );
  }

  Color getColor(Progress status) {
    switch (status) {
      case Progress.COMPLETED:
        return Colors.green;
      case Progress.FAILED_TO_FIX:
        return Colors.red;
      case Progress.IN_PROGRESS:
        return Colors.blue;
      case Progress.WAITING_FOR_PICKUP:
        return Colors.orange;
      default: // also UNDEFINED
        return Colors.black;
    }
  }

  List<DataColumn> getHeaders() {
    return <DataColumn>[
      // DataColumn(
      // label: Text("ID"),
      // ),
      DataColumn(
        label: Text(
          "Customer",
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      DataColumn(
        label: Text(
          "Phone Number",
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      DataColumn(
        label: Text(
          "Model",
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      DataColumn(
        label: Text(
          "Description",
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      DataColumn(
        label: Text(
          "Status",
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      DataColumn(
        label: Text(
          "Date Added",
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
    ];
  }

  List<DataRow> getJobRows(
      BuildContext context, ItemProvider itemProvider, UserProvider userProvider, OperationProvider operationProvider) {
    int index = 0;
    return itemProvider.jobs
        .map(
          (Job job) => translateJobToRow(
            context,
            itemProvider,
            userProvider,
            operationProvider,
            job,
            index: index++,
            onSaved: (Job job) {
              itemProvider.updateJob(job, userProvider).then(
                (value) {
                  //when the item updated, load all items back
                  Navigator.pop(context);
                  itemProvider.loadAllJobs(userProvider, filter: filter);
                },
              ).onError((error, stackTrace) {
                showErrorSnack(context, error);
              });
            },
          ),
        )
        .toList();
  }

  DataRow translateJobToRow(
    BuildContext context,
    ItemProvider itemProvider,
    UserProvider userProvider,
    OperationProvider operationProvider,
    Job job, {
    Function onSaved,
    int index,
  }) {
    return DataRow(
      color: MaterialStateColor.resolveWith((states) => index % 2 == 0 ? Colors.blueGrey[50] : Colors.blueGrey[100]),
      onSelectChanged: (bool selected) {
        // Allows to open the menu of job details when pressing on the row instead of a specific cell
        if (selected) {
          itemProvider
              .updateJobParts(job, userProvider)
              .then((value) => operationProvider.pullJobPrice(job, userProvider.loggedInUser))
              .onError((error, stackTrace) {
            showErrorSnack(context, error);
          });

          generateJobDialog(context, job, onSaved: onSaved);
        }
      },
      cells: [
        // DataCell(Text(job.id)),
        DataCell(Text(job.customer)),
        DataCell(Text(job.phoneNumber)),
        DataCell(Text(job.phoneModel)),
        DataCell(Text(job.jobDescription)),
        DataCell(
          Text(
            job.status == null ? Progress.UNDEFINED.value : job.status.value,
            style: TextStyle(
              color: getColor(job.status),
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
        DataCell(Text(job.createdTimestamp.toString())),
      ],
    );
  }

  Widget loadTable(
    BuildContext context,
    UserProvider userProvider,
    ItemProvider itemProvider,
    OperationProvider operationProvider,
  ) {
    if (!itemProvider.isJobsLoaded) {
      return Center(
        child: Text(
          "Loading Data...",
          style: TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 20,
          ),
        ),
      );
    } else
      return Container(
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
          rows: getJobRows(context, itemProvider, userProvider, operationProvider),
        ),
      );
  }

  @override
  Widget build(BuildContext context) {
    return Consumer3<UserProvider, ItemProvider, OperationProvider>(
      builder: (context, userProvider, itemProvider, operationProvider, child) => loadTable(
        context,
        userProvider,
        itemProvider,
        operationProvider,
      ),
    );
  }
}
