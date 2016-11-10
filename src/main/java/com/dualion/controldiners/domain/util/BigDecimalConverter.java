package com.dualion.controldiners.domain.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BigDecimalConverter implements AttributeConverter<BigDecimal, BigDecimal>{

	@Override
	public BigDecimal convertToDatabaseColumn(BigDecimal attribute) {
		return attribute.setScale(2, RoundingMode.CEILING);
	}

	@Override
	public BigDecimal convertToEntityAttribute(BigDecimal dbData) {
		return dbData.setScale(2, RoundingMode.CEILING);
	}
	
	
	
}
