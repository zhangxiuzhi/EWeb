/**
 * Created by wzj on 2017/11/2.
 */


class TradeCardList extends React.Component{

	constructor(props) {
		super(props)

		this.state = {
			data:[
				{name:"node",type:"f",commodityName:"Atlas粉+Atlas块",company:"上海点钢电子商务",price:"11月普氏62%+viu+1.0/1.2",grade:"62.23+63.37",number:"70,000+100,000",transportStatus:"2017/08/11-2017/08/20",expiryDate:"1小时49分钟59秒"},
				{name:"node-1",type:"s",commodityName:"混合粉",company:"上海点钢电子商务",price:"500",port:"青岛港",grade:"58.02",number:"10,000",breakUp:"可拆分",breakUpDesc:"起订量5,000湿吨",expiryDate:"1小时49分钟59秒"},
				{name:"node-2",type:"p",commodityName:"国王粉",company:"上海点钢电子商务",price:"i1801+0.6",port:"连云港",number:"8,000",offerDate:"2017/08/11-2017/08/20",dealDate:"2017/09/01-2017/09/30",expiryDate:"1小时49分钟59秒"},
				{name:"node",type:"f",commodityName:"Atlas粉+Atlas块",company:"上海点钢电子商务",price:"11月普氏62%+viu+1.0/1.2",grade:"62.23+63.37",number:"70,000+100,000",transportStatus:"2017/08/11-2017/08/20",expiryDate:"1小时49分钟59秒"},
				{name:"node-1",type:"s",commodityName:"混合粉",company:"上海点钢电子商务",price:"500",port:"青岛港",grade:"58.02",number:"10,000",breakUp:"可拆分",breakUpDesc:"起订量5,000湿吨",expiryDate:"1小时49分钟59秒"},
				{name:"node-2",type:"p",commodityName:"国王粉",company:"上海点钢电子商务",price:"i1801+0.6",port:"连云港",number:"8,000",offerDate:"2017/08/11-2017/08/20",dealDate:"2017/09/01-2017/09/30",expiryDate:"1小时49分钟59秒"},
				{name:"node",type:"f",commodityName:"Atlas粉+Atlas块",company:"上海点钢电子商务",price:"11月普氏62%+viu+1.0/1.2",grade:"62.23+63.37",number:"70,000+100,000",transportStatus:"2017/08/11-2017/08/20",expiryDate:"1小时49分钟59秒"},
				{name:"node-1",type:"s",commodityName:"混合粉",company:"上海点钢电子商务",price:"500",port:"青岛港",grade:"58.02",number:"10,000",breakUp:"可拆分",breakUpDesc:"起订量5,000湿吨",expiryDate:"1小时49分钟59秒"},
				{name:"node-2",type:"p",commodityName:"国王粉",company:"上海点钢电子商务",price:"i1801+0.6",port:"连云港",number:"8,000",offerDate:"2017/08/11-2017/08/20",dealDate:"2017/09/01-2017/09/30",expiryDate:"1小时49分钟59秒"},


			]
		}
	}

	render(){
		const data = this.state.data;

		return React.createElement(EsteelCard, { data: data, className: "esteel-cards"} );
	}
}