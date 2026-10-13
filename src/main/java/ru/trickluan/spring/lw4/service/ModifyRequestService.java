package ru.trickluan.spring.lw4.service;

import org.springframework.stereotype.Service;
import ru.trickluan.spring.lw4.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}
