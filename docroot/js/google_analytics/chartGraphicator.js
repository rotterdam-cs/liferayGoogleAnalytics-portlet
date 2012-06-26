//************************************************
//@author Prj.M@x <pablo.rendon@rotterdam-cs.com>
//************************************************

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

function showTooltip(x, y, contents, pos) {	
	toolSize = 170;
	var sizex = jQuery(window).width();	
	if(x + toolSize > sizex) {
		x = sizex - toolSize;
	}
    var top = y + 5;
    var left = x + 5;
    jQuery(document.body).append('<div id="tooltip" style="position: absolute; top: ' + top + 'px; left: ' + left + 'px; border: 1px solid #333; padding: 3px; background-color : #fff; -webkit-border-radius : 5px; -moz-border-radius: 5px; border-radius: 5px; opacity: 0.85;" >' + contents + '</div>');
}

function formatDataPoint(value, dataType) {
	if (dataType === 'int') {
		return parseInt(value, 10);
	} else if (dataType === 'percentage') {
	    return value + '%';
	} else if (dataType === 'time') {
		return secondsToTime(parseInt(value, 10));
	} else {
		return value;
	}
}

function secondsToTime(secs) {
    var hours = Math.floor(secs / (60 * 60));

    var divisor_for_minutes = secs % (60 * 60);
    var minutes = Math.floor(divisor_for_minutes / 60);

    var divisor_for_seconds = divisor_for_minutes % 60;
    var seconds = Math.ceil(divisor_for_seconds);

    if (minutes < 10) {
        minutes = '0' + minutes;
    }

    if (seconds < 10) {
        seconds = '0' + seconds;
    }

    if (hours > 1) {
        hours = '0' + hours + ':';
    } else {
        hours = '';
    }
    return hours + minutes + ':' + seconds;
}

function drawGraph(key, pastDates, value, past, type) {
    dataType = type;
    currentKPI = value;
    var currentData = [];
    len = Math.max(key.length, value.cur.data.length);

    for (var i=0; i < key.length; i++) {
        currentData.push([key[i], value.cur.data[i]]);
    }

    var pastData = [];
    if (past) {
        for (var i=0; i < key.length; i++) {
            pastData.push([key[i], value.prev.data[i]]);
        }
    }

    var data = [];
    data.push({
    	label: ' ' + new Date(key[0]*1).format(dateFormat) + ' - ' + new Date(key[key.length-1]*1).format(dateFormat),
    	data: currentData,
        color: '#0077CC',
        lines: {
            show: true,
            fill: true,
            fillColor: '#E6F2FA',
            lineWidth: 3
        },
        points: {
            show: true
        }
    });
	if (past) {
	    data.push({	    	
	    	label: ' ' + new Date(pastDates[0]*1).format(dateFormat) + ' - ' + new Date(pastDates[pastDates.length-1]*1).format(dateFormat),
	    	color: '#FF9900',
	        lines: {
	            show: true,
	            lineWidth: 1
	        },
	        points: {
	            show: false,
	            fill: true,
	            fillColor: '#FF9900',
	            lineWidth: 1
	        },
	        data: pastData
	    });
    }
    if (type === 'time') {
        plot = jQuery.plot(jQuery("#flot"), data, {
            xaxis: {
                mode: 'time',
                ticks: 5
            },
            yaxis: {
                ticks: 2,
                tickFormatter: function (val, axis) {
                    return secondsToTime(val);
                }
            },
            grid: {
                hoverable: true,
                borderWidth: 1,
                borderColor: '#AAAAAA',
                color: '#A69999',
                tickColor: '#E7E7E7'
            },
            legend: {
                container: jQuery('#legend'),
                noColumns: 2
            }
        });
    } else {
        plot = jQuery.plot(jQuery("#flot"), data, {
            xaxis: {
                mode: 'time',
                ticks: 5
            },
            yaxis: {
                ticks: 2
            },
            grid: {
                hoverable: true,
                borderWidth: 1,
                borderColor: '#AAAAAA',
                color: '#A69999',
                tickColor: '#E7E7E7'
            },
            legend: {
                container: jQuery('#legend'),
                noColumns: 2
            }
        });
    }

    var previousPoint = null;
    jQuery("#flot").bind("plothover", function (event, pos, item) {
        jQuery("#x").text(pos.x.toFixed(2));
        jQuery("#y").text(pos.y.toFixed(2));

        if (item) {
            if (previousPoint != item.datapoint) {
                previousPoint = item.datapoint;

                tooltip = jQuery('#tooltip');
                if (tooltip) {
                    tooltip.remove();
                }

                var x = item.datapoint[0].toFixed(2),
                    y = formatDataPoint(item.datapoint[1].toFixed(2), dataType);
                
                var date = new Date(parseInt(x,10));
                
                content = '<span class="datecurrent"> ' + date.format(dateFormatLong) + ' </span><br>';
				
                if(item.seriesIndex === 0) {
                	content += currentKPI.title+': <strong>' + y + '</strong>';
                }
                if(item.seriesIndex === 0 && showPast) {
                	y2 = formatDataPoint(plot.getData()[1].data[item.dataIndex][1], dataType);
                	content += '<br><span class="datepast"> ' + new Date(date.getTime() - (31 *24 * 60 * 60 * 1000)).format(dateFormatLong);
                	content += '</span><br> ' + previous + ': <strong>' + y2 + '</strong>';
                }
                if(item.seriesIndex === 1) {
                	y1 = formatDataPoint(plot.getData()[0].data[item.dataIndex][1], dataType);
                	content += currentKPI.title+': <strong>' + y1 + '</strong>';
                	content += '<br><span class="datepast"> ' + new Date(date.getTime() - (31 *24 * 60 * 60 * 1000)).format(dateFormatLong);
                	content += '</span><br> ' + previous +  ': <strong>' + y + '</strong>';
                }
                
                showTooltip(item.pageX, item.pageY, content, pos);
            }
        } else {
            tooltip = $('#tooltip');
            if (tooltip) {
                tooltip.remove();
            }

            previousPoint = null;
        }

    });
}

function drawSparkline(key, dates, pastDates, data, past, type, inverted, textGraph) {	
    var curvalue = (type === 'time') ? secondsToTime(data.cur.value) : data.cur.value;
    var postText = (type=="percentage")?" %":"";
    jQuery("#" + key + 'Summary .item_value').text(curvalue + postText);
    //86be2b FF9900
    var maxvalue = getMaxValue(data.cur.data,1);
    var imgsrc = 'http://chart.apis.google.com/chart?cht=ls&chs=75x18&chdlp=r&chm=B,e6f2fa,0,0.0,0.0&chco=0077cc,FF9900&chd=s:';
    if (textGraph) {
    	imgsrc = 'http://chart.apis.google.com/chart?cht=ls&chs=75x18&chls=1|1&chm=B,E6F2FA,0,0,0&chco=0077CC,FF9900&chds=0,' + maxvalue + ',0,' + maxvalue + '&chd=t:';
    }    
    if (past) {
    	if (!textGraph) {
    		imgsrc = imgsrc + data.cur.sparkline + ',' + data.prev.sparkline;
    	} else {
    		imgsrc = imgsrc + data.cur.data.toString() + '|' + data.prev.data.toString();
    	}	
    } else {
    	if (!textGraph) {
    		imgsrc = imgsrc + data.cur.sparkline;
    	} else {
    		imgsrc = imgsrc + data.cur.data.toString();
    	}
    }

    if (past) {
        var cssclass = (data.prev.delta.charAt(0) === '+' && !inverted) ? 'positive_comparison' : 'negative_comparison';
        var pastvalue = (type === 'time') ? secondsToTime(data.prev.value) : data.prev.value;        
        jQuery("#" + key + 'Summary .statistic').append('<p class="date_comparison"><span class="comparison_value"> ' + previous + ': ' + pastvalue +' <span class="' + cssclass + '"> ' + '(' + data.prev.delta + ')' +' </span> </span></p>');
    } else {
    	jQuery("#" + key + 'Summary .statistic p').remove();
    }

    jQuery("#" + key + 'Summary a').on('click', function (e) {
        e.preventDefault();
        drawGraph(dates, pastDates, data, past, type);
    });
    jQuery("#" + key + 'Sparkline .f_sparkline_inner img').remove();    
    jQuery("#" + key + 'Sparkline .f_sparkline_inner').append('<img src="' + imgsrc + '">');    
    jQuery("#" + key + 'Sparkline .f_sparkline_inner img').on('click', function (e) {
        drawGraph(dates, pastDates, data, past, type);
    });

}

function drawSparklines(ga, past) {
    drawSparkline('Visits', ga.dates, ga.pastDates, ga.visits, past, 'int', false, false);
    drawSparkline('NewVisits', ga.dates, ga.pastDates, ga.visitors, past, 'int', false, false);
    drawSparkline('Pageviews', ga.dates, ga.pastDates, ga.pageviews, past, 'int', false, false);
    drawSparkline('PagesVisit', ga.dates, ga.pastDates, ga.pageviewsPerVisit, past, 'double', false, false);
    drawSparkline('AvgVisitDuration', ga.dates, ga.pastDates, ga.avgTimeOnSite, past, 'time', false, false);
    drawSparkline('BounceRate', ga.dates, ga.pastDates, ga.visitBounceRate, past, 'percentage', true, false);    
    drawSparkline('NewVisitsPer', ga.dates, ga.pastDates, ga.percentNewVisits, past, 'percentage', false, false);
    
    // Hover effect
    jQuery('.visualization').hover(function () {
        jQuery(this).find("div").addClass("hover");
      },function() {
        jQuery(this).find("div").removeClass("hover");
      }
    );
}