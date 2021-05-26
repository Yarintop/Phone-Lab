import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:myapp/routes/routes.dart';
import 'package:myapp/views/create_job_page.dart';
import 'package:myapp/views/jobs_page.dart';
import 'package:myapp/views/login_page.dart';
import 'package:myapp/views/parts_page.dart';
import 'package:myapp/views/user_page.dart';

class RouteGenerator {
  static Route<dynamic> generateRoute({RouteSettings settings, String routeName}) {
    String name = "";
    if (settings != null)
      name = settings.name;
    else
      name = routeName;
    switch (name) {
      case ROUTE_CREATE_JOB:
        return MaterialPageRoute(builder: (_) => CreateJobPage());
        break;
      case ROUTE_ALL_JOBS:
        return MaterialPageRoute(builder: (_) => JobPage(filter: JobFilter.ALL));
        break;
      case ROUTE_NEW_JOBS:
        return MaterialPageRoute(builder: (_) => JobPage(filter: JobFilter.NEW));
        break;
      case ROUTE_ONGOING_JOBS:
        return MaterialPageRoute(builder: (_) => JobPage(filter: JobFilter.ONGOING));
        break;
      case ROUTE_COMPLETED_JOBS:
        return MaterialPageRoute(builder: (_) => JobPage(filter: JobFilter.COMPLETED));
        break;
      case ROUTE_PARTS:
        return MaterialPageRoute(builder: (_) => PartsPage());
        break;
      case ROUTE_USERS:
        return MaterialPageRoute(builder: (_) => UsersPage());
        break;
      default: // if the text is "login-page" or anything else, go to the login page.
        return MaterialPageRoute(builder: (_) => LoginPage());
    }
  }
}
