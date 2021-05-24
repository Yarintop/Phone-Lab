import 'package:flutter/material.dart';
import 'package:myapp/routes/routes.dart';

class JobPage extends StatelessWidget {
  final JobFilter filter;

  JobPage({this.filter = JobFilter.ALL});

  @override
  Widget build(BuildContext context) {
    return Container(child: Text("Job Page - " + this.filter.toString()));
  }
}
