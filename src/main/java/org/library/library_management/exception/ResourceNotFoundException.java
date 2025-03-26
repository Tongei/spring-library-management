package org.library.library_management.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(long id, String resource) {
        super(HttpStatus.NOT_FOUND, String.format("Resource %s with id %d not founded!", resource, id ));
    }
}
