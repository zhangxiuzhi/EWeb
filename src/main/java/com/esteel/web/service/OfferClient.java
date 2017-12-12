package com.esteel.web.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esteel.web.vo.offer.IronOfferMainVo;

import feign.hystrix.FallbackFactory;

/**
 * 
 * @ClassName: OfferClient
 * @Description: TODO
 * @author wyf
 * @date 2017年12月4日 下午2:55:06
 *
 */
@FeignClient(name = "Offer",url = "http://127.0.0.1:8880",fallback = OfferClientCallback.class ,path = "offer")
//@FeignClient(name = "Offer", fallback = OfferClientCallback.class, path = "offer")
public interface OfferClient {
	@RequestMapping(value = "/saveOffer", method = RequestMethod.POST)
	public IronOfferMainVo saveOffer(@RequestBody IronOfferMainVo ironOfferVo);
}

@Component
class OfferClientCallback implements OfferClient {

	@Override
	public IronOfferMainVo saveOffer(IronOfferMainVo ironOfferVo) {
		IronOfferMainVo offer = new IronOfferMainVo();

		offer.setStatus(1);
		offer.setMsg("保存失败!");
		offer.setMsgEn("Save failed.");
		
		return offer;
	}
}

@Component
class OfferClientCallbackFactory implements FallbackFactory<OfferClient> {

	@Override
	public OfferClient create(Throwable cause) {
		return new OfferClient() {
			@Override
			public IronOfferMainVo saveOffer(IronOfferMainVo ironOfferVo) {
				cause.printStackTrace();
				return null;
			}
		};
	}
}
