//************************************************
//@author Prj.M@x <pablo.rendon@rotterdam-cs.com>
//************************************************

//Objects
function liferayGoogleAnalyticsDataObjectValue() {
	this.data = new Array();
	this.sparkline = "";
	this.value = 0;
	this.delta = "+100%";
}

function liferayGoogleAnalyticsDataObjectValues() {
	this.title = "";
	this.cur = new liferayGoogleAnalyticsDataObjectValue();
	this.prev = new liferayGoogleAnalyticsDataObjectValue();
}

function liferayGoogleAnalyticsDataObject() {
	this.dates = new Array();
	this.visits = new liferayGoogleAnalyticsDataObjectValues();
	this.visitors = new liferayGoogleAnalyticsDataObjectValues();
	this.pageviews = new liferayGoogleAnalyticsDataObjectValues();
	this.pageviewsPerVisit = new liferayGoogleAnalyticsDataObjectValues();
	this.visitBounceRate = new liferayGoogleAnalyticsDataObjectValues();
	this.avgTimeOnSite = new liferayGoogleAnalyticsDataObjectValues();
	this.percentNewVisits = new liferayGoogleAnalyticsDataObjectValues();
}

//Methods
function drawLiferayGoogleAnalyticsGraphics(results) {
	lgaDataObject = new liferayGoogleAnalyticsDataObject();
	
	//Add Dates
	var datesIndex = getcolumnHeaderIndex(results.columnHeaders, "date");
	var dates = new Array();
	for ( var i = 0; i < results.rows.length; i++) {		
		dates.push(getDateFromGA(results.rows[i][datesIndex]));
	}
	lgaDataObject.dates = dates;	
		
	lgaDataObject.visits = getliferayGoogleAnalyticsDataObject(results, "visits");
	lgaDataObject.visitors = getliferayGoogleAnalyticsDataObject(results, "visitors");
	lgaDataObject.pageviews = getliferayGoogleAnalyticsDataObject(results, "pageviews");
	lgaDataObject.pageviewsPerVisit = getliferayGoogleAnalyticsDataObject(results, "pageviewsPerVisit");
	lgaDataObject.visitBounceRate = getliferayGoogleAnalyticsDataObject(results, "visitBounceRate");
	lgaDataObject.avgTimeOnSite = getliferayGoogleAnalyticsDataObject(results, "avgTimeOnSite");
	lgaDataObject.percentNewVisits = getliferayGoogleAnalyticsDataObject(results, "percentNewVisits");
	
	init(lgaDataObject);
}

function drawLiferayGoogleAnalyticsGraphicsPrev(prevResults){
	lgaDataObject = data;		
	lgaDataObject.visits = getliferayGoogleAnalyticsDataObjectPrev(prevResults, "visits", lgaDataObject.visits);
	lgaDataObject.visitors = getliferayGoogleAnalyticsDataObjectPrev(prevResults, "visitors", lgaDataObject.visitors);
	lgaDataObject.pageviews = getliferayGoogleAnalyticsDataObjectPrev(prevResults, "pageviews", lgaDataObject.pageviews);
	lgaDataObject.pageviewsPerVisit = getliferayGoogleAnalyticsDataObjectPrev(prevResults, "pageviewsPerVisit", lgaDataObject.pageviewsPerVisit);
	lgaDataObject.visitBounceRate = getliferayGoogleAnalyticsDataObjectPrev(prevResults, "visitBounceRate", lgaDataObject.visitBounceRate);
	lgaDataObject.avgTimeOnSite = getliferayGoogleAnalyticsDataObjectPrev(prevResults, "avgTimeOnSite", lgaDataObject.avgTimeOnSite);
	lgaDataObject.percentNewVisits = getliferayGoogleAnalyticsDataObjectPrev(prevResults, "percentNewVisits", lgaDataObject.percentNewVisits);
	
	initprev(lgaDataObject);
}


function getliferayGoogleAnalyticsDataObject(results, objectName) {
	var objectNameIndex = getcolumnHeaderIndex(results.columnHeaders, objectName);
	var curData = new Array();
	for ( var i = 0; i < results.rows.length; i++) {		
		curData.push(results.rows[i][objectNameIndex]);
	}
	var iObject = new liferayGoogleAnalyticsDataObjectValues();
	iObject.title = capitalize(objectName);
	
	var iObjectCurData = new liferayGoogleAnalyticsDataObjectValue();
	iObjectCurData.data = curData;
	iObjectCurData.sparkline = simpleEncode(curData, maxValue);
	iObjectCurData.value = fixTotalVal(results.totalsForAllResults[results.columnHeaders[objectNameIndex].name]);
	iObject.cur = iObjectCurData;	
	return iObject;	
}

function getliferayGoogleAnalyticsDataObjectPrev(prevResults, objectName, iObject) {
	var objectNameIndex = getcolumnHeaderIndex(prevResults.columnHeaders, objectName);
	var prevData = new Array();
	for ( var i = 0; i < prevResults.rows.length; i++) {		
		prevData.push(prevResults.rows[i][objectNameIndex]);
	}	
	var iObjectPrevData = new liferayGoogleAnalyticsDataObjectValue();
	iObjectPrevData.data = prevData;
	iObjectPrevData.sparkline = simpleEncode(prevData, maxValue);
	iObjectPrevData.value = fixTotalVal(prevResults.totalsForAllResults[prevResults.columnHeaders[objectNameIndex].name]);
	
	var curvalue = parseInt(iObject.cur.value.replace(",",""),10);
	var prevvalue = parseInt(iObjectPrevData.value.replace(",",""),10);
	var delta = ((curvalue - prevvalue) * 100) / prevvalue;	
	iObjectPrevData.delta = ((delta > 0)?"+":"") + roundVal(delta) + " %";
	iObject.prev = iObjectPrevData;	
	return iObject;	
}

//Auxiliar Methods
function capitalize (text) {
	capitalized = text.charAt(0).toUpperCase() + text.slice(1);
	return capitalized;
}

function roundVal(val){
	var dec = 2;
	var result = Math.round(val*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}

function fixTotalVal(val){
	return roundVal(val).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function getcolumnHeaderIndex(columnHeaders, name) {
	var result = -1;
	for ( var i = 0; i < columnHeaders.length; i++) {
		if (columnHeaders[i].name == name || columnHeaders[i].name.replace("ga:","") == name) {
			result = i;
		}
	}
	return result;
}

function getDateFromGA(datai) {
	var dates = datai.toString();
	var year = dates.substr(0,4);
	var month = dates.substr(4,2)-1;
	var day = dates.substr(6,2);
	var result = new Date(year, month, day).getTime();	
	return result;		
}

function getMaxValue(array, maxValue) {
	if (array.length > 0) {
		for ( var i = 0; i < array.length; i++) {
			var compValue = parseInt(array[i].replace(",",""),10);
			if (compValue > maxValue) {
				maxValue = compValue;
			}
		}
	}
	return maxValue;
}









