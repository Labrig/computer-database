(function ( ) {
	console.log("salut");
	if($("#introduced").data("min")) {

		$("#discontinued").setAttribute("min", this.value);
	}
}( jQuery ));

//document.getElementById("introduced").onchange = function () {
//    var input = document.getElementById("discontinued");
//    input.disabled = false;
//    input.setAttribute("min", this.value);
//}
//
//document.getElementById("discontinued").onchange = function () {
//    var input = document.getElementById("introduced");
//    input.setAttribute("max", this.value);
//}