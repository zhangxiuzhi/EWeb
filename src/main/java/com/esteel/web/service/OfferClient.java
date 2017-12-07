package com.esteel.web.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esteel.web.vo.offer.IronOfferBaseVo;

import feign.hystrix.FallbackFactory;

/**
 * 
 * @ClassName: OfferClient
 * @Description: TODO
 * @author wyf
 * @date 2017年12月4日 下午2:55:06
 *
 */
@FeignClient(name = "Offer", fallback = OfferClientCallback.class, path = "offer")
public interface OfferClient {
	@RequestMapping(value = "/saveOffer", method = RequestMethod.POST)
	public IronOfferBaseVo saveOffer(@RequestBody IronOfferBaseVo ironOfferVo);
}

@Component
class OfferClientCallback implements OfferClient {

	@Override
	public IronOfferBaseVo saveOffer(IronOfferBaseVo ironOfferVo) {
//		if (vo.getOfferStatus() == EsteelConstant.OFFER_STATUS_In_SALE) {
//			return "发布失败";
//		}
//
//		return "保存草稿失败";
		
		return null;
	}
}

@Component
class OfferClientCallbackFactory implements FallbackFactory<OfferClient> {

	@Override
	public OfferClient create(Throwable cause) {
		return new OfferClient() {
			@Override
			public IronOfferBaseVo saveOffer(IronOfferBaseVo ironOfferVo) {
				cause.printStackTrace();
				return null;
			}
		};
	}
}
