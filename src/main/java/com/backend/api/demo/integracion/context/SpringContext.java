package com.backend.api.demo.integracion.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext ac) {
		context = ac;
	}

	public static <T> T getBean(Class<T> beanClass) {
		return context.getBean(beanClass);
	}
}