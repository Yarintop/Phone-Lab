import 'Item.dart';

class Part extends Item {
  Part() : super();
  Part.fromParams(_id, _space, _type, _name, _active, _createdTimestamp, _createdBy, _lat, _lng, attributes)
      : super.fromParams(_id, _space, _type, _name, _active, _createdTimestamp, _createdBy, _lat, _lng, attributes);

  get isUsed => this.itemAttributes["isUsed"];
  get price => this.itemAttributes["price"];
  get usedIn => this.parents;
  get quality => this.itemAttributes["quality"];

  set isUsed(value) => this.itemAttributes["isUsed"] = value;
  set quality(value) => this.itemAttributes["quality"] = value;
  set price(value) => this.itemAttributes["price"] = value;
  set usedIn(value) => this.parents = value;
}
