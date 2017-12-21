package com.esteel.web.service;

import java.util.ArrayList;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esteel.web.vo.offer.IronOfferMainVo;
import com.esteel.web.vo.offer.IronOfferQueryVo;

import feign.hystrix.FallbackFactory;

/**
 * 
 * @ClassName: OfferClient
 * @Description: TODO
 * @author wyf
 * @date 2017年12月4日 下午2:55:06
 *
 */
@FeignClient(name = "Offer",url = "http://127.0.0.1:8880", fallbackFactory = OfferClientCallbackFactory.class, path = "offer")
//@FeignClient(name = "Offer", fallback = OfferClientCallback.class, path = "offer")
public interface OfferClient {
	@RequestMapping(value = "/saveIronOffer", method = RequestMethod.POST)
	public IronOfferMainVo saveIronOffer(@RequestBody IronOfferMainVo ironOfferVo);
	
	@RequestMapping(value = "/ironOffer", method = RequestMethod.POST)
	public IronOfferMainVo getIronOffer(@RequestBody IronOfferQueryVo queryVo);
	
	@RequestMapping(value = "/ironOfferPage", method = RequestMethod.POST)
	public Page<IronOfferMainVo> query(@RequestBody IronOfferQueryVo queryVo);
}

//@Component
//class OfferClientCallback implements OfferClient {
//
//	@Override
//	public IronOfferMainVo saveIronOffer(IronOfferMainVo ironOfferVo) {
//		IronOfferMainVo offer = new IronOfferMainVo();
//
//		offer.setStatus(1);
//		offer.setMsg("保存失败!");
//		offer.setMsgEn("Save failed.");
//		
//		return offer;
//	}
//}

@Component
class OfferClientCallbackFactory implements FallbackFactory<OfferClient> {

	@Override
	public OfferClient create(Throwable cause) {
		return new OfferClient() {
			@Override
			public IronOfferMainVo saveIronOffer(IronOfferMainVo ironOfferVo) {
				cause.printStackTrace();
				return null;
			}

			@Override
			public IronOfferMainVo getIronOffer(IronOfferQueryVo queryVo) {
				cause.printStackTrace();
				return null;
			}

			@Override
			public Page<IronOfferMainVo> query(IronOfferQueryVo queryVo) {
				cause.printStackTrace();
				
				if (queryVo == null) {
					queryVo = new IronOfferQueryVo();
				}
				
				return new PageImpl<IronOfferMainVo>(new ArrayList<>(), queryVo.getPageable(), 0);
			}
		};
	}
}
