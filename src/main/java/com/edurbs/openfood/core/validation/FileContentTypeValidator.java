package com.edurbs.openfood.core.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> mediaType;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.mediaType = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        
        return multipartFile == null || mediaType.contains(multipartFile.getContentType());
        
    }
    
}
