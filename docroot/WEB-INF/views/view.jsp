<%@taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<portlet:defineObjects />

<!-- The 2 Buttons for the user to interact with -->
<button id="authorize-button" style="visibility: hidden">Authorize</button><br/>
<button id="make-api-call-button" style="visibility: hidden">Get Visits</button>

<div id="mod_analytics" class="hidden">
	<div id="legend"></div>
	<div id="analyticsControls"><input type="checkbox" id="pastMonth" name="pastMonth"  /> Compare to past</div>
	<div class="clr"></div>	
	<div id="flot" style="width:100%;height:150px;"></div>	
	<div class="report_section">
		<div class="section">		
			<div class="sparkline"><div id="VisitsSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div>
			<div id="VisitsSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#">Visits: </a></span><span class="item_value"></span></div></div></div></div>
						
			<div class="sparkline"><div id="NewVisitsSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div>
			<div id="NewVisitsSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#">Unique Visitors: </a></span><span class="item_value"></span></div></div></div></div>
			
			<div class="sparkline"><div id="PageviewsSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div>
			<div id="PageviewsSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#">Pageviews: </a></span><span class="item_value"></span></div></div></div></div>
			
			<div class="sparkline"><div id="PagesVisitSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div>
			<div id="PagesVisitSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#">Pages/Visit: </a></span><span class="item_value"></span></div></div></div></div>
		</div>
		
		<div class="section">
			<div class="sparkline"><div id="AvgVisitDurationSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div>
			<div id="AvgVisitDurationSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#">Avg. Visit Duration: </a></span><span class="item_value"></span></div></div></div></div>

			<div class="sparkline"><div id="BounceRateSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div> 
			<div id="BounceRateSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#">Bounce Rate: </a></span><span class="item_value"></span></div></div></div></div>
						
			<div class="sparkline"><div id="NewVisitsPerSparkline"><div class="visualization"><div class="f_sparkline_outer"><div class="f_sparkline_inner"></div></div></div></div> 
			<div id="NewVisitsPerSummary"><div class="statistic"><div class="primary_value"><span class="labelanalytics"><a href="#">% New Visits: </a></span><span class="item_value"></span></div></div></div></div>
		</div>
		<div class="clr"></div>
	</div>
</div>

<script type="text/javascript">
var previous = 'Previous';
var dateFormat = 'mm/dd/yy';
var dateFormatLong = 'dddd, mmmm dd, yyyy';

var selProfileId;
var dataType;
var currentKPI;
var plot;
var data;
var showPast = jQuery('#pastMonth').checked;
var maxValue = 1;

var init = function(initData) {
	data = initData;
	<%--//comparePast = (Cookie.read('analitycsCompareToPast') == 'true'); --%>
	comparePast = false;
	drawSparklines(data, comparePast);
	drawGraph(data.dates, data.visits, comparePast, 'int');
	jQuery("#mod_analytics").unmask();
	jQuery("#make-api-call-button").hide();
};

var initprev = function(initPrevData) {
	data = initPrevData;
	showPast = (jQuery('#pastMonth').attr('checked'))?true:false;
	drawGraph(initPrevData.dates, currentKPI, showPast, dataType);
	drawSparklines(initPrevData, showPast);
	jQuery("#mod_analytics").unmask();
};
	
Liferay.on('portletReady', function(event) {            
	if('_' + event.portletId + '_' == '<portlet:namespace/>') {
					
// 		jQuery('#pastMonth').on('click', function() {
// 			queryCoreReportingApiPrev();			
// 		});	
		
// 		jQuery('#flot').on('mouseleave', function() {
// 			tooltip = jQuery('#tooltip');
// 			if (tooltip) {
// 			    tooltip.remove();
// 			}
// 		});
	}
});
</script>

<div>test Lifreay Google Analytics</div>
