import 'package:flutter/material.dart';
import 'package:myapp/constants/job_specific.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/widgets/form_widgets/dropdown_button.dart';
import 'package:provider/provider.dart';

class TechnicianDetails extends StatefulWidget {
  final Job job;
  final Function callback;
  TechnicianDetails({this.job, this.callback});

  @override
  _TechnicianDetailsState createState() => _TechnicianDetailsState();
}

class _TechnicianDetailsState extends State<TechnicianDetails> {
  // User _selectedTechnician;
  // String _selectedTechnician;

  String getDefaultValue(UserProvider provider, Job job) {
    if (provider.users.length == 0) return NOT_ASSIGNED;
    String technician = job.dirty ? job.draftTechnician : job.assignedTechnician;
    return technician == "" ? NOT_ASSIGNED : technician;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<UserProvider>(
      builder: (context, provider, child) => Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("Created By: "),
              Text(
                widget.job.createdBy.email,
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("Assigned To: "),
              Padding(
                padding: const EdgeInsets.all(12.0),
                child: SizedBox(
                  width: 350,
                  child: CustomDropdownButton(
                      defaultValue: getDefaultValue(provider, widget.job),
                      onChange: widget.callback,
                      values: [NOT_ASSIGNED, ...provider.users.map<String>((u) => u.email)]),
                ),
              )
            ],
          ),
        ],
      ),
    );
  }
}
