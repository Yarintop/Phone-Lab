import 'package:flutter/material.dart';
import 'package:myapp/constants/job_specific.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/provider/item_provider.dart';
import 'package:myapp/widgets/form_widgets/dropdown_button.dart';
import 'package:myapp/widgets/popups/customer_details.dart';
import 'package:myapp/widgets/popups/technical_details.dart';
import 'package:myapp/widgets/popups/technician_details.dart';
import 'package:provider/provider.dart';

class JobDetail extends StatefulWidget {
  final Job job;

  JobDetail({this.job});

  @override
  _JobDetailState createState() => _JobDetailState();
}

class _JobDetailState extends State<JobDetail> {
  Widget generateDraftAlert() {
    if (!widget.job.dirty) return SizedBox();
    return Padding(
      padding: const EdgeInsets.only(top: 8),
      child: Text(
        "Unsaved changes detected, please save",
        style: TextStyle(color: Colors.amber[700], fontSize: 18, fontWeight: FontWeight.bold),
      ),
    );
  }

  void assignTechnician(Job job, String email) {
    if ((job.dirty && job.draftFixDescription == email) || (!job.dirty && job.assignedTechnician == email)) return;
    setState(() {
      job.draftTechnician = email == NOT_ASSIGNED ? "" : email;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<ItemProvider>(
      builder: (context, provider, child) => Container(
        width: 550,
        child: Column(
          children: [
            Text("Repair Job - \"" + widget.job.phoneModel + "\""),
            generateDraftAlert(),
            SizedBox(height: 16),
            // Customer name & contact
            CustomerDetails(job: widget.job),
            SizedBox(height: 16),
            // Technical details
            TechnicalDetails(
              job: widget.job,
              onChange: (v) => setState(() => widget.job.draftFixDescription = v),
            ),
            SizedBox(height: 16),

            // Work progress details
            TechnicianDetails(
              job: widget.job,
              callback: (email) => assignTechnician(widget.job, email),
            ),

            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text("Job Status: "),
                CustomDropdownButton(
                  defaultValue: widget.job.dirty ? widget.job.draftStatus.value : widget.job.status.value,
                  onChange: (val) => setState(() {
                    widget.job.draftStatus = Progress.values.firstWhere((e) => e.value == val);
                  }),
                  values: Progress.values.map<String>((v) => v.value).toList(),
                ),
              ],
            ),

            SizedBox(height: 16),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                ElevatedButton(
                  onPressed: () {
                    provider.updateItem(widget.job);
                    Navigator.pop(context);
                  },
                  child: Text("Save"),
                ),
                ElevatedButton(
                  onPressed: () {
                    provider.clearDraft(widget.job);
                    Navigator.pop(context);
                  },
                  child: Text("Cancel"),
                )
              ],
            ),
          ],
        ),
      ),
    );
  }
}
