import 'dart:convert';

import 'package:myapp/constants/job_specific.dart';
import 'package:myapp/models/part.dart';

import 'item.dart';

class Job extends Item {
  bool _dirty = false;
  String _draftFixDescription;
  String _draftTechnician;
  Part _draftNewPart;
  Progress _draftStatus;
  List<Part> _children = [];

  Job() : super();
  Job.fromParams(_id, _space, _type, _name, _active, _createdTimestamp, _createdBy, _lat, _lng, attributes)
      : super.fromParams(_id, _space, _type, _name, _active, _createdTimestamp, _createdBy, _lat, _lng, attributes) {
    String tmp = attributes["status"];
    Progress status = Progress.values.firstWhere((p) => p.value == tmp);
    this.itemAttributes["status"] = status;
    this._draftStatus = status;
    this._draftFixDescription = attributes["fixDescription"];
    this._draftTechnician = attributes["assignedTechnician"];
  }

  Job.fromJSON(Map<String, dynamic> json) : super.fromJSON(json) {
    String tmp = json["itemAttributes"]["status"];
    Progress status = Progress.values.firstWhere((p) => p.value == tmp);
    this.itemAttributes["status"] = status;
    this._draftStatus = status;
    this._draftFixDescription = json["itemAttributes"]["fixDescription"];
    this._draftTechnician = json["itemAttributes"]["assignedTechnician"];
  }

  factory Job.createFromJSON(Map<String, dynamic> json) {
    return new Job.fromJSON(json);
  }

  void addPart(Part part) {
    //TODO - the class that calls this function, should make an API call to bind also
    this._children.add(part);
  }

  @override
  Map<String, dynamic> toJson() {
    return super.toJson();
  }

  /// This function is a quick workaround to the toJson function that produces an error
  String convertToJson() {
    //Convert Progress to string
    Map tmpMap = Map.from(itemAttributes);
    tmpMap["status"] = status.value;
    // return json as a string
    return jsonEncode(
      {
        "type": type,
        "name": name,
        "active": active,
        "createdTimestamp": createdTimestamp.toIso8601String(),
        "createdBy": {
          "userId": {"space": createdBy.space, "email": createdBy.email}
        },
        "itemAttributes": tmpMap,
        "location": {
          "lat": lat,
          "lng": lng,
        }
      },
    );
  }

  // GETTERS
  bool get dirty => this._dirty;
  get draftTechnician => this._draftTechnician;
  Progress get draftStatus => this._draftStatus;
  get draftFixDescription => this._draftFixDescription;
  Part get draftNewPart => this._draftNewPart;

  get customer => this.itemAttributes["customer"];
  get phoneNumber => this.itemAttributes["phoneNumber"];
  get phoneModel => this.itemAttributes["phoneModel"];
  get jobDescription => this.itemAttributes["description"];
  get fixDescription => this.itemAttributes["fixDescription"];
  Progress get status => this.itemAttributes["status"];
  get assignedTechnician => this.itemAttributes["assignedTechnician"];
  List<Part> get partsUsed => this._children;

  // SETTERS
  set dirty(bool value) => this._dirty = value;
  set draftFixDescription(value) => {this._draftFixDescription = value, this.dirty = true};
  set draftTechnician(value) => {this._draftTechnician = value, this.dirty = true};
  set draftStatus(Progress value) => {this._draftStatus = value, this.dirty = true};
  set draftNewPart(Part value) => {this._draftNewPart = value, this.dirty = true};

  set fixDescription(value) => {this.itemAttributes["fixDescription"] = value, this.draftFixDescription = value};
  set assignedTechnician(value) => {this.itemAttributes["assignedTechnician"] = value, this.draftTechnician = value};
  set status(Progress value) => {this.itemAttributes["status"] = value, this.draftStatus = value};

  set customer(value) => this.itemAttributes["customer"] = value;
  set phoneNumber(value) => this.itemAttributes["phoneNumber"] = value;
  set phoneModel(value) => this.itemAttributes["phoneModel"] = value;
  set jobDescription(value) => this.itemAttributes["description"] = value;
  set partsUsed(List<Part> parts) => this._children = List.from(parts);
}
