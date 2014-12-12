package com.rosten.app.finance

class Payment {
	String id
    static constraints = {
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_FIN_PAY"
	}
}
