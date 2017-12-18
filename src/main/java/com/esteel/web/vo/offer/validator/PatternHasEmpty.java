package com.esteel.web.vo.offer.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * 
 * @ClassName: PatternHasEmpty
 * @Description: Validator 允许空字符串, 遍历List Array
 * @author wyf
 * @date 2017年12月15日 下午4:00:51 
 *
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PatternHasEmpty.VoPatternValidator.class)
public @interface PatternHasEmpty {

	String message() default "{javax.validation.constraints.Pattern.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * @return the regular expression to match
	 */
	String regexp() default "";

	public class VoPatternValidator implements ConstraintValidator<PatternHasEmpty, Object> {
		
	    private String r = "";

		@Override
		public void initialize(PatternHasEmpty constraintAnnotation) {
			
			r = constraintAnnotation.regexp();
		}

		@Override
		public boolean isValid(Object obj, ConstraintValidatorContext context) {
			if (obj == null) {
				return true;
			}
			
			if (obj instanceof List) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) obj;
				
				return isValidList(list);
			} else if (obj instanceof Object[]) {
				Object[] array = (Object[]) obj;
				
				return isValidArray(array);
			}
			
			return isValidOne(obj);
		}
		
		private boolean isValidOne(Object obj) {
			if(obj == null) {
				return true;
			}
			
			String value = obj.toString();
			
			if (value.trim().equals("")) {
				return true;
			}

			try {
				value = value.trim();
				
				return value.matches(r);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return false;
		}
		
		private boolean isValidList(List<Object> list) {
			if(list == null) {
				return true;
			}
			
			if (list.size() == 0) {
				return true;
			}
			
			for (Object obj : list) {
				if (!isValidOne(obj)) {
					return false;
				}
			}
			
			return true;
		}
		
		private boolean isValidArray(Object[] array) {
			if(array == null) {
				return true;
			}
			
			if (array.length == 0) {
				return true;
			}
			
			for (int i = 0; i < array.length; i ++) {
				if (!isValidOne(array[i])) {
					return false;
				}
			}
			
			return true;
		}
	}
}
