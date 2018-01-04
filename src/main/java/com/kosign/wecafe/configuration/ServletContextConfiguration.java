package com.kosign.wecafe.configuration;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.kosign.wecafe.controller")
public class ServletContextConfiguration extends WebMvcConfigurerAdapter {

	@Inject
	ObjectMapper objectMapper;
	@Inject
	Marshaller marshaller;
	@Inject
	Unmarshaller unmarshaller;

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/jsp/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public RequestToViewNameTranslator viewNameTranslator() {
		return new DefaultRequestToViewNameTranslator();
	}

	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new SourceHttpMessageConverter<>());
		MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
		xmlConverter.setSupportedMediaTypes(
				Arrays.asList(new MediaType("application", "xml"), new MediaType("text", "xml")));
		xmlConverter.setMarshaller(this.marshaller);
		xmlConverter.setUnmarshaller(this.unmarshaller);
		converters.add(xmlConverter);
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setSupportedMediaTypes(
				Arrays.asList(new MediaType("application", "json"), new MediaType("text", "json")));
		jsonConverter.setObjectMapper(this.objectMapper);
		converters.add(jsonConverter);
		FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
		formConverter.setCharset(Charset.forName("UTF-8"));
		converters.add(formConverter);
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new AcceptHeaderLocaleResolver();
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		mapper.registerModule(new Hibernate5Module());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
		return mapper;
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true).favorParameter(false).parameterName("mediaType").ignoreAcceptHeader(false)
				.useJaf(false).defaultContentType(MediaType.APPLICATION_XML).mediaType("xml", MediaType.APPLICATION_XML)
				.mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(new String[] { "com.kosign.wecafe.entities" });
		return marshaller;
	}

/*	@Bean(name = "multipartResolver")
	public MultipartResolver resolver() {
		return new StandardServletMultipartResolver();
	}*/
	
/*	@Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
        //multipartResolver.setMaxUploadSize(2097152);
        multipartResolver.setMaxUploadSize(5242880);
        return multipartResolver;
    }*/

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(
				new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
		return converter;
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
	}

}
