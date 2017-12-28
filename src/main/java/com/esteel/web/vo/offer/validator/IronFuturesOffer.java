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

import org.apache.commons.lang3.math.NumberUtils;

import com.esteel.common.util.EsteelConstant;
import com.esteel.web.vo.offer.request.IronFuturesOfferRequest;

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
@Constraint(validatedBy = IronFuturesOffer.IronOfferClauseVoValidator.class)
public @interface IronFuturesOffer {

	String message() default "{javax.validation.constraints.Digits.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	public class IronOfferClauseVoValidator implements ConstraintValidator<IronFuturesOffer, IronFuturesOfferRequest> {
		
		@Override
		public void initialize(IronFuturesOffer constraintAnnotation) {
		}

		@Override
		public boolean isValid(IronFuturesOfferRequest futuresOfferRequest, ConstraintValidatorContext context) {
			if (futuresOfferRequest == null) {
				return true;
			}
			
			if (futuresOfferRequest.getFeArr() == null 
					|| futuresOfferRequest.getFeArr().length == 0) {
				context.buildConstraintViolationWithTemplate("请填写Fe指标。")  
                .addPropertyNode("feArr")  
                .addConstraintViolation();
				
				return false;
			}
			
			if (futuresOfferRequest.getIndicatorTypeIdArr() == null 
					|| futuresOfferRequest.getIndicatorTypeIdArr().length == 0) {
				context.buildConstraintViolationWithTemplate("请新选择指标。")  
                .addPropertyNode("indicatorTypeIdArr")  
                .addConstraintViolation();
				
				return false;
			}
			
			if (futuresOfferRequest.getOfferQuantityArr() == null 
					|| futuresOfferRequest.getOfferQuantityArr().length == 0) {
				context.buildConstraintViolationWithTemplate("请填写报盘数量。")  
                .addPropertyNode("offerQuantityArr")  
                .addConstraintViolation();
				
				return false;
			}
			
			if (futuresOfferRequest.getMoreOrLessArr() == null 
					|| futuresOfferRequest.getMoreOrLessArr().length == 0) {
				context.buildConstraintViolationWithTemplate("请填写溢短装。")  
                .addPropertyNode("moreOrLessArr")  
                .addConstraintViolation();
				
				return false;
			}
			
			// 价格模式 0:固定价, 1:浮动价
			boolean fixPrice = 
					NumberUtils.toInt(futuresOfferRequest.getPriceModel()) == EsteelConstant.PRICE_MODEL_FIX;
			
			if (fixPrice) {
				if (futuresOfferRequest.getPriceValueArr() == null 
						|| futuresOfferRequest.getPriceValueArr().length == 0) {
					context.buildConstraintViolationWithTemplate("请填写价格数值。")  
	                .addPropertyNode("priceValueArr")  
	                .addConstraintViolation();
					
					return false;
				}
				
				if (futuresOfferRequest.getPriceBasisFeArr() == null 
						|| futuresOfferRequest.getPriceBasisFeArr().length == 0) {
					context.buildConstraintViolationWithTemplate("请填写价格基数Fe。")  
	                .addPropertyNode("priceBasisFeArr")  
	                .addConstraintViolation();
					
					return false;
				}
			} else {
				if (futuresOfferRequest.getPriceDescription() == null 
						|| futuresOfferRequest.getPriceDescription().trim().equals("")) {
					context.buildConstraintViolationWithTemplate("请填写价格描述。")  
	                .addPropertyNode("priceValueArr")  
	                .addConstraintViolation();
					
					return false;
				}
			}
			
			int length = 1;
			// 是否一船多货 0:否, 1:是
			if (NumberUtils.toInt(futuresOfferRequest.getIsMultiCargo()) == EsteelConstant.YES) {
				length = 2;
				
				if (futuresOfferRequest.getFeArr().length < length) {
					context.buildConstraintViolationWithTemplate("请填完 Fe指标。")  
	                .addPropertyNode("feArr")  
	                .addConstraintViolation();
					
					return false;
				}
				
				if (futuresOfferRequest.getIndicatorTypeIdArr().length < length) {
					context.buildConstraintViolationWithTemplate("请选完 指标。")  
	                .addPropertyNode("indicatorTypeIdArr")  
	                .addConstraintViolation();
					
					return false;
				}
				
				if (futuresOfferRequest.getOfferQuantityArr().length < length) {
					context.buildConstraintViolationWithTemplate("请填完 报盘数量。")  
	                .addPropertyNode("offerQuantityArr")  
	                .addConstraintViolation();
					
					return false;
				}
				
				if (futuresOfferRequest.getMoreOrLessArr().length < length) {
					context.buildConstraintViolationWithTemplate("请填完 溢短装。")  
	                .addPropertyNode("moreOrLessArr")  
	                .addConstraintViolation();
					
					return false;
				}
				
				if (fixPrice) {
					if (futuresOfferRequest.getPriceValueArr().length < length) {
						context.buildConstraintViolationWithTemplate("请填完 价格数值。")  
		                .addPropertyNode("priceValueArr")  
		                .addConstraintViolation();
						
						return false;
					}
					
					if (futuresOfferRequest.getPriceBasisFeArr().length < length) {
						context.buildConstraintViolationWithTemplate("请填完 价格基数Fe。")  
		                .addPropertyNode("priceBasisFeArr")  
		                .addConstraintViolation();
						
						return false;
					}
				}
			}
			
			for (int i = 0; i < length; i ++) {
				if (futuresOfferRequest.getFeArr()[i] == null 
						|| futuresOfferRequest.getFeArr()[i].trim().equals("")) {
					context.buildConstraintViolationWithTemplate("请填写商品"+ (i + 1) +"Fe指标。")  
	                .addPropertyNode("feArr")  
	                .addConstraintViolation();
					
					return false;
				}
				
				if (futuresOfferRequest.getIndicatorTypeIdArr()[i] == null 
						|| futuresOfferRequest.getIndicatorTypeIdArr()[i].trim().equals("")) {
					context.buildConstraintViolationWithTemplate("请新选择商品"+ (i + 1) +"指标。")  
	                .addPropertyNode("indicatorTypeIdArr")  
	                .addConstraintViolation();
					
					return false;
				}
				
				if (futuresOfferRequest.getOfferQuantityArr()[i] == null 
						|| futuresOfferRequest.getOfferQuantityArr()[i].trim().equals("")) {
					context.buildConstraintViolationWithTemplate("请填写商品"+ (i + 1) +"报盘数量。")  
	                .addPropertyNode("offerQuantityArr")  
	                .addConstraintViolation();
					
					return false;
				}
				
				if (futuresOfferRequest.getMoreOrLessArr()[i] == null 
						|| futuresOfferRequest.getMoreOrLessArr()[i].trim().equals("")) {
					context.buildConstraintViolationWithTemplate("请填写商品"+ (i + 1) +"溢短装。")  
	                .addPropertyNode("moreOrLessArr")  
	                .addConstraintViolation();
					
					return false;
				}
				
				if (fixPrice) {
					if (futuresOfferRequest.getPriceValueArr()[i] == null 
							|| futuresOfferRequest.getMoreOrLessArr()[i].trim().equals("")) {
						context.buildConstraintViolationWithTemplate("请填写商品"+ (i + 1) +"价格数值。")  
		                .addPropertyNode("priceValueArr")  
		                .addConstraintViolation();
						
						return false;
					}
					
					if (futuresOfferRequest.getPriceBasisFeArr()[i] == null 
							|| futuresOfferRequest.getMoreOrLessArr()[i].trim().equals("")) {
						context.buildConstraintViolationWithTemplate("请填写商品"+ (i + 1) +" 价格基数Fe。")  
		                .addPropertyNode("priceBasisFeArr")  
		                .addConstraintViolation();
						
						return false;
					}
				}
			}
			
			// 是否在保税区 0:否, 1:是
	    	boolean isBondedArea = NumberUtils.toInt(futuresOfferRequest.getIsBondedArea()) == EsteelConstant.YES;
	    	if (!isBondedArea) {
	    		if (futuresOfferRequest.getPriceTerm() == null 
						|| futuresOfferRequest.getPriceTerm().trim().equals("")) {
					context.buildConstraintViolationWithTemplate("请填写价格术语。")  
	                .addPropertyNode("priceTerm")  
	                .addConstraintViolation();
					
					return false;
				}
	    		
	    		if (futuresOfferRequest.getPriceTermPortId() == null 
						|| futuresOfferRequest.getPriceTermPortId().trim().equals("")) {
					context.buildConstraintViolationWithTemplate("请选择价格术语 港口。")  
	                .addPropertyNode("priceTermPortId")  
	                .addConstraintViolation();
					
					return false;
				}
	    	}
			
			return true;
		}
	}
}
