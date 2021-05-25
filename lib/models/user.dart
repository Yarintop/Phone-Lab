class User {
  String _id;
  String _email;
  String _username;
  String _avatar;

  String get id => _id;
  String get email => _email;
  String get username => _username;
  String get avatar => _avatar;

  set id(String id) => _id = id;
  set email(String email) => _email = email;
  set username(String username) => _username = username;
  set avatar(String avatar) => _avatar = avatar;
}
