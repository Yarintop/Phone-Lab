import 'package:flutter/material.dart';
import 'package:myapp/provider/item_provider.dart';
import 'package:myapp/provider/user_provider.dart';
import 'package:myapp/provider/operation_provider.dart';
import 'package:myapp/provider/utils_provider.dart';
import 'package:myapp/routes/route_generator.dart';
import 'package:myapp/routes/route_observer.dart';
import 'package:myapp/routes/routes.dart';
import 'package:provider/provider.dart';
import 'app_view.dart';

void main() {
  runApp(MultiProvider(providers: [
    ChangeNotifierProvider(create: (context) => ItemProvider()),
    ChangeNotifierProvider(create: (context) => UserProvider()),
    ChangeNotifierProvider(create: (context) => OperationProvider()),
    ChangeNotifierProvider(create: (context) => UtilsProvider()),
  ], child: App()));
}

// This widget is the root of your application.
class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // observer.subscribe(RepairRouteObserver(), RouteGenerator.generateRoute(routeName: ROUTE_LOGIN));

    return Consumer<UtilsProvider>(
      builder: (context, utilsProvider, child) => MaterialApp(
        title: 'Repair System',
        // theme: ThemeData.dark().copyWith(
        // primaryColor: Colors.deepPurple,
        // ),

        // * This observer is to detect routes changes
        navigatorObservers: [RepairRouteObserver(provider: utilsProvider)],
        initialRoute: ROUTE_LOGIN,
        navigatorKey: navKey,
        onGenerateRoute: (settings) {
          // utilsProvider.selectRoute(settings.name);
          return RouteGenerator.generateRoute(settings: settings);
        },
        builder: (context, child) => AppView(
          child: child,
          currentRoute: utilsProvider.selectedRoute,
        ),
      ),
    );
  }
}
