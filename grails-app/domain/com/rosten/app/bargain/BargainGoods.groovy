package com.rosten.app.bargain

import java.text.SimpleDateFormat
import com.rosten.app.system.Company
import com.rosten.app.annotation.GridColumn
import com.rosten.app.util.SystemUtil



class BargainGoods {

    String id
	
	//材料名称
	@GridColumn(name="材料名称",formatter="bargainGoods_formatTopic",colIdx=1,width="160px")
	String barGoodsName
	
	//生产厂家
	@GridColumn(name="生产厂家",width="160px",colIdx=2)
	String barGoodsCorp
	
	//单位（如吨）
	@GridColumn(name="单位",width="60px",colIdx=4)
	String barGoodsUnit
	
	//数量
	@GridColumn(name="数量",width="60px",colIdx=3)
	Long barGoodsNum
	
	//市场价
	@GridColumn(name="市场价",width="60px",colIdx=5)
	Long barGoodsPrice
	
	//折扣
	@GridColumn(name="折扣",width="60px",colIdx=6)
	Long barGoodsDiscount
	
	//备注
	@GridColumn(name="备注",colIdx=7)
	String barGoodsRemark
	
	
	static belongsTo = [bargain:Bargain]
	
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_BARGAIN_GOODS"
	}
}
