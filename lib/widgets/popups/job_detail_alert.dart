import 'package:flutter/material.dart';
import 'package:myapp/constants/job_specific.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/models/part.dart';
import 'package:myapp/providers/item_provider.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/widgets/form_widgets/dropdown_button.dart';
import 'package:myapp/widgets/popups/customer_details.dart';
import 'package:myapp/widgets/popups/job_parts.dart';
import 'package:myapp/widgets/popups/technical_details.dart';
import 'package:myapp/widgets/popups/technician_details.dart';
import 'package:provider/provider.dart';

class JobDetail extends StatefulWidget {
  final Job job;
  Function onSaved;
  JobDetail({this.job, this.onSaved});

  @override
  _JobDetailState createState() => _JobDetailState();
}

class _JobDetailState extends State<JobDetail> {
  Widget generateDraftAlert() {
    if (!widget.job.dirty) return SizedBox();
    return Padding(
      padding: const EdgeInsets.only(top: 8),
      child: Text(
        CHANGES_DETECTED_STRING,
        style: TextStyle(color: Colors.amber[700], fontSize: 18, fontWeight: FontWeight.bold),
      ),
    );
  }

  String _partNameAndPrice;

  void _bindPart(ItemProvider itemProvider, UserProvider userProvider) {
    //Get the last location because price won't have ':' (need to implement validation )
    int delimeter = _partNameAndPrice.lastIndexOf(":");
    String partName = _partNameAndPrice.substring(0, delimeter);
    String partPrice = _partNameAndPrice.substring(delimeter + 1);
    // Get the first part that have the same name and price, exact isn't matter (only the active one matter [for this build] )
    Part part = itemProvider.parts.firstWhere((p) => p.name == partName && p.price == partPrice);
    itemProvider
        .bindPart(widget.job, part, userProvider)
        .then((res) => res ? itemProvider.updateJobParts(widget.job, userProvider) : null);
  }

  void _setPartName(String name) {
    setState(() {
      this._partNameAndPrice = name;
    });
  }

  void assignTechnician(Job job, String email) {
    if ((job.dirty && job.draftFixDescription == email) || (!job.dirty && job.assignedTechnician == email)) return;
    setState(() {
      job.draftTechnician = email == NOT_ASSIGNED ? "" : email;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Consumer2<ItemProvider, UserProvider>(
      builder: (context, itemProvider, userProvider, child) => Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Container(
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
                JobPartsInfo(
                  job: widget.job,
                  onBind: _partNameAndPrice == null ? null : () => _bindPart(itemProvider, userProvider),
                  onChange: _setPartName,
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text("Job Status: "),
                    Padding(
                      padding: const EdgeInsets.all(12.0),
                      child: SizedBox(
                        width: 250,
                        child: CustomDropdownButton(
                          defaultValue: widget.job.dirty ? widget.job.draftStatus.value : widget.job.status.value,
                          onChange: (val) => setState(() {
                            widget.job.draftStatus = Progress.values.firstWhere((e) => e.value == val);
                          }),
                          values: Progress.values.map<String>((v) => v.value).toList(),
                        ),
                      ),
                    ),
                  ],
                ),

                // * Confirmation / Cancellation area
                // SizedBox(height: 8),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    ElevatedButton(
                      onPressed: () {
                        widget.onSaved(widget.job);
                      },
                      child: Text("Save"),
                    ),
                    ElevatedButton(
                      onPressed: () {
                        itemProvider.clearDraft(widget.job);
                        Navigator.pop(context);
                      },
                      child: Text("Cancel"),
                    )
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
