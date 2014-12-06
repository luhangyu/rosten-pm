package com.rosten.app.material
import java.util.Date;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company
class Check {

    static constraints = {
    }
	
	static belongsTo = [company:Company]
	
		
		
		static mapping = {
			id generator:'uuid.hex',params:[separator:'-']
			table "RS_Material_Check"
		}
	
}
