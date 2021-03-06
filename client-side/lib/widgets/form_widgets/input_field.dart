import 'dart:html';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class InputField extends StatelessWidget {
  final TextEditingController controller;
  final Function callback;
  final String hint;
  final bool numbersOnly;
  InputField({this.hint, this.controller, this.callback, this.numbersOnly = false});
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(4.0),
      child: TextField(
        inputFormatters: numbersOnly ? [FilteringTextInputFormatter.digitsOnly] : [],
        controller: controller,
        decoration: InputDecoration(
          enabledBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.black54),
            borderRadius: BorderRadius.circular(5),
          ),
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
