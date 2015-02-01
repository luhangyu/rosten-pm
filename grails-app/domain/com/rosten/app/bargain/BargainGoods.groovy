package com.rosten.app.bargain
import com.rosten.app.base.MaterialInfo
import java.text.SimpleDateFormat
import com.rosten.app.system.Company
import com.rosten.app.annotation.GridColumn
import com.rosten.app.util.SystemUtil



class BargainGoods {

    String id
	//货物名称
	@GridColumn(name="货物名称",formatter="bargainGoods_formatTopic",colIdx=1,width="160px")
	String barGoodsName

	//生产厂家
	@GridColumn(name="生产厂家",width="100px",colIdx=2)
	String barGoodsCorp
	
	//单位（如吨）
	@GridColumn(name="单位",width="60px",colIdx=4)
	String barGoodsUnit
	
	//数量
	@GridColumn(name="数量",width="60px",colIdx=3)
	Long barGoodsNum
	
	//市场价
	@GridColumn(name="市场价(元)",width="60px",colIdx=5)
	Long barGoodsPrice
	
	//折扣
	@GridColumn(name="折扣",width="40px",colIdx=6)
	Double barGoodsDiscount = 10
	
	@GridColumn(name="总价(元)",width="60px",colIdx=7)
	Double barGoodsTPrice
	
	//备注
	@GridColumn(name="备注",colIdx=8)
	String barGoodsRemark
	

	
	@GridColumn(name="操作",colIdx=9,width="40px",formatter="bargainGoods_action")
	def bargainGoodsId(){
		return id
	}
	
	static belongsTo = [bargain:Bargain]
	static constraints = {
		barGoodsRemark nullable:true,blank:true
	}
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_BAR_GOODS"
	}
}
