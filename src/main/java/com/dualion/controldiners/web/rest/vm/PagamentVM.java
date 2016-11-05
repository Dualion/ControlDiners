package com.dualion.controldiners.web.rest.vm;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PagamentVM implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long userId;
	
	@JsonCreator
	public PagamentVM() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "PagamentVM [userId=" + userId + "]";
	}
}
