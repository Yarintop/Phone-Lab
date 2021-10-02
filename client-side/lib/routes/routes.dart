import 'package:flutter/cupertino.dart';

enum JobFilter { ALL, MY_JOBS, ONGOING, COMPLETED }

JobFilter getFilterByRoute(String route) {
  if (route == ROUTE_COMPLETED_JOBS) return JobFilter.COMPLETED;
  if (route == ROUTE_MY_JOBS) return JobFilter.MY_JOBS;
  if (route == ROUTE_ONGOING_JOBS) return JobFilter.ONGOING;
  return JobFilter.ALL;
}

const String ROUTE_LOGIN = "/login";
const String ROUTE_ALL_JOBS = "/all-jobs";
const String ROUTE_CREATE_JOB = "/create-job";
const String ROUTE_MY_JOBS = "/my-jobs";
const String ROUTE_ONGOING_JOBS = "/ongoing";
const String ROUTE_COMPLETED_JOBS = "/completed-jobs";
const String ROUTE_PARTS = "/parts";
const String ROUTE_USERS = "/users";

final navKey = new GlobalKey<NavigatorState>();
