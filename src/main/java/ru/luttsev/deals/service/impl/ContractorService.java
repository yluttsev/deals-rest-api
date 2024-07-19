package ru.luttsev.deals.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.luttsev.deals.model.payload.dealstatus.SetMainBorrowerPayload;

import java.net.URI;

@Service
@RequiredArgsConstructor
@PropertySources(
        @PropertySource("classpath:contractor.properties")
)
public class ContractorService {

    @Value("${contractor.server.address}")
    private String contractorServiceAddress;

    @Value("${contractor.server.port}")
    private Integer contractorServicePort;

    public boolean sendMainBorrower(String contractorId, boolean isMain) {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<String> response = restTemplate.exchange(
                URI.create("http://%s:%s/contractor/main-borrower".formatted(contractorServiceAddress, contractorServicePort)),
                HttpMethod.PATCH,
                new HttpEntity<>(SetMainBorrowerPayload.builder()
                        .contractorId(contractorId)
                        .mainBorrower(isMain)
                        .build()),
                String.class
        );
        return response.getStatusCode().is2xxSuccessful();
    }

}
