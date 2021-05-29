import 'package:myapp/models/job.dart';

import 'item.dart';

class Part extends Item {
  Part() : super();
  Part.fromParams(_id, _space, _type, _name, _active, _createdTimestamp, _createdBy, _lat, _lng, attributes)
      : super.fromParams(_id, _space, _type, _name, _active, _createdTimestamp, _createdBy, _lat, _lng, attributes);

  Part.fromJSON(json) : super.fromJSON(json);

  List<Job> _userIn = [];

  @override
  String convertToJson() {
    return super.convertToJson();
  }

  double get price => this.itemAttributes["price"];
  get usedIn => this._userIn;

  set price(double value) => this.itemAttributes["price"] = value;
  set usedIn(List<Job> jobs) => this._userIn = List.from(jobs);
}
