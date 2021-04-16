import 'package:flutter/material.dart';

import 'MyHomePage.dart';

void main() {
    runApp(MyApp());
}

/// This widget is the root of your application.
class MyApp extends StatelessWidget
{
    @override
    Widget build(BuildContext context)
    {
        return MaterialApp(
            title: 'Flutter App',
            // theme: ThemeData(
            //     primarySwatch: Colors.deepPurple,
            // ),
            theme: ThemeData.dark().copyWith(
                primaryColor: Colors.deepPurple,
            ),
            home: MyHomePage(
                title: 'Home Page'
            ),
        );
    }
}
