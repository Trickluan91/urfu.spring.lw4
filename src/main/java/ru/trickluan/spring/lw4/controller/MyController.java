package ru.trickluan.spring.lw4.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.trickluan.spring.lw4.exception.UnsupportedCodeException;
import ru.trickluan.spring.lw4.exception.ValidationFailedException;
import ru.trickluan.spring.lw4.model.*;
import ru.trickluan.spring.lw4.service.ModifyRequestService;
import ru.trickluan.spring.lw4.service.ModifyResponseService;
import ru.trickluan.spring.lw4.service.ValidationService;
import ru.trickluan.spring.lw4.util.DateTimeUtil;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final List<ModifyRequestService> modifyRequestServices;

    @Autowired
    public MyController(@Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        ValidationService validationService, List<ModifyRequestService> modifyRequestServices) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestServices = modifyRequestServices;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult result) {
        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        try {
            validationService.isValid(request, result);
        } catch (ValidationFailedException e) {
            log.error("Validation failed: {}", e.getMessage());

            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e){
            log.error("Unsupported code: {}", e.getMessage());

            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("UnknownException: {}", e.getMessage());

            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        modifyResponseService.modify(response);
        modifyRequestServices.forEach(s -> s.modify(request));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
