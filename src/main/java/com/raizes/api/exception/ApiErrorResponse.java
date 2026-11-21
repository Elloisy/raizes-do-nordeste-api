package com.raizes.api.exception;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        String error,
        String message,
        List<FieldIssue> details,
        Instant timestamp,
        String path
) {
    public record FieldIssue(String field, String issue) {}
}
