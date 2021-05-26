import 'package:flutter/material.dart';

// class CustomDropdownButton extends StatelessWidget {

//   @override

// }

class CustomDropdownButton extends StatefulWidget {
  final String defaultValue;
  final Function onChange;
  final List<String> values;
  CustomDropdownButton({this.defaultValue, this.onChange, this.values});
  @override
  _CustomDropdownButtonState createState() => _CustomDropdownButtonState(defaultValue, onChange, values);
}

class _CustomDropdownButtonState extends State<CustomDropdownButton> {
  String _selectedValue;
  final Function onChange;
  final List<String> values;
  _CustomDropdownButtonState(this._selectedValue, this.onChange, this.values);

  void innerChange(value) {
    // This function will handle the innder value change and will call the callback.
    // That way, the parent wouldn't neeed to keep track of the selected value all the time
    setState(() {
      _selectedValue = value;
    });
    onChange(value);
  }

  @override
  Widget build(BuildContext context) {
    return DropdownButton<String>(
        value: _selectedValue,
        onChanged: innerChange,

        // focusNode: _secondNode,
        items: values
            .map((e) => DropdownMenuItem(
                  child: Text(e),
                  value: e,
                ))
            .toList());
  }
}
