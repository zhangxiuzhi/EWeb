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

import com.esteel.web.vo.offer.IronFuturesTransportVo;

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
@Constraint(validatedBy = IronFuturesTransport.IronFuturesTransportValidator.class)
public @interface IronFuturesTransport {

	String message() default "{javax.validation.constraints.Digits.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	public class IronFuturesTransportValidator implements ConstraintValidator<IronFuturesTransport, IronFuturesTransportVo> {
		
		@Override
		public void initialize(IronFuturesTransport constraintAnnotation) {
		}

		@Override
		public boolean isValid(IronFuturesTransportVo transport, ConstraintValidatorContext context) {
			if (transport == null) {
				return true;
			}
			
			if (transport.getTransport_load_start() != null && transport.getTransport_load_end() == null) {
				context.buildConstraintViolationWithTemplate("请填完 装船期。")  
                .addPropertyNode("transport_load_start")  
                .addConstraintViolation();
				
				 return false;
	        } else if (transport.getTransport_load_start() == null && transport.getTransport_load_end() != null) {
	        	context.buildConstraintViolationWithTemplate("请填完 装船期。")  
                .addPropertyNode("transport_load_start")  
                .addConstraintViolation();
	        	
	        	 return false;
	        }
			
			if (transport.getTransport_arrive_month() != null && transport.getTransport_half_month() == null) {
				context.buildConstraintViolationWithTemplate("请填完到港月。")  
                .addPropertyNode("transport_load_start")  
                .addConstraintViolation();
				
				 return false;
	        }
			
			boolean flag = false;
			if (transport.getTransport_load_start() != null && transport.getTransport_load_end() != null) {
				flag = true;
	        }
			
			if (transport.getTransport_bill() != null) {
				flag = true;
			}
			
			if (transport.getTransport_arrive_month() != null && transport.getTransport_half_month() != null) {
				flag = true;
	        } 
			
			if (transport.getTransport_etaxjb() != null) {
				flag = true;
			}
			
			if (transport.getTransport_etaqdg() != null) {
				flag = true;
			}
			
			if (transport.getTransport_remark() != null) {
				flag = true;
			}
			
			if (!flag) {
				context.buildConstraintViolationWithTemplate("运输状态：至少填一项。")  
                .addPropertyNode("transport_remark")  
                .addConstraintViolation();
				
				 return false;
			}
			
			return true;
		}
	}
}
