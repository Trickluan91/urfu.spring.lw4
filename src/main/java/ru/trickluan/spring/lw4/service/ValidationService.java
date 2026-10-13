package ru.trickluan.spring.lw4.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.trickluan.spring.lw4.exception.UnsupportedCodeException;
import ru.trickluan.spring.lw4.exception.ValidationFailedException;
import ru.trickluan.spring.lw4.model.Request;

@Service
public interface ValidationService {
    void isValid(Request request, BindingResult bindingResult) throws ValidationFailedException, UnsupportedCodeException;
}
