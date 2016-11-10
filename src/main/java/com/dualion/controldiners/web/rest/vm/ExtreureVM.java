package com.dualion.controldiners.web.rest.vm;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ExtreureVM implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	BigDecimal diners;

	@JsonCreator
	public ExtreureVM() {
	}

	public BigDecimal getDiners() {
		return diners;
	}

	public void setDiners(BigDecimal diners) {
		this.diners = diners;
	}

	@Override
	public String toString() {
		return "ExtreureVM [diners=" + diners + "]";
	}
}
