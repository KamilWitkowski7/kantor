package pl.kw.app.account.infrastructure.secondary.currency;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import pl.kw.app.account.boundary.secondary.NbpIntegration;
import pl.kw.app.account.boundary.secondary.dto.Currency;
import pl.kw.app.account.core.application.properties.AccountProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NbpJsonIntegration implements NbpIntegration {
    private final AccountProperties accountProperties;
    private final RestTemplate restTemplate;

    public ResponseEntity<?> getCurrency() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        try {
            return restTemplate.getForEntity(accountProperties.getNbpUri(), Currency.class, entity);
        } catch (HttpClientErrorException httpClientErrorException) {
            log.error(httpClientErrorException.getMessage());
            return new ResponseEntity<>(httpClientErrorException.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (ResourceAccessException resourceAccessException) {
            log.error("Request timeout occured: ", resourceAccessException);
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        } catch (Exception exception) {
            log.error("Exception occured: ", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
