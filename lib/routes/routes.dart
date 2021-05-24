import 'package:flutter/cupertino.dart';

enum JobFilter { ALL, NEW, ONGOING, COMPLETED }

const String ROUTE_LOGIN = "/login";
const String ROUTE_ALL_JOBS = "/all-jobs";
const String ROUTE_CREATE_JOB = "/create-job";
const String ROUTE_NEW_JOBS = "/new-jobs";
const String ROUTE_ONGOING_JOBS = "/ongoing";
const String ROUTE_COMPLETED_JOBS = "/completed-jobs";
const String ROUTE_PARTS = "/parts";

final navKey = new GlobalKey<NavigatorState>();
