package com.rosten.app.project

class VedioMonitor {
	String id
    static constraints = {
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_PRO_VEDO"
	}
}
