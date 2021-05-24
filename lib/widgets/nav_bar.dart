import 'package:flutter/material.dart';
import 'package:myapp/routes/routes.dart';
import 'package:myapp/widgets/nav_item.dart';

class NavBar extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      width: 100,
      child: Column(
        mainAxisSize: MainAxisSize.max,
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          NavItem(text: "All Jobs", route: ROUTE_ALL_JOBS),
          NavItem(text: "Create New Job", route: ROUTE_CREATE_JOB),
          NavItem(text: "New Jobs", route: ROUTE_NEW_JOBS),
          NavItem(text: "Ongoing jobs", route: ROUTE_ONGOING_JOBS),
          NavItem(text: "Completed Jobs", route: ROUTE_COMPLETED_JOBS),
          NavItem(text: "Parts List", route: ROUTE_PARTS),
        ],
      ),
    );
  }
}
