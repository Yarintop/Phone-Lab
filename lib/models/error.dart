class ErrorResult {
  bool _isError = false;
  String _errorMessage;

  bool get isError => this._isError;

  set isError(bool value) => this._isError = value;

  get errorMessage => this._errorMessage;

  set errorMessage(value) => this._errorMessage = value;
}
