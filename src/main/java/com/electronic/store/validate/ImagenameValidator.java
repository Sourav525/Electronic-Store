package com.electronic.store.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImagenameValidator implements ConstraintValidator<ImageNameValid,String> {



    private Logger logger = LoggerFactory.getLogger(ImagenameValidator.class);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("message from isValid:{}",value);


        //logic
        if(value.isBlank()){
            return false;
        }
        else {
            return true;
        }
    }
}
