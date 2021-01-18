package com.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class RecursoCriadoEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private HttpServletResponse response;
	
	private Long codigo;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getCodigo() {
		return codigo;
	}

}