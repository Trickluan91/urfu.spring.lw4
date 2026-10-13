package ru.trickluan.spring.lw4.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.trickluan.spring.lw4.exception.UnsupportedCodeException;
import ru.trickluan.spring.lw4.exception.ValidationFailedException;
import ru.trickluan.spring.lw4.model.Request;

@Slf4j
@Service
public class RequestValidationService implements ValidationService {
    @Override
    public void isValid(Request request, BindingResult bindingResult) throws ValidationFailedException, UnsupportedCodeException {
        if("123".equals(request.getUid())) {
            log.error("Validation failed, not supported uid: {}", request.getUid());
            throw new UnsupportedCodeException("Такое не позволительно!");
        }

        if(bindingResult.hasErrors()) {
            log.error("Validation failed: {}", bindingResult.getAllErrors());
            throw new ValidationFailedException(bindingResult.getFieldError().toString());
        }
    }
}
