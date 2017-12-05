package com.esteel.web.web;

import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esteel.web.service.BaseClient;
import com.esteel.web.service.OfferClient;
import com.esteel.web.vo.base.CommodityCategoryEnum;
import com.esteel.web.vo.base.CommodityQueryVo;
import com.esteel.web.vo.base.CommodityVo;
import com.esteel.web.vo.config.IronAttributeLinkVo;
import com.esteel.web.vo.offer.IronOfferVo;

/**
 * ESTeel
 * Description: 报盘用controller
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 13:49
 */
@RequestMapping("/offer")
@Controller
public class OfferController {
	@Autowired
	BaseClient baseClient;
	@Autowired
	OfferClient offerClient;
	
	@RequestMapping("/myOffer")
    public String myOfferUI(){
        return "/offer/myOffer";
    }

    @RequestMapping(value = "/addOffer", method = RequestMethod.GET)
    public String addOfferUI(Model model) throws JSONException {
    	List<CommodityVo> ironCommodityList = baseClient.findCommodityListByIron();
    	model.addAttribute("ironCommodityList", ironCommodityList);
    	
    	CommodityQueryVo commodityQueryVo = new CommodityQueryVo();
    	commodityQueryVo.setCategoryId(CommodityCategoryEnum.getInstance().IRON.getId());
    	
    	for (CommodityVo commodityVo : ironCommodityList) {
    		commodityQueryVo.setCommodityCode(commodityVo.getCommodityCode());
    		
    		List<IronAttributeLinkVo> ironAttributeList = baseClient.findAttributeListByIron(commodityQueryVo);
    		
    		boolean b = ironAttributeList.isEmpty();
    	}
    	
    	
        return "/offer/addOffer";
    }
    
    @RequestMapping(value = "/saveOffer", method = RequestMethod.POST)
    public String saveOffer(IronOfferVo offerVo, Model model){
    	try {
    		offerClient.saveOffer(offerVo);
    	} catch (Exception e) {
			e.printStackTrace();
			
			model.addAttribute("msg", "新增失败");
			
			model.addAttribute("offerVo", offerVo);
		    
		    return "/offer/addOffer";
		}
    	
    	model.addAttribute("msg", "新增成功");
    	
        return "redirect:/offer/myOffer";
    }
}
