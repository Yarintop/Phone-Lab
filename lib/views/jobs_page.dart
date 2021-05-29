import 'package:flutter/material.dart';
import 'package:myapp/routes/routes.dart';
import 'package:myapp/widgets/tables/jobs_table.dart';

class JobPage extends StatelessWidget {
  final JobFilter filter;

  JobPage({this.filter = JobFilter.ALL});

  String getTitle(JobFilter filter) {
    switch (filter) {
      case JobFilter.ONGOING:
        return "On Going Jobs";
      case JobFilter.COMPLETED:
        return "Completed Jobs";
      case JobFilter.MY_JOBS:
        return "My Jobs";
      default:
        return "All Jobs";
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Text(
              getTitle(filter),
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
          ),
          JobsTable(filter),
        ],
      ),
    );
  }
}
