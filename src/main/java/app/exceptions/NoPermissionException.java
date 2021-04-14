package app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class NoPermissionException extends RuntimeException {
    private static final long serialVersionUID = -2501651730564940439L;

    public NoPermissionException() {
    }

    public NoPermissionException(String message) {
        super(message);
    }

    public NoPermissionException(Throwable cause) {
        super(cause);
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

}
