package org.library.library_management.exception;

import org.springframework.http.ResponseEntity;

public class GlobalErrorHandler {
    public ResponseEntity<?>ApiException(ApiException e) {
        ErrorResponse errorResponse = new ErrorResponse(
            e.getStatus(), e.getMessage()
        );
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }

}
