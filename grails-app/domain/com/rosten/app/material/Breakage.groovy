package com.rosten.app.material

class Breakage {
	String id
    static constraints = {
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_MAT_BREAGE"
	}
}
