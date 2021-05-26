import 'Item.dart';

class Part extends Item {
  Part() : super();
  Part.fromParams(_id, _space, _type, _name, _active, _createdTimestamp, _createdBy, _lat, _lng, attributes)
      : super.fromParams(_id, _space, _type, _name, _active, _createdTimestamp, _createdBy, _lat, _lng, attributes);

  get isUsed => this.itemAttributes["isUsed"];
  get price => this.itemAttributes["price"];
  get usedIn => this.parents;

  set isUsed(value) => this.itemAttributes["isUsed"] = value;
  set price(value) => this.itemAttributes["price"] = value;
  set usedIn(value) => this.parents = value;
}
