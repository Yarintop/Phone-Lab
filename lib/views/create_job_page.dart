import 'package:flutter/material.dart';
import 'package:myapp/widgets/job_from/job_form.dart';

class CreateJobPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          SizedBox(height: 32.0),
          //TODO - style the header
          Text("Creating New Job"),
          SizedBox(height: 64.0),
          JobCreationForm(),
        ],
      ),
    );
  }
}
