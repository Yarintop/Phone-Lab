import 'package:flutter/material.dart';

/// This widget is the home page of your application. It is stateful, meaning
/// that it has a State object (defined below) that contains fields that affect
/// how it looks.
///
/// This class is the configuration for the state. It holds the values (in this
/// case the title) provided by the parent (in this case the App widget) and
/// used by the build method of the State. Fields in a Widget subclass are
/// always marked "final".
class MyHomePage extends StatefulWidget
{
    final String title;
    
    MyHomePage({Key key, this.title}) : super(key: key);

    @override
    _MyHomePageState createState() =>
        _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage>
{
    int _counter = 0;
    Color _counterColor;

    void floatingActionButtonHandleOnPressed(ThemeData themeData)
    {
        setState( () =>
            _counter++
        );

        if (_counter % 5 == 0)
            _counterColor = themeData.accentColor;
        else
            _counterColor = null;

    }

    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    @override
    Widget build(BuildContext context)
    {
        return Scaffold(
            appBar: AppBar(
                title: Text(widget.title),
            ),
            body: Center(
                child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children:
                    [
                        Text(
                            'You have pushed the button this many times:',
                        ),
                        Text(
                            '$_counter',
                            style: Theme.of(context).textTheme.headline4.copyWith(color: _counterColor)
                        ),
                    ],
                ),
            ),
            floatingActionButton: FloatingActionButton(
                onPressed: () => floatingActionButtonHandleOnPressed(Theme.of(context)),
                tooltip: 'Increment',
                child: Icon(Icons.add),
            ),
        );
    }
}