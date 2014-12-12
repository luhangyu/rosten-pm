package com.rosten.app.finance

class BorrowManage {
	String id
	
    static constraints = {
    }
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_FIN_BORROW"
	}
}
