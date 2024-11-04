package rest.decrypto;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamLambdaHandler implements RequestStreamHandler {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    private static final Logger logger = LoggerFactory.getLogger(StreamLambdaHandler.class);

    static {
        try {
            logger.info("Inicializando el handler de Lambda");
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);

            // Configuración adicional para Swagger
            handler.onStartup(servletContext -> {
                logger.info("Configurando recursos de Swagger");
                servletContext.setInitParameter("swagger.api.baseUrl", "/");
                servletContext.setInitParameter("swagger.api.title", "Tu API");
            });

            logger.info("Handler inicializado correctamente");
        } catch (ContainerInitializationException e) {
            logger.error("No se pudo inicializar la aplicación Spring Boot", e);
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        handler.proxyStream(inputStream, outputStream, context);
    }
}