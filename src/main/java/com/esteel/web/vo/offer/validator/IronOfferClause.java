package com.esteel.web.vo.offer.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.esteel.web.vo.offer.request.IronOfferClauseRequest;

/**
 * 
 * @ClassName: IronFuturesTransport
 * @Description: 运输信息 Validator
 * @author wyf
 * @date 2017年12月18日 上午11:30:57 
 *
 */
@Target({ElementType.TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IronOfferClause.IronOfferClauseVoValidator.class)
public @interface IronOfferClause {

	String message() default "{javax.validation.constraints.Digits.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	public class IronOfferClauseVoValidator implements ConstraintValidator<IronOfferClause, IronOfferClauseRequest> {
		
		@Override
		public void initialize(IronOfferClause constraintAnnotation) {
		}

		@Override
		public boolean isValid(IronOfferClauseRequest clause, ConstraintValidatorContext context) {
			if (clause == null) {
				return true;
			}
			
			if (clause.getSettlement_method().equals("1")) {
				if (clause.getAfter_sign_several_working_days() == null 
						|| clause.getAfter_pay_off_several_working_days().trim().equals("")) {
					context.buildConstraintViolationWithTemplate("请填完 结算方式。")  
	                .addPropertyNode("after_sign_several_working_days")  
	                .addConstraintViolation();
					
					return false;
				}
				
				if (clause.getContract_funds_percentage() == null 
						|| clause.getContract_funds_percentage().trim().equals("")) {
					context.buildConstraintViolationWithTemplate("请填完 结算方式。")  
	                .addPropertyNode("after_sign_several_working_days")  
	                .addConstraintViolation();
					
					return false;
				}
				
				if (clause.getClear_within_several_working_daysArr() == null 
						|| clause.getClear_within_several_working_daysArr().length < 2
						|| clause.getClear_within_several_working_daysArr()[1] == null
						|| clause.getClear_within_several_working_daysArr()[1].trim().equals("")) {
					context.buildConstraintViolationWithTemplate("请填完 结算方式。")  
	                .addPropertyNode("after_sign_several_working_days")  
	                .addConstraintViolation();
					
					return false;
				}
			}
			
			return true;
		}
	}
}
