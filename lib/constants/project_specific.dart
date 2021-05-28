import 'package:myapp/constants/user_specific.dart';
import 'package:myapp/models/user.dart';

const SPACE = "2021b.noam.levi1";

const DEFAULT_EMAIL_ADMIN = "admin@admin.com";
const DEFAULT_EMAIL_MANAGER = "manager@manager.com";
const DEFAULT_EMAIL_PLAYER = "player@player.com";

final User defaultAdmin = User.fromParams(
  SPACE,
  "----",
  DEFAULT_EMAIL_ADMIN,
  "----",
  Role.ADMIN.value,
);
final User defaultManager = User.fromParams(
  SPACE,
  "----",
  DEFAULT_EMAIL_MANAGER,
  "----",
  Role.MANAGER.value,
);
final User defaultPlayer = User.fromParams(
  SPACE,
  "----",
  DEFAULT_EMAIL_PLAYER,
  "----",
  Role.PLAYER.value,
);

//"http://localhost:8010/twins/...";
const BASE_API = "twins";
const USER_API = "users";
const ITEM_API = "items";
const OPERATION_API = "operations";
const ADMIN_API = "admin";
