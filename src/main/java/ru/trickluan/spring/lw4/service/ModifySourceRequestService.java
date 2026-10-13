package ru.trickluan.spring.lw4.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.trickluan.spring.lw4.model.Request;
import ru.trickluan.spring.lw4.model.Response;

@Service
public class ModifySourceRequestService implements ModifyRequestService {
    @Override
    public void modify(Request request) {
        request.setSource("postman");

        HttpEntity<Request> requestEntity = new HttpEntity<>(request);

        new RestTemplate().exchange("http://localhost:8082/feedback",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response>(){}
        );
    }
}
