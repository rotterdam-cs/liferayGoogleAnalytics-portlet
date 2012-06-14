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
	jQuery("#" + namespace + "alert-content").html(message);
    jQuery(".alert-success").hide();
    jQuery(".alert-error").fadeIn();
}
function showInfo(message){
	jQuery(".alert-error").hide();
    jQuery("#" + namespace + "info-content").html(message);
    jQuery(".alert-success").fadeIn();
}
function getLocallizedKey(fkey) {
	var message=defaultErrorMessage;
	try {
		jQuery.each(messages.errors, function(key, value) {
			if (value.key == fkey) {
				message = value.value;
			}			
		});
	}catch(e){
		console.log("global variable 'messages' is not defined");
	}
	return message;	       
}