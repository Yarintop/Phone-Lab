class User {
  String _id = "";
  String get id => _id;
  set id(String id) => _id = id;

  String _name = "";
  String _email = "";
  String _role = "Player";
  String _avatar = "";

  String get role => _role;
  String get email => _email;
  String get name => _name;
  String get avatar => _avatar;

  set role(String role) => _role = role;
  set email(String email) => _email = email;
  set name(String name) => _name = name;
  set avatar(String avatar) => _avatar = avatar;
}
