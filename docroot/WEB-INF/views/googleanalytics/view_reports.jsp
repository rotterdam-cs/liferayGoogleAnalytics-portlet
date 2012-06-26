<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<%@taglib prefix="theme" uri="http://liferay.com/tld/theme" %>
<%@taglib prefix="liferay-util" uri="http://liferay.com/tld/util" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:setBundle basename="Language"/>
<portlet:resourceURL var="getAnalyticsDataURL" id="getAnalyticsData" />
<portlet:defineObjects />

<div id="mod_analytics">
	
	<div id="date-range">
		<div id="date-range-field">
		    <span></span>
		    <a href="#">&#9660;</a>
	  	</div>	   
	  	<div id="datepicker-calendar">
		  	<a class="btn btn-primary" href="#">
				<i class="icon-ok icon-white"></i>
				<fmt:message key="com.rcs.general.apply"/>
			</a>
		</div>
	</div>
	
	<div id="legend"></div>
	<div id="analyticsControls"><input type="checkbox" id="pastMonth" name="pastMonth"  /> <fmt:message key="com.rcs.googleanalytics.graphic.compare.to.past"/></div>
	<div class="clr"></div>	
	<div id="flot" style="width:100%;height:150px;"></div>	
	<div class="report_section">
		<div class="section">		
			<div class="sparkline"><div id="VisitsSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div>
			<div id="VisitsSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#"><fmt:message key="com.rcs.googleanalytics.graphic.visits"/>: </a></span><span class="item_value"></span></div></div></div></div>
						
			<div class="sparkline"><div id="NewVisitsSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div>
			<div id="NewVisitsSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#"><fmt:message key="com.rcs.googleanalytics.graphic.unique.visitors"/>: </a></span><span class="item_value"></span></div></div></div></div>
			
			<div class="sparkline"><div id="PageviewsSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div>
			<div id="PageviewsSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#"><fmt:message key="com.rcs.googleanalytics.graphic.pageviews"/>: </a></span><span class="item_value"></span></div></div></div></div>
			
			<div class="sparkline"><div id="PagesVisitSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div>
			<div id="PagesVisitSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#"><fmt:message key="com.rcs.googleanalytics.graphic.pages.visits"/>: </a></span><span class="item_value"></span></div></div></div></div>
		</div>
		
		<div class="section">
			<div class="sparkline"><div id="AvgVisitDurationSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div>
			<div id="AvgVisitDurationSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#"><fmt:message key="com.rcs.googleanalytics.graphic.avg.visit.duration"/>: </a></span><span class="item_value"></span></div></div></div></div>

			<div class="sparkline"><div id="BounceRateSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div> 
			<div id="BounceRateSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#"><fmt:message key="com.rcs.googleanalytics.graphic.bounce.rate"/>: </a></span><span class="item_value"></span></div></div></div></div>
						
			<div class="sparkline"><div id="NewVisitsPerSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div> 
			<div id="NewVisitsPerSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#"><fmt:message key="com.rcs.googleanalytics.graphic.new.visitsper"/>: </a></span><span class="item_value"></span></div></div></div></div>
		</div>
		<div class="clr"></div>
	</div>
</div>

<script type="text/javascript">
	var previous = '<fmt:message key="com.rcs.googleanalytics.graphic.previous"/>';
	var dateFormat = 'mm/dd/yy';
	var dateFormatLong = 'dddd, mmmm dd, yyyy';	
	var selProfileId;
	var dataType;
	var currentKPI;
	var plot;
	var data = jQuery.parseJSON('${googleAnalyticsData}');
	var showPast = jQuery('#pastMonth').checked;
	var maxValue = 1;
	var to = new Date(${endDate});
  	var from = new Date(${startDate});
	
	function init() {
		showPast = (jQuery('#pastMonth').attr('checked'))?true:false;
		drawSparklines(data, showPast);
		drawGraph(data.dates, data.pastDates, data.visits, showPast, 'int');
		jQuery("#mod_analytics").unmask();		
	};
		
	jQuery(function() {
		init();
		jQuery('#pastMonth').on('click', function() {
			init();			
		});	
		
		jQuery('#flot').on('mouseleave', function() {
			tooltip = jQuery('#tooltip');
			if (tooltip) {
			    tooltip.remove();
			}
		});
		
		//DateRangeSelector		  
		  jQuery('#datepicker-calendar').DatePicker({
		    inline: true,
		    date: [from, to],
		    calendars: 3,
		    mode: 'range',
		    current: new Date(to.getFullYear(), to.getMonth() - 1, 1),
		    onChange: function(dates,el) {
		      // update the range display
		      jQuery('#date-range-field span').text(dates[0].getDate()+' '+dates[0].getMonthName(true)+', '+dates[0].getFullYear()+' - '+dates[1].getDate()+' '+dates[1].getMonthName(true)+', '+dates[1].getFullYear());
		      from = dates[0];
		      to = dates[1];
		     }
		   });
		   
		   // initialize the special date dropdown field
		   jQuery('#date-range-field span').text(from.getDate()+' '+from.getMonthName(true)+', '+from.getFullYear()+' - '+
		                                        to.getDate()+' '+to.getMonthName(true)+', '+to.getFullYear());
		   
		   // bind a click handler to the date display field, which when clicked
		   // toggles the date picker calendar, flips the up/down indicator arrow,
		   jQuery('#date-range-field').bind('click', function(){
		     jQuery('#datepicker-calendar').toggle();
		     if(jQuery('#date-range-field a').text().charCodeAt(0) == 9660) {
		       // switch to up-arrow
		       jQuery('#date-range-field a').html('&#9650;');
		       jQuery('#date-range-field').css({borderBottomLeftRadius:0, borderBottomRightRadius:0});
		       jQuery('#date-range-field a').css({borderBottomRightRadius:0});
		     } else {
		       // switch to down-arrow
		       jQuery('#date-range-field a').html('&#9660;');
		       jQuery('#date-range-field').css({borderBottomLeftRadius:5, borderBottomRightRadius:5});
		       jQuery('#date-range-field a').css({borderBottomRightRadius:5});
		     }
		     return false;
		   });
		   
		   function hidecal(){
			   jQuery('#datepicker-calendar').hide();
		       jQuery('#date-range-field a').html('&#9660;');
		       jQuery('#date-range-field').css({borderBottomLeftRadius:5, borderBottomRightRadius:5});
		       jQuery('#date-range-field a').css({borderBottomRightRadius:5});
		   }
		   
		   jQuery('html').click(function() {
		     if(jQuery('#datepicker-calendar').is(":visible")) {
		    	 hidecal();
		     }
		   });
		   
		   // stop the click propagation when clicking on the calendar element
		   // so that we don't close it
		   jQuery('#datepicker-calendar').click(function(event){
		     event.stopPropagation();
		   });
		   
		   jQuery("#datepicker-calendar .btn").click(function(event){
		   		event.stopPropagation();
		   		jQuery("#mod_analytics").mask('<fmt:message key="com.rcs.googleanalytics.retrieving.data"/>');
		     	jQuery.get("${getAnalyticsDataURL}"
    		 	,{
		   			"startDateS" : from.getTime()
    		 		,"endDateS" : to.getTime()
    		 	}
    		 	,function(analyticsData) {
    		 		data = jQuery.parseJSON(analyticsData);
    		 		init();
    		 		hidecal();
    		 	}
    		 );
		     
		   });
		
		
		
	});
</script>