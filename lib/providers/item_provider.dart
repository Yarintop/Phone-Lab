import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:myapp/constants/job_specific.dart';
import 'package:myapp/constants/project_specific.dart';
import 'package:myapp/constants/user_specific.dart';
import 'package:myapp/models/item.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/models/part.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:http/http.dart' as http;

class ItemProvider extends ChangeNotifier {
  final baseUrl = "http://${env["HOST"]}:${env["PORT"]}/$BASE_API";

  //TEMP

  // Job.fromParams(
  //   "temp",
  //   "2021b.noam.levi1",
  //   "Phone",
  //   "SN-950F",
  //   true,
  //   DateTime.now(),
  //   User(),
  //   1,
  //   1,
  //   {
  //     "customer": "John",
  //     "phoneModel": "SN-950F",
  //     "phoneNumber": "054-1234567",
  //     "description": "Sample Phone",
  //     "status": Progress.IN_PROGRESS,
  //     "assignedTechnician": "Jimmy",
  //   },
  // )
  final List<Job> jobs = [];

  final List<Part> parts = [
    Part.fromParams(
      "partId",
      "2021b.noam.levi1",
      "Screen",
      "Samsung Galaxy S21+ Screen",
      true,
      DateTime.now(),
      User(),
      1,
      1,
      {
        "isUsed": true,
        "description": "Samsung Galaxy S21+ screen (6.7\" Inch)",
        "quality": "New",
        "price": "50\$",
      },
    )
  ];
  //

  final List<Item> items = [];
  bool _isJobsLoaded = false;
  bool get isJobsLoaded => _isJobsLoaded;
  set isJobsLoaded(val) => _isJobsLoaded = val;

  Future<bool> loadAllJobs(UserProvider provider) async {
    User user = provider.loggedInUser;
    print("Loading jobs...");
    if (user == null) {
      print("ERROR - user unll");
      return false; //TODO - make an error box or something
    }

    String tempRole;
    print("Item - " + user.role);
    if (user.role != Role.MANAGER.value) {
      tempRole = user.role;
      print("Saving role (ITEM) - " + tempRole);
      await provider.updateRole(user, Role.MANAGER.value);
    }

    final res = await http.get(Uri.parse("$baseUrl/$ITEM_API/${user.space}/${user.email}?type=$REPAIR_JOB_TYPE"));

    //get all users
    if (res.statusCode != 200) {
      //TODO - make erorr show bahshah
      return false;
    }
    Iterable tmp = jsonDecode(res.body);
    List<Job> resJobs = tmp.map<Job>((j) => Job.fromJSON(j)).toList();

    jobs.addAll(resJobs);

    // Restore role if it was changed
    if (tempRole != null) {
      await provider.updateRole(user, tempRole);
      User tempUser = provider.users.firstWhere((u) => u == user);
      if (tempUser != null) tempUser.role = tempRole;
    }
    isJobsLoaded = true;
    print("ITEMMMMMMMMM DONEEEEEEEEE");
    notifyListeners();
    return true;
  }

  // TODO - maybe change bool to enum of error types
  Future<bool> addJob(Job job, User user) async {
    //TODO = make an error message
    _isJobsLoaded = false;
    jobs.clear();
    print(user.role);
    if (user.role != Role.MANAGER.value) return false;
    String url = "$baseUrl/$ITEM_API/${user.space}/${user.email}";
    job.itemAttributes["status"] = job.status.value;

    final res = await http.post(Uri.parse(url),
        body: jsonEncode({
          "type": job.type,
          "name": job.name,
          "active": job.active,
          "createdTimestamp": job.createdTimestamp.toIso8601String(),
          "createdBy": {
            "userId": {"space": job.createdBy.space, "email": job.createdBy.email}
          },
          "itemAttributes": job.itemAttributes,
          "location": {
            "lat": job.lat,
            "lng": job.lng,
          }
        }),
        headers: {"Content-Type": "application/json"});
    if (res.statusCode == 200) notifyListeners();

    //TODO - add error / success messsage
    return res.statusCode == 200;
  }

  Future<bool> updateItem(Job job) async {
    if (job.fixDescription != job.draftFixDescription) job.fixDescription = job.draftFixDescription;
    if (job.assignedTechnician != job.draftTechnician) job.assignedTechnician = job.draftTechnician;
    if (job.status != job.draftStatus) job.status = job.draftStatus;
    //TODO - make an API call to update the job (updateItem)

    job.dirty = false;
    _isJobsLoaded = false;
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

  Future<bool> pullData(UserProvider provider) async {
    jobs.clear();
    _isJobsLoaded = false;
    return await loadAllJobs(provider);
  }
}
