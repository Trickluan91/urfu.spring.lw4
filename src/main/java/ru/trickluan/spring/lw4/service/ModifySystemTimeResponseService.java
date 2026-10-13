package ru.trickluan.spring.lw4.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.trickluan.spring.lw4.model.Response;
import ru.trickluan.spring.lw4.util.DateTimeUtil;

import java.util.Date;

@Slf4j
@Service
@Qualifier("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService implements ModifyResponseService {
    @Override
    public Response modify(Response response) {
        response.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
        log.info("Modify system time response {}", response);
        return response;
    }
}
