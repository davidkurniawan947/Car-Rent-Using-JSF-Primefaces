/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author louiv
 */
@FacesValidator("util.EmailValidator")
public class EmailValidator implements Validator, Serializable{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String inputEmail = (String) value;
        
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        
        Matcher m = p.matcher(inputEmail);
        
        boolean validEmail = m.matches();
        
        if (!validEmail){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email tidak valid!", "Email tidak valid!");
            throw new ValidatorException(message);
        }
    }
    
}
