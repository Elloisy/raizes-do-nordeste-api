package com.raizes.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Campos obrigatórios ausentes ou inválidos (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ApiErrorResponse.FieldIssue> details = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ApiErrorResponse.FieldIssue(err.getField(), err.getDefaultMessage()))
                .toList();

        ApiErrorResponse body = new ApiErrorResponse(
                "ERRO_VALIDACAO",
                "Um ou mais campos estão inválidos.",
                details,
                Instant.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 400 - JSON errado ou Enum inválido (ex.: canalPedido inexistente)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidJson(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        ApiErrorResponse body = new ApiErrorResponse(
                "REQUISICAO_INVALIDA",
                "Formato de JSON ou valor de campo inválido.",
                List.of(),
                Instant.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 404 / 409 / 422 - Exceções lançadas com ResponseStatusException nos Services
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatus(
            ResponseStatusException ex, HttpServletRequest request) {

        String msg = ex.getReason() != null ? ex.getReason() : "Erro na requisição.";
        String errorCode = switch (ex.getStatusCode().value()) {
            case 404 -> "RECURSO_NAO_ENCONTRADO";
            case 409 -> "ESTOQUE_INSUFICIENTE";
            case 422 -> "PAGAMENTO_RECUSADO";
            default -> "ERRO_REGRA_NEGOCIO";
        };

        ApiErrorResponse body = new ApiErrorResponse(
                errorCode,
                msg,
                List.of(),
                Instant.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    // Fallback para outras RuntimeExceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleBusiness(
            RuntimeException ex, HttpServletRequest request) {

        String msg = ex.getMessage() != null ? ex.getMessage() : "Erro interno na operação.";
        String msgLower = msg.toLowerCase();

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorCode = "ERRO_REGRA_NEGOCIO";

        if (msgLower.contains("não encontrad") || msgLower.contains("nao encontrad")) {
            status = HttpStatus.NOT_FOUND;
            errorCode = "RECURSO_NAO_ENCONTRADO";
        } else if (msgLower.contains("estoque")) {
            status = HttpStatus.CONFLICT;
            errorCode = "ESTOQUE_INSUFICIENTE";
        } else if (msgLower.contains("recusado")) {
            status = HttpStatus.UNPROCESSABLE_CONTENT;
            errorCode = "PAGAMENTO_RECUSADO";
        }

        ApiErrorResponse body = new ApiErrorResponse(
                errorCode,
                msg,
                List.of(),
                Instant.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(body);
    }
}