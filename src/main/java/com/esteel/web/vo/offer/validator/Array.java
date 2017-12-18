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

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * 
 * @ClassName: Number
 * @Description: 允许空字符串
 * @author wyf
 * @date 2017年12月15日 下午4:01:46 
 *
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = Array.ArrayValidator.class)
public @interface Array {

	String message() default "{javax.validation.constraints.Digits.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	/**
	 * @return size the element must be higher or equal to
	 */
	int min() default 0;

	public class ArrayValidator implements ConstraintValidator<Array, Object[]> {
		
		private int s = 0;
		
		@Override
		public void initialize(Array constraintAnnotation) {
			
			s = constraintAnnotation.min();
			if (s < 0) {
				s = 0;
			}
		}

		@Override
		public boolean isValid(Object[] valueArr, ConstraintValidatorContext context) {
			if (s > 0) {
				if(valueArr == null || valueArr.length == 0) {
					return false;
				}
			} else {
				if(valueArr == null || valueArr.length == 0) {
					return true;
				}
			}
			
			int count = 0;
			for (int index = 0; index < valueArr.length; index ++) {
				Object obj = valueArr[index];
				if (obj == null) {
					continue;
				}
				
				String value = obj.toString();
				if (value.trim().equals("")) {
					continue;
				}
				
				count ++;
			}
			
			return count != 0;
		}
	}
}
