package com.dualion.controldiners.web.rest.vm;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ExtreureVM implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	Float diners;

	@JsonCreator
	public ExtreureVM() {
	}

	public Float getDiners() {
		return diners;
	}

	public void setDiners(Float diners) {
		this.diners = diners;
	}

	@Override
	public String toString() {
		return "ExtreureVM [diners=" + diners + "]";
	}
}
