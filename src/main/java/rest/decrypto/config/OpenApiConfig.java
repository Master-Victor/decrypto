package rest.decrypto.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration //Descomentar para desplegar en AWS
/* @Profile("aws") No toma el perfil */
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("Decrypto API")
                        .version("1.0")
                        .description("API Documentation for Decrypto"));

        // Configura el servidor
        String serverUrl = "https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev";

        openAPI.addServersItem(new Server().url(serverUrl).description("Server"));
        return openAPI;
    }
}