	function toggleSearch(id) {
		var x = document.getElementById(id);
		if (x.className.indexOf("w3-show") == -1) {
			x.className += " w3-show";
		} else {
			x.className = x.className.replace(" w3-show", "");
		}
	}

	function w3_open() {
		document.getElementById("mySidenav").style.display = "block";
	}

	function w3_close() {
		document.getElementById("mySidenav").style.display = "none";
	}