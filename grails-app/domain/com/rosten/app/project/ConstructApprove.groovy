package com.rosten.app.project

class ConstructApprove {
	String id
    static constraints = {
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_PRO_CONAPP"
	}
}
