package com.rosten.app.project

class ConstructLog {
	String id
    static constraints = {
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_PRO_CONLOG"
	}
}
