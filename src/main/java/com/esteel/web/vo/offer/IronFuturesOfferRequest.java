package com.esteel.web.vo.offer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @ClassName: IronFuturesOfferVo
 * @Description: 铁矿远期现货报盘DTO
 * @author wyf
 * @date 2017年12月6日 下午1:28:25 
 *
 */
public class IronFuturesOfferRequest extends IronOfferBaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 铁矿报盘附表ID
	 */
	private long[] offerAttachId;
	/**
	 * 化学元素指标 Al2O3
	 */
	private BigDecimal[] al2o3;
	/**
	 * 其他化学元素指标 Json数据
	 */
	private String[] chemicalIndicators;
	/**
	 * 品名ID
	 */
	private long[] commodityId;
	/**
	 * 品名名称
	 */
	private String[] commodityName;
	/**
	 * 目的港ID
	 */
	private long dischargePortId;
	/**
	 * 目的港
	 */
	private String dischargePortName;
	/**
	 * 化学元素指标 Fe
	 */
	private BigDecimal[] fe;
	/**
	 * 化学元素指标 H2O
	 */
	private BigDecimal[] h2o;
	/**
	 * 指标类型ID
	 */
	private long[] indicatorTypeId;
	/**
	 * 指标类型
	 */
	private String[] indicatorTypeName;
	/**
	 * 是否在保税区 0:否, 1:是
	 */
	private int isBondedArea = 0;
	/**
	 * 信用证交单期
	 */
	private Date lcTime;
	/**
	 * 装货港ID
	 */
	private long loadingPortId;
	/**
	 * 装货港
	 */
	private String loadingPortName;
	/**
	 * 化学元素指标 LOI
	 */
	private BigDecimal[] LOI;
	/**
	 * 起订量
	 */
	private BigDecimal minQuantity;
	/**
	 * 化学元素指标 Mn
	 */
	private BigDecimal[] mn;
	/**
	 * 溢短装
	 */
	private BigDecimal[] moreOrLess;
	/**
	 * 铁矿报盘附表编码
	 */
	private String[] offerAttachCode;
	/**
	 * 铁矿报盘ID
	 */
	private long offerId;
	/**
	 * 报盘重量
	 */
	private BigDecimal[] offerQuantity;
	/**
	 *  化学元素指标 P
	 */
	private BigDecimal[] p;
	/**
	 * 保税区港口ID
	 */
	private long portId;
	/**
	 * 保税区港口
	 */
	private String portName;
	/**
	 * 价格基数 铁
	 */
	private BigDecimal[] priceBasisFe;
	/**
	 * 价格描述
	 */
	private String []priceDescription;
	/**
	 * 价格模式 0:固定价, 1:浮动价
	 */
	private int[] priceModel = new int[]{1, 1};
	/**
	 * 价格术语
	 */
	private String priceTerm1;
	/**
	 * 价格术语基于港ID
	 */
	private long[] priceTermPortId;
	/**
	 * 价格术语基于港
	 */
	private String[] priceTermPortName;
	/**
	 * 价格单位ID
	 */
	private long priceUnitId;
	/**
	 * 价格数值
	 */
	private BigDecimal[] priceValue;
	/**
	 * 重量单位ID
	 */
	private long quantityUnitId;
	/**
	 * 备注
	 */
	private String offerAttachRemarks;
	/**
	 * 化学元素指标 S
	 */
	private BigDecimal[] s;
	/**
	 * 化学元素指标 SiO2
	 */
	private BigDecimal[] sio2;
	/**
	 * 粒度指标
	 */
	private String[] sizeIndicators;
	/**
	 * 已售重量
	 */
	private BigDecimal soldQuantity;
	/**
	 * 运输状态 Json数据
	 */
	private String transportDescription;
	
/*	public IronFuturesOfferVo getOneCargoOfferVo(int index) {
		index = index > 1 ? 1 : index;
		
		IronFuturesOfferVo one = new IronFuturesOfferVo();
		BeanUtils.copyProperties(this, one);
		
		// 货物字段
		Set<String> cargoAttrSet = new HashSet<>();
		cargoAttrSet.addAll(
				Arrays.asList("al2o3", "chemicalIndicators", "commodityIdStr", "commodityName", "fe", "h2o",
						"indicatorTypeIdStr", "indicatorTypeName", "LOI", "mn", "moreOrLess", "offerQuantityStr",
						"p", "priceDescription", "priceModel", "priceTerm", "priceTermPortId", "priceTermPortName", 
						"priceValue", "s", "sio2", "", "sizeIndicators"));
		
		Field[] fields = one.getClass().getFields();
		for (int j = 0; j < fields.length; j++) {
			Field f = fields[j];
			f.setAccessible(true); // 设置些属性是可以访问的
			
			if (cargoAttrSet.contains(f.getName())) {
				try {
					String value = (String) f.get(this);
					
					f.set(one, f.get(f.getName()));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			}
		}
		
		
		return one;
	}*/
}
