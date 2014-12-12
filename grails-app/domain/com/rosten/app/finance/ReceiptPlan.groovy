package com.rosten.app.finance

class ReceiptPlan {
	String id
    static constraints = {
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_FIN_RECEPLAN"
	}
}
