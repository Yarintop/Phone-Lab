import 'package:flutter/material.dart';
import 'package:myapp/routes/route_generator.dart';
import 'package:myapp/routes/routes.dart';

import 'app_view.dart';

void main() {
  runApp(MyApp());
}

/// This widget is the root of your application.
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Repair System',
        // theme: ThemeData.dark().copyWith(
        // primaryColor: Colors.deepPurple,
        // ),
        initialRoute: ROUTE_LOGIN,
        navigatorKey: navKey,
        onGenerateRoute: RouteGenerator.generateRoute,
        builder: (context, child) => AppView(child: child));
  }
}
