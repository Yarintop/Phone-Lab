import 'package:flutter/material.dart';
import 'package:myapp/routes/routes.dart';
import 'package:myapp/widgets/jobs-table/jobs_table.dart';

class JobPage extends StatelessWidget {
  final JobFilter filter;

  JobPage({this.filter = JobFilter.ALL});

  @override
  Widget build(BuildContext context) {
    return Container(
        child: Column(
      children: [
        Text("Header"),
        JobsTable(),
      ],
    ));
  }
}
