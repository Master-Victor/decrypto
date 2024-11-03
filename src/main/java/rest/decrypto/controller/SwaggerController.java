package rest.decrypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {
    @RequestMapping("/swagger-ui")
    public String swaggerUi() {
        return "redirect:/swagger-ui/index.html";
    }
}