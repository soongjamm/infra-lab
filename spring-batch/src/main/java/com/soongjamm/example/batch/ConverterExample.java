package com.soongjamm.example.batch;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class ConverterExample {

//    @Bean
//    public ConversionService conversionService() {
//        ApplicationConversionService acs = new ApplicationConversionService();
//        acs.addConverter(new Converter<AccountNumber, String>() {
//            @Override
//            public String convert(AccountNumber accountNumber) {
//                return accountNumber.getAccountNumber();
//            }
//        });
//        return acs;
//    }
}
