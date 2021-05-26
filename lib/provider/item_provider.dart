import 'package:flutter/cupertino.dart';
import 'package:myapp/constants/job_specific.dart';
import 'package:myapp/models/Item.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/models/job.dart';

class ItemProvider extends ChangeNotifier {
  //TEMP
  final List<Job> jobs = [
    Job.fromParams(
      "temp",
      "2021b.noam.levi1",
      "Phone",
      "SN-950F",
      true,
      DateTime.now(),
      User(),
      1,
      1,
      {
        "customer": "John",
        "phoneModel": "SN-950F",
        "phoneNumber": "054-1234567",
        "description": "Sample Phone",
        "status": Progress.IN_PROGRESS,
        "assignedTechnician": "Jimmy",
      },
    )
  ];
  //
  final List<Item> items = [];

  // TODO - maybe change bool to enum of error types
  Future<bool> addJob(Job job) async {
    //TODO - make an API call
    jobs.add(job);
    job.dirty = false;
    notifyListeners();

    return true;
  }

  Future<bool> updateItem(Job job) async {
    if (job.fixDescription != job.draftFixDescription) job.fixDescription = job.draftFixDescription;
    if (job.assignedTechnician != job.draftTechnician) job.assignedTechnician = job.draftTechnician;
    if (job.status != job.draftStatus) job.status = job.draftStatus;
    //TODO - make an API call to update the job (updateItem)

    job.dirty = false;
    notifyListeners();
    return true;
  }

  void clearDraft(Job job) {
    job.draftFixDescription = job.fixDescription;
    job.draftStatus = job.status;
    job.draftTechnician = job.assignedTechnician;
    job.dirty = false;
    notifyListeners();
  }
}
