import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:test_tool/MainProvider.dart';
import 'package:test_tool/Widgets/Controllers/OperationController.dart';

import 'package:test_tool/Widgets/ListViews/OperationList.dart';
import 'package:test_tool/Widgets/ListViews/ItemList.dart';

import 'Widgets/Controllers/ItemController.dart';
import 'Widgets/Controllers/UserController.dart';
import 'Widgets/Controllers/OperationController.dart';
import 'Widgets/ListViews/UserList.dart';

void main() {
  runApp(MultiProvider(
    providers: [ChangeNotifierProvider(create: (context) => MainModel())],
    child: MyApp(),
  ));
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Testing Tool',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MainView(title: 'Phone Repair Lab'),
    );
  }
}

class MainView extends StatelessWidget {
  final String title;
  MainView({Key? key, required this.title}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // appBar: ,
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(title),
          Container(
              margin: EdgeInsets.only(top: 16.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Container(
                      decoration: BoxDecoration(
                          border: Border.all(color: Colors.red),
                          borderRadius: BorderRadius.all(Radius.circular(5.0)),
                          color: Colors.blueGrey[100]),
                      alignment: AlignmentDirectional.center,
                      width: 300,
                      margin: EdgeInsets.all(16.0),
                      child: UsersView()),
                  Container(
                    decoration: BoxDecoration(
                        border: Border.all(color: Colors.green),
                        borderRadius: BorderRadius.all(Radius.circular(5.0)),
                        color: Colors.blueGrey[100]),
                    alignment: AlignmentDirectional.center,
                    width: 300,
                    margin: EdgeInsets.all(16.0),
                    child: ItemsView(),
                  ),
                  Container(
                    decoration: BoxDecoration(
                        border: Border.all(color: Colors.blue),
                        borderRadius: BorderRadius.all(Radius.circular(5.0)),
                        color: Colors.blueGrey[100]),
                    alignment: AlignmentDirectional.center,
                    width: 300,
                    margin: EdgeInsets.all(16.0),
                    child: OperationsView(),
                  ),
                ],
              )),
          Container(
            margin: EdgeInsets.only(top: 16.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Container(
                  decoration: BoxDecoration(
                      border: Border.all(color: Colors.black),
                      borderRadius: BorderRadius.all(Radius.circular(5.0)),
                      color: Colors.blueGrey[100]),
                  alignment: AlignmentDirectional.center,
                  width: 300,
                  padding: EdgeInsets.all(10.0),
                  margin: EdgeInsets.all(16.0),
                  child: UserController(),
                ),
                Container(
                  decoration: BoxDecoration(
                      border: Border.all(color: Colors.black),
                      borderRadius: BorderRadius.all(Radius.circular(5.0)),
                      color: Colors.blueGrey[100]),
                  alignment: AlignmentDirectional.center,
                  width: 300,
                  padding: EdgeInsets.all(10.0),
                  margin: EdgeInsets.all(16.0),
                  child: ItemController(),
                ),
                Container(
                  decoration: BoxDecoration(
                      border: Border.all(color: Colors.black),
                      borderRadius: BorderRadius.all(Radius.circular(5.0)),
                      color: Colors.blueGrey[100]),
                  alignment: AlignmentDirectional.center,
                  width: 300,
                  padding: EdgeInsets.all(10.0),
                  margin: EdgeInsets.all(16.0),
                  child: OperationController(),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
