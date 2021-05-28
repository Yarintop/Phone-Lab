import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:myapp/routes/routes.dart';
import 'package:myapp/views/create_job_page.dart';
import 'package:myapp/views/jobs_page.dart';
import 'package:myapp/views/parts_page.dart';
import 'package:myapp/views/user_page.dart';

class RouteGenerator {
  static Route<dynamic> generateRoute({RouteSettings settings, String routeName, bool loggedIn = false}) {
    // if not logged in, return only users page
    if (!loggedIn)
      return PageRouteBuilder(
        pageBuilder: (_, __, ___) => UsersPage(),
        transitionDuration: Duration(seconds: 0),
      );

    String name = "";
    if (settings != null)
      name = settings.name;
    else
      name = routeName;

    switch (name) {
      case ROUTE_CREATE_JOB:
        return PageRouteBuilder(
          pageBuilder: (_, __, ___) => CreateJobPage(),
          transitionDuration: Duration(seconds: 0), // added duration=0 to disable minimize waiting time
        );
        break;
      case ROUTE_ALL_JOBS:
        return PageRouteBuilder(
          pageBuilder: (_, __, ___) => JobPage(filter: JobFilter.ALL),
          transitionDuration: Duration(seconds: 0),
        );
        break;
      case ROUTE_MY_JOBS:
        return PageRouteBuilder(
          pageBuilder: (_, __, ___) => JobPage(filter: JobFilter.MY_JOBS),
          transitionDuration: Duration(seconds: 0),
        );
        break;
      case ROUTE_ONGOING_JOBS:
        return PageRouteBuilder(
          pageBuilder: (_, __, ___) => JobPage(filter: JobFilter.ONGOING),
          transitionDuration: Duration(seconds: 0),
        );
        break;
      case ROUTE_COMPLETED_JOBS:
        return PageRouteBuilder(
            pageBuilder: (_, __, ___) => JobPage(filter: JobFilter.COMPLETED),
            transitionDuration: Duration(seconds: 0));
        break;
      case ROUTE_PARTS:
        return PageRouteBuilder(
          pageBuilder: (_, __, ___) => PartsPage(),
          transitionDuration: Duration(seconds: 0),
        );
        break;
      default: // return user page if asked or asked something that is not valid
        return PageRouteBuilder(
          pageBuilder: (_, __, ___) => UsersPage(),
          transitionDuration: Duration(seconds: 0),
        );
        break;
    }
  }
}
