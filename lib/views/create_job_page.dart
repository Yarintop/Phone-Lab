import 'package:flutter/material.dart';
import 'package:myapp/providers/utils_provider.dart';
import 'package:myapp/widgets/job_from/job_form.dart';
import 'package:provider/provider.dart';

class CreateJobPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Consumer<UtilsProvider>(
      builder: (context, provider, _) => Container(
        child: Column(
          children: [
            SizedBox(height: 32.0),
            //TODO - style the header
            Text("Creating New Job"),
            SizedBox(height: 64.0),
            JobCreationForm(),
          ],
        ),
      ),
    );
  }
}
