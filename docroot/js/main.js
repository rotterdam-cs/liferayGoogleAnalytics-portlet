function setSelectOptions(selectId, options) {
	jQuery.each(options, function(val, text) {
		setSelectOption(selectId, val, text);
	});
}
function setSelectOption(selectId, val, text) {
	console.log(selectId);
	var mySelect = jQuery('#' + selectId);
	mySelect.append(
        jQuery('<option></option>').val(val).html(text)
    );
}

function showError(message){
	jQuery("#" + namespace + "alert-content").html(message);
    jQuery(".alert-success").hide();
    jQuery(".alert-error").fadeIn();
}
function showInfo(message){
	jQuery(".alert-error").hide();
    jQuery("#" + namespace + "info-content").html(message);
    jQuery(".alert-success").fadeIn();
}