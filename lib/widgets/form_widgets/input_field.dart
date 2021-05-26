import 'package:flutter/material.dart';

class InputField extends StatelessWidget {
  final TextEditingController controller;
  final Function callback;
  final String hint;
  InputField({this.hint, this.controller, this.callback});
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(4.0),
      child: TextField(
        controller: controller,
        decoration: InputDecoration(
          hintText: hint,
          contentPadding: EdgeInsets.all(4.0),
          fillColor: Colors.white,
          filled: true,
        ),
        onChanged: callback,
      ),
    );
  }
}
