package com.soongjamm.example.batch;

import org.springframework.beans.BeanWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
public class ABCD {

    @Bean
    public BeanPropertyRowMapper<AccountNumber> accountNumberBeanPropertyRowMapper() {
        return new BeanPropertyRowMapper<AccountNumber>(AccountNumber.class) {
            @Override
            protected void initBeanWrapper(BeanWrapper bw) {
                super.initBeanWrapper(bw);
                bw.setConversionService(new ConversionService() {
                    @Override
                    public boolean canConvert(Class<?> aClass, Class<?> aClass2) {
                        return aClass == AccountNumber.class && aClass2 == String.class;
                    }

                    @Override
                    public boolean canConvert(TypeDescriptor typeDescriptor, TypeDescriptor typeDescriptor2) {
                        return canConvert(typeDescriptor.getType(), typeDescriptor2.getType());
                    }

                    @Override
                    public <T> T convert(Object o, Class<T> tClass) {
                        if (o instanceof AccountNumber && tClass == String.class) {
                            return (T) o.toString();
                        }

                        return null;
                    }

                    @Override
                    public Object convert(Object o, TypeDescriptor typeDescriptor, TypeDescriptor typeDescriptor2) {
                        return convert(o, typeDescriptor2.getType());
                    }
                });
            }

        };

    }
}