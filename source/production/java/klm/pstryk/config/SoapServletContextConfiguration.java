package klm.pstryk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

@Configuration
@ComponentScan(
        basePackages = "klm.pstryk.site",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(Endpoint.class)
)
@ImportResource("classpath:klm/pstryk/config/soapServletContext.xml")
public class SoapServletContextConfiguration
{
    @Bean
    public WebServiceMessageFactory messageFactory()
    {
        SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
        factory.setSoapVersion(SoapVersion.SOAP_12);
        return factory;
    }
}
