package com.esteel.web.service;

import java.util.ArrayList;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esteel.web.vo.offer.IronOfferMainVo;
import com.esteel.web.vo.offer.IronOfferPage;
import com.esteel.web.vo.offer.request.IronOfferQueryVo;

import feign.hystrix.FallbackFactory;

/**
 * 
 * @ClassName: OfferClient
 * @Description: TODO
 * @author wyf
 * @date 2017年12月4日 下午2:55:06
 *
 */
//@FeignClient(name = "Offer",url = "http://127.0.0.1:8880", fallbackFactory = OfferClientCallbackFactory.class, path = "offer")
@FeignClient(name = "Offer",url = "http://10.0.1.234:8880", fallbackFactory = OfferClientCallbackFactory.class, path = "offer")
public interface OfferClient {
	/**
	 * 铁矿报盘 保存/发布
	 * @param ironOfferVo
	 * @return
	 */
	@RequestMapping(value = "/saveIronOffer", method = RequestMethod.POST)
	public IronOfferMainVo saveIronOffer(@RequestBody IronOfferMainVo ironOfferVo);
	/**
	 * 铁矿报盘
	 * @param queryVo
	 * @return
	 */
	@RequestMapping(value = "/ironOffer", method = RequestMethod.POST)
	public IronOfferMainVo getIronOffer(@RequestBody IronOfferQueryVo queryVo);
	/**
	 * 铁矿报盘 更新全部相关信息
	 * @param ironOfferVo
	 * @return
	 */
	@RequestMapping(value = "/updateIronOffer", method = RequestMethod.POST)
	public IronOfferMainVo updateIronOffer(@RequestBody IronOfferMainVo ironOfferVo);
	/**
	 * 铁矿报盘分页
	 * @param queryVo
	 * @return
	 */
	@RequestMapping(value = "/ironOfferPage", method = RequestMethod.POST)
	public IronOfferPage query(@RequestBody IronOfferQueryVo queryVo);
	/**
	 * 铁矿报盘 更新主表
	 * @param ironOfferVo
	 * @return
	 */
	@RequestMapping(value = "/updateIronOfferMain", method = RequestMethod.POST)
	public IronOfferMainVo updateIronOfferMain(@RequestBody IronOfferMainVo ironOfferVo);
	/**
	 * 铁矿报盘 更新主表 附表
	 * @param ironOfferVo
	 * @return
	 */
	@RequestMapping(value = "/updateIronOfferMainAndAttach", method = RequestMethod.POST)
	public IronOfferMainVo updateIronOfferMainAndAttach(@RequestBody IronOfferMainVo ironOfferVo);
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
			public IronOfferPage query(IronOfferQueryVo queryVo) {
				cause.printStackTrace();
				
				if (queryVo == null) {
					queryVo = new IronOfferQueryVo();
				}
				
				IronOfferPage offerPage = new IronOfferPage();
				offerPage.setData(new ArrayList<>());
				
				return offerPage;
			}

			@Override
			public IronOfferMainVo updateIronOffer(IronOfferMainVo ironOfferVo) {
				cause.printStackTrace();
				return null;
			}

			@Override
			public IronOfferMainVo updateIronOfferMain(IronOfferMainVo ironOfferVo) {
				cause.printStackTrace();
				return null;
			}

			@Override
			public IronOfferMainVo updateIronOfferMainAndAttach(IronOfferMainVo ironOfferVo) {
				cause.printStackTrace();
				return null;
			}
		};
	}
}
