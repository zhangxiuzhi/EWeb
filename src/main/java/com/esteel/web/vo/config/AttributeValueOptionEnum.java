package com.esteel.web.vo.config;

import org.springframework.beans.factory.annotation.Autowired;

import com.esteel.common.util.EsteelConstant;
import com.esteel.web.service.AutowireHelper;
import com.esteel.web.service.BaseClient;

/**
 * 
 * @ClassName: AttributeValueOptionEnum
 * @Description: 属性值选项Enum
 * @author wyf
 * @date 2017年12月5日 下午2:11:55 
 *
 */
public class AttributeValueOptionEnum {

	private static final AttributeValueOptionEnum INSTANCE = new AttributeValueOptionEnum();
	
	/**
	 * 属性值选项编码:湿吨
	 */
	public Content WMT = new Content(EsteelConstant.ATTRIBUTE_VALUE_OPTION_CODE_WMT);
	/**
	 * 属性值选项编码:吨
	 */
	public Content MT = new Content(EsteelConstant.ATTRIBUTE_VALUE_OPTION_CODE_MT);
	/**
	 * 属性值选项编码:干吨
	 */
	public Content DMT = new Content(EsteelConstant.ATTRIBUTE_VALUE_OPTION_CODE_DMT);
	/**
	 * 属性值选项编码:人民币
	 */
	public Content CNY = new Content(EsteelConstant.ATTRIBUTE_VALUE_OPTION_CODE_CNY);
	/**
	 * 属性值选项编码:美元
	 */
	public Content USD = new Content(EsteelConstant.ATTRIBUTE_VALUE_OPTION_CODE_USD);
	/**
	 * 属性值选项编码:人民币/湿吨
	 */
	public Content CNY_WMT = new Content(EsteelConstant.ATTRIBUTE_VALUE_OPTION_CODE_CNY_WMT);
	/**
	 * 属性值选项编码:美元/干吨
	 */
	public Content USD_DMT = new Content(EsteelConstant.ATTRIBUTE_VALUE_OPTION_CODE_USD_DMT);
	/**
	 * 属性值选项编码:人民币/吨
	 */
	public Content CNY_MT = new Content(EsteelConstant.ATTRIBUTE_VALUE_OPTION_CODE_CNY_MT);
	
	@Autowired
	private BaseClient baseClient;

	public static AttributeValueOptionEnum getInstance() {
		return INSTANCE;
	}

	private AttributeValueOptionEnum() {
		if (baseClient == null) {
			AutowireHelper.autowire(this, baseClient);
		}
	}
	
	public class Content {
		private long id = -1;
		private String code;
		private String value;
		private String valueEn;
		private AttributeValueOptionVo optionVo;
		
		public Content(String code) {
			this.code = code;
		}

		public long getId() {
			if (id == -1) {
				AttributeValueOptionVo queryVo = new AttributeValueOptionVo();
				queryVo.setOptionCode(code);
				
				AttributeValueOptionVo vo = baseClient.getAttributeValueOption(queryVo);
				if (vo == null) {
					vo = new AttributeValueOptionVo();
					vo.setOptionId(-1);
				}

				setId(vo.getOptionId());
				setValue(vo.getOptionValue());
				setValueEn(vo.getOptionValueEn());
				setOptionVo(vo);
			}
			
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getValueEn() {
			return valueEn;
		}

		public void setValueEn(String valueEn) {
			this.valueEn = valueEn;
		}

		public AttributeValueOptionVo getOptionVo() {
			if (optionVo == null) {
				AttributeValueOptionVo queryVo = new AttributeValueOptionVo();
				queryVo.setOptionCode(code);
				
				AttributeValueOptionVo vo = baseClient.getAttributeValueOption(queryVo);
				if (vo == null) {
					vo = new AttributeValueOptionVo();
					vo.setOptionId(-1);
				}

				setId(vo.getOptionId());
				setValue(vo.getOptionValue());
				setValueEn(vo.getOptionValueEn());
				setOptionVo(vo);
			}
			
			return optionVo;
		}

		public void setOptionVo(AttributeValueOptionVo optionVo) {
			this.optionVo = optionVo;
		}
	}
}
