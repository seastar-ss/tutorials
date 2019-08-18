package com.graphqljava.tutorial.bookdetails;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

//import org.apache.http.auth.AUTH;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

//    @Autowired
//    private RequestMappingHandlerAdapter handlerAdapter;
    private ApplicationContext applicationContext;

//    @Autowired
//    Gson gson;

//    @EventListener
//    public void handleContextRefresh(ContextRefreshedEvent event) {
//        applicationContext = event.getApplicationContext();
//        handlerAdapter.getMessageConverters()
//                .stream()
//                .forEach(System.out::println);
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authInterceptor());
//    }

//    @Bean
//    public AuthInterceptor authInterceptor(){
//        return new AuthInterceptor();
//    }

//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//
//    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//
//    }
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//
//    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        for(HttpMessageConverter<?> converter:converters){
//            if(converter instanceof GsonHttpMessageConverter){
//
//            }
//        }
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setWriteAcceptCharset(false);  // see SPR-7316

//        converters = new ArrayList<>(4);
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(stringHttpMessageConverter);
        converters.add(new SourceHttpMessageConverter<>());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        GsonHttpMessageConverter converter=new GsonHttpMessageConverter();
        GsonExtHttpMessageConverter extConverter =new GsonExtHttpMessageConverter(gson());
        converters.add(extConverter);
    }

    public Gson gson(){
        GsonBuilder builder = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .disableHtmlEscaping();
        return builder.create();
    }
}
