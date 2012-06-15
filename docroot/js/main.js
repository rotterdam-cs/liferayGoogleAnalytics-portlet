function setSelectOptions(selectId, options) {
	jQuery.each(options, function(val, text) {
		setSelectOption(selectId, val, text);
	});
}
function clearSelectOption(selectId) {
	jQuery("#" + selectId + " .added").remove();
}
function setSelectOption(selectId, val, text) {
	var mySelect = jQuery('#' + selectId);
	mySelect.append(
        jQuery('<option class="added"></option>').val(val).html(text)
    );
}
function showError(message){	
	if (jQuery("#" + namespace + "alert-content").is(":visible")) {
		jQuery("#" + namespace + "alert-content").append("<br /><br />" + message);
	} else {
		jQuery("#" + namespace + "alert-content").html(message);
	}
    jQuery(".alert-success").hide();
    jQuery(".alert-error").fadeIn();
}
function showInfo(message){
	jQuery(".alert-error").hide();
	if (jQuery("#" + namespace + "info-content").is(":visible")) {
		jQuery("#" + namespace + "info-content").append("<br /><br />" + message);
	} else {
		jQuery("#" + namespace + "info-content").html(message);
	}
    jQuery(".alert-success").fadeIn();
}
function getLocallizedKey(fkey) {
	var message=defaultErrorMessage;
	try {
		jQuery.each(messages.messages, function(key, value) {
			if (value.key == fkey) {
				message = value.value;
			}			
		});
	}catch(e){
		console.log("global variable 'messages' is not defined");
	}
	return message;	       
}