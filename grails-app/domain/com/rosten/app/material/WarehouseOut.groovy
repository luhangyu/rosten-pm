package com.rosten.app.material

class WarehouseOut {
	String id
    static constraints = {
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_MAT_WARHOUOUT"
	}
}
