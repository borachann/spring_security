package com.kosign.wecafe.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class WeCafeSecurityBootstrap extends AbstractSecurityWebApplicationInitializer{

	@Override
	protected boolean enableHttpSessionEventPublisher() {
		System.out.println("Executing security bootstrap.");
		return true;
	}
}