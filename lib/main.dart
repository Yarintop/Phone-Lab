import 'package:flutter/material.dart';
import 'package:myapp/providers/item_provider.dart';
import 'package:myapp/providers/user_provider.dart';
import 'package:myapp/providers/operation_provider.dart';
import 'package:myapp/providers/utils_provider.dart';
import 'package:myapp/routes/route_generator.dart';
import 'package:myapp/routes/routes.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart' as DotEnv;
import 'package:provider/provider.dart';
import 'app_view.dart';

Future main() async {
  await DotEnv.load(fileName: ".env");
  print("Server address: ${DotEnv.env["HOST"]}:${DotEnv.env["PORT"]}");
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
    return Consumer2<UtilsProvider, UserProvider>(
      builder: (context, utilsProvider, userProvider, child) => MaterialApp(
        title: 'Repair System',
        // theme: ThemeData.dark().copyWith(
        // primaryColor: Colors.deepPurple,
        // ),
        theme: ThemeData(primaryColor: Colors.blueGrey),

        initialRoute: ROUTE_LOGIN,
        navigatorKey: navKey,
        onGenerateRoute: (settings) {
          // utilsProvider.selectRoute(settings.name);
          return RouteGenerator.generateRoute(settings: settings, loggedIn: userProvider.loggedInUser != null);
        },
        builder: (context, child) => AppView(
          child: child,
          currentRoute: utilsProvider.selectedRoute,
        ),
      ),
    );
  }
}
