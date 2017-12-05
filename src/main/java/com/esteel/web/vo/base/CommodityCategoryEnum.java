package com.esteel.web.vo.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.esteel.common.util.EsteelConstant;
import com.esteel.web.service.BaseClient;

/**
 * 
 * @ClassName: CommodityCategoryEnum
 * @Description: 品名大类Enum
 * @author wyf
 * @date 2017年12月5日 下午2:09:11 
 *
 */
public class CommodityCategoryEnum {

	private static final CommodityCategoryEnum INSTANCE = new CommodityCategoryEnum();
	
	/**
	 * 品名大类编码:钢材
	 */
	public Content STEEL = new Content(EsteelConstant.COMMODITY_CATEGORY_CODE_STEEL);
	/**
	 * 品名大类编码:铁矿
	 */
	public Content IRON = new Content(EsteelConstant.COMMODITY_CATEGORY_CODE_IRON);
	/**
	 * 品名大类编码:镍矿
	 */
	public Content NICKEL = new Content(EsteelConstant.COMMODITY_CATEGORY_CODE_NICKEL);
	/**
	 * 品名大类编码:煤焦
	 */
	public Content COAL = new Content(EsteelConstant.COMMODITY_CATEGORY_CODE_COAL);
	
	@Autowired
	BaseClient baseClient;

	public static CommodityCategoryEnum getInstance() {
		return INSTANCE;
	}

	private CommodityCategoryEnum() {
	}
	
	public class Content {
		private long id = -1;
		private String code;
		private String name;
		private String nameEn;
		private CommodityCategoryVo categoryVo;
		
		public Content(String code) {
			this.code = code;
		}

		public long getId() {
			if (id == -1) {
				CommodityCategoryVo vo = baseClient.findByCategoryCode(code);
				if (vo == null) {
					vo = new CommodityCategoryVo();
					vo.setCategoryId(-1);
				}

				setId(vo.getCategoryId());
				setName(vo.getCategoryName());
				setNameEn(vo.getCategoryNameEn());
				setCategoryVo(vo);
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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNameEn() {
			return nameEn;
		}

		public void setNameEn(String nameEn) {
			this.nameEn = nameEn;
		}

		public CommodityCategoryVo getCategoryVo() {
			if (categoryVo == null) {
				CommodityCategoryVo vo = baseClient.findByCategoryCode(code);
				if (vo == null) {
					vo = new CommodityCategoryVo();
					vo.setCategoryId(-1);
				}

				setId(vo.getCategoryId());
				setName(vo.getCategoryName());
				setNameEn(vo.getCategoryNameEn());
				setCategoryVo(vo);
			}
			
			return categoryVo;
		}

		public void setCategoryVo(CommodityCategoryVo categoryVo) {
			this.categoryVo = categoryVo;
		}

	}
}
