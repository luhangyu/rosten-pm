package com.rosten.app.bargain

import java.text.SimpleDateFormat
import com.rosten.app.system.Company
import com.rosten.app.annotation.GridColumn
import com.rosten.app.util.SystemUtil



class BargainGoods {

    String id
	
	//材料名称
	@GridColumn(name="材料名称",formatter="bargainGoods_formatTopic",colIdx=1,width="160px")
	String bargainGoodsName
	
	//生产厂家
	@GridColumn(name="生产厂家",width="160px",colIdx=2)
	String bargainGoodsCorp
	
	//单位（如吨）
	@GridColumn(name="单位",width="60px",colIdx=4)
	String bargainGoodsUnit
	
	//数量
	@GridColumn(name="数量",width="60px",colIdx=3)
	Long bargainGoodsNum
	
	//市场价
	@GridColumn(name="市场价",width="60px",colIdx=5)
	Long bargainGoodsPrice
	
	//折扣
	@GridColumn(name="折扣",width="60px",colIdx=6)
	Long bargainGoodsDiscount
	
	//备注
	@GridColumn(name="备注",colIdx=7)
	String bargainGoodsRemark
	
	
	static belongsTo = [bargain:Bargain]
	
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_Bargain_BargainGoods"
	}
}
