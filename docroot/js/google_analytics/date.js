/***
* 
* Simple date format 
* 
*  @author : Ferron Hanse
*  @date : 10/11/2010
*  @lincense : do no evil!
*  
*/

// a global month names array
var gsMonthNames = new Array(
'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'
);


// a global day names array
var gsDayNames = new Array(
'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'
);


// the date format prototype

Date.prototype.format = function (f) {
    if (!this.valueOf())
        return ' ';

    var d = this;

    return f.replace(/(yyyy|yy|mmmm|mmm|mm|dddd|ddd|dd|hh|nn|ss|a\/p)/gi,
        function (val) {
            switch (val.toLowerCase()) {
                case 'yy': return d.getFullYear().substr(2);
                case 'yyyy': return d.getFullYear();
                case 'mmmm': return gsMonthNames[d.getMonth()];
                case 'mmm': return gsMonthNames[d.getMonth()].substr(0, 3);
                case 'mm': return (d.getMonth() + 1).zf(2);
                case 'dddd': return gsDayNames[d.getDay()];
                case 'ddd': return gsDayNames[d.getDay()].substr(0, 3);
                case 'dd': return d.getDate().zf(2);
                case 'hh': return ((h = d.getHours() % 12) ? h : 12).zf(2);
                case 'nn': return d.getMinutes().zf(2);
                case 'ss': return d.getSeconds().zf(2);
                case 'a/p': return d.getHours() < 12 ? 'am' : 'pm';
            }
        }
    );
};

// Zero-Fill
Number.prototype.zf = function (l) { return '0'.string(l - this.toString().length) + this; };

//return the sub of an integer
Number.prototype.substr = function (l) { return this.toString().substr(l); };

// VB-like string
String.prototype.string = function (l) { var s = '', i = 0; while (i++ < l) { s += this; } return s; };
