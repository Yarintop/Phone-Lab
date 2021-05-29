import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:myapp/constants/job_specific.dart';
import 'package:myapp/constants/part_specific.dart';
import 'package:myapp/constants/project_specific.dart';
import 'package:myapp/constants/user_specific.dart';
import 'package:myapp/models/item.dart';
import 'package:myapp/models/user.dart';
import 'package:myapp/models/job.dart';
import 'package:myapp/models/part.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:http/http.dart' as http;
import 'package:myapp/routes/routes.dart';

class ItemProvider extends ChangeNotifier {
  final baseUrl = "http://${env["HOST"]}:${env["PORT"]}/$BASE_API";

  final List<Job> jobs = [];

  final List<Part> parts = [];

  final List<Item> items = [];
  bool _isJobsLoaded = false;
  bool _isPartsLoaded = false;

  bool get isJobsLoaded => _isJobsLoaded;
  set isJobsLoaded(val) => _isJobsLoaded = val;

  Future<Iterable> loadAllItems(UserProvider provider, String type) async {
    User user = provider.loggedInUser;
    if (user == null) return Future.error("User not logged in!");

    String tempRole;
    if (user.role != Role.MANAGER.value) {
      tempRole = user.role;
      await provider.updateRole(user, Role.MANAGER.value);
    }

    final res = await http.get(Uri.parse("$baseUrl/$ITEM_API/${user.space}/${user.email}?type=$type"));

    //get all users
    if (res.statusCode != 200) return Future.error(jsonDecode(res.body)["message"]);

    if (tempRole != null) {
      await provider.updateRole(user, tempRole);
      User tempUser = provider.users.firstWhere((u) => u == user);
      if (tempUser != null) tempUser.role = tempRole;
    }
    isJobsLoaded = true;
    return jsonDecode(res.body);
  }

  bool matchJobToFilter(Job job, User user, JobFilter filter) {
    switch (filter) {
      case JobFilter.COMPLETED:
        return job.status == Progress.COMPLETED || job.status == Progress.FAILED_TO_FIX;
      case JobFilter.ONGOING:
        return job.status == Progress.IN_PROGRESS;
      case JobFilter.MY_JOBS:
        return job.assignedTechnician == user.email;
      default:
        return true;
    }
  }

  Future<bool> loadAllJobs(UserProvider provider, {JobFilter filter = JobFilter.ALL}) async {
    Iterable rawJobs = await loadAllItems(provider, REPAIR_JOB_TYPE).onError((error, stackTrace) {
      print("Test");
      print(error);
      return Future.error(error);
    });
    // if (rawJobs == null || rawJobs.length == 0) return false;
    print("pepe");
    List<Job> resJobs = rawJobs.map<Job>((j) => Job.fromJSON(j)).toList();
    resJobs = resJobs.where((j) => matchJobToFilter(j, provider.loggedInUser, filter)).toList();
    jobs.clear();
    jobs.addAll(resJobs);

    notifyListeners();

    return true;
  }

  Future<bool> loadAllParts(UserProvider provider) async {
    Iterable rawParts = await loadAllItems(provider, PART_TYPE).onError((error, stackTrace) => Future.error(error));
    // if (rawParts == null || rawParts.length == 0) return false;

    List<Part> resParts = rawParts.map<Part>((j) => Part.fromJSON(j)).toList();
    parts.clear();
    parts.addAll(resParts);

    notifyListeners();

    return true;
  }

  Future<bool> addJob(Job job, User user) async {
    _isJobsLoaded = false;
    jobs.clear();

    return addItem(job, user);
  }

  Future<bool> addPart(Part part, User user) async {
    _isPartsLoaded = false;
    parts.clear();

    return addItem(part, user);
  }

  Future<bool> addItem(Item item, User user) async {
    if (user.role != Role.MANAGER.value) Future.error("User must be a Manager! Current user is ${user.role}");
    String url = "$baseUrl/$ITEM_API/${user.space}/${user.email}";

    final res = await http.post(
      Uri.parse(url),
      body: item.convertToJson(),
      headers: {"Content-Type": "application/json"},
    );

    if (res.statusCode == 200)
      notifyListeners();
    else
      return Future.error(jsonDecode(res.body)["message"]);
    return res.statusCode == 200;
  }

  Future<bool> updateJob(Job job, UserProvider userProvider) async {
    if (job.fixDescription != job.draftFixDescription) job.fixDescription = job.draftFixDescription;
    if (job.assignedTechnician != job.draftTechnician) job.assignedTechnician = job.draftTechnician;
    if (job.status != job.draftStatus) job.status = job.draftStatus;

    User loggedInUser = userProvider.loggedInUser;
    String tempRole;
    // Toggle role if user is not manager
    if (loggedInUser.role != Role.MANAGER.value) {
      tempRole = loggedInUser.role;
      await userProvider.updateRole(loggedInUser, Role.MANAGER.value);
    }

    bool res = await updateItem(job, userProvider.loggedInUser);

    // Toggle role if user wasn't manager
    if (tempRole != null) await userProvider.updateRole(loggedInUser, tempRole);

    job.dirty = false;
    _isJobsLoaded = false;
    notifyListeners();
    return res;
  }

  Future<bool> updatePart(Part part, UserProvider userProvider) async {
    User loggedInUser = userProvider.loggedInUser;
    String tempRole;
    // Toggle role if user is not manager
    if (loggedInUser.role != Role.MANAGER.value) {
      tempRole = loggedInUser.role;
      await userProvider.updateRole(loggedInUser, Role.MANAGER.value);
    }

    bool res = await updateItem(part, userProvider.loggedInUser);

    // Toggle role if user wasn't manager
    if (tempRole != null) await userProvider.updateRole(loggedInUser, tempRole);

    _isPartsLoaded = false;
    notifyListeners();
    return res;
  }

  Future<bool> bindPart(Job job, Part part, UserProvider userProvider) async {
    User user = userProvider.loggedInUser;
    String url = "$baseUrl/$ITEM_API/${user.space}/${user.email}/${job.space}/${job.id}/children";
    String tempRole;
    // Toggle role if user is not manager
    if (user.role != Role.MANAGER.value) {
      tempRole = user.role;
      await userProvider.updateRole(user, Role.MANAGER.value);
    }

    final res = await http.put(
      Uri.parse(url),
      headers: {"Content-Type": "application/json"},
      // body: part.convertToJson(), // The whole part will work as well because it includes the itemId
      body: jsonEncode(
        {
          "space": part.space,
          "id": part.id,
        },
      ), // The whole part will work as well because it includes the itemId
    );
    part.active = false;
    await updatePart(part, userProvider);
    if (res.statusCode != 200) return Future.error(jsonDecode(res.body)["message"]);
    if (tempRole != null) await userProvider.updateRole(user, tempRole);
    notifyListeners();
    return true;
  }

  Future<bool> updateItem(Item item, User user) async {
    String url = "$baseUrl/$ITEM_API/${user.space}/${user.email}/${item.space}/${item.id}";
    final res = await http.put(
      Uri.parse(url),
      body: item.convertToJson(),
      headers: {"Content-Type": "application/json"},
    );
    //For now just log the error
    if (res.statusCode != 200) return Future.error(jsonDecode(res.body)["message"]);
    return res.statusCode == 200;
  }

  Future<bool> updateJobParts(Job job, UserProvider userProvider) async {
    User user = userProvider.loggedInUser;
    String url = "$baseUrl/$ITEM_API/${user.space}/${user.email}/${job.space}/${job.id}/children";
    String tempRole;
    // Toggle role if user is not manager
    if (user.role != Role.MANAGER.value) {
      tempRole = user.role;
      await userProvider.updateRole(user, Role.MANAGER.value);
    }

    final res = await http.get(Uri.parse(url));
    if (res.statusCode != 200) return Future.error(jsonDecode(res.body)["message"]);
    Iterable tmp = jsonDecode(res.body);
    List<Part> resParts = tmp.map((j) => Part.fromJSON(j)).toList();
    job.partsUsed = resParts;

    if (tempRole != null) await userProvider.updateRole(user, tempRole);
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

  Future<bool> pullData(UserProvider provider, JobFilter filter) async {
    jobs.clear();
    parts.clear();
    _isJobsLoaded = false;
    _isPartsLoaded = false;
    bool success = await loadAllJobs(provider, filter: filter);
    success = success && await loadAllParts(provider);
    return success;
  }
}
