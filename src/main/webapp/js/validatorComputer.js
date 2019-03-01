(function ( ) {
	if($( "#introduced" ).val() == ""){
		$( "#discontinued" ).attr("disabled", true);
	}
	$( "#discontinued" ).attr("min", $( "#introduced" ).val());
	$( "#introduced" ).attr("max", $( "#discontinued" ).val());
}( jQuery ));

$( "#introduced" ).change(function(){
	$( "#discontinued" ).attr("disabled", false);
	$( "#discontinued" ).attr("min", $( "#introduced" ).val());
});

$( "#discontinued" ).change(function(){
	$( "#introduced" ).attr("max", $( "#discontinued" ).val());
});