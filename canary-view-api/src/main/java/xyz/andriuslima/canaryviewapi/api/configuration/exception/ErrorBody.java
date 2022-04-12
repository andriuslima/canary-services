package xyz.andriuslima.canaryviewapi.api.configuration.exception;

import lombok.Data;

@Data
public class ErrorBody {
    private int status;
    private String message;

    public static ErrorBody from(String message, int status) {
        var body = new ErrorBody();
        body.message = message;
        body.status = status;
        return body;
    }

}

