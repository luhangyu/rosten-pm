package com.rosten.app.finance

class ExpenseReport {
	String id
	
    static constraints = {
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_FIN_EXPRE"
	}
}
