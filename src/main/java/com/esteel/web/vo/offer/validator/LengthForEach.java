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
 * @ClassName: Number
 * @Description: Validator 允许空字符串, 遍历List Array
 * @author wyf
 * @date 2017年12月15日 下午4:01:46 
 *
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = LengthForEach.NumberValidator.class)
public @interface LengthForEach {

	String message() default "{javax.validation.constraints.Digits.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	int min() default 0;

	int max() default Integer.MAX_VALUE;
	
	public class NumberValidator implements ConstraintValidator<LengthForEach, Object> {
		
	    private int i = 0;
	    private int x = 0;

		@Override
		public void initialize(LengthForEach constraintAnnotation) {
			
			i = constraintAnnotation.min();
			if (i < 0) {
				i = 0;
			}
			x = constraintAnnotation.max();
			if (x < 0) {
				x = 0;
			}
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
			if (obj == null) {
				return true;
			}

			String value = obj.toString();

			if (value.trim().equals("")) {
				return true;
			}

			int length = value.trim().length();
			
			if (length < i) {
				return false;
			}
			
			if (length > x) {
				return false;
			}

			return true;
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
