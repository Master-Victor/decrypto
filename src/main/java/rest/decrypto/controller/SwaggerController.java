package rest.decrypto.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller Descomentar para desplegar en AWS
/* @Profile("aws") */
public class SwaggerController {
    @GetMapping("/swagger-ui.html")
    public ResponseEntity<Resource> getSwaggerUI() {
        Resource resource = new ClassPathResource("static/swagger-ui.html");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/html");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource);
    }
}