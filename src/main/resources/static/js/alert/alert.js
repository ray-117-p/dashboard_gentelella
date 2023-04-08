var mensaje = "";
/** *************** sweetalert2  Mixin **************************** */
const Toast = Swal.mixin({
  toast: true,
  position: 'top-end',
  showConfirmButton: false,
  timer: 3000,
  timerProgressBar: true
})

function warningToast(message) {
	Toast.fire({
		icon: 'warning',
		title: 'Oops...',
		text: message
	})
}

function infoToast(message) {
	Toast.fire({
		icon: 'info',
		title: 'Información.',
		text: message
	})
}

function errorToast(message) {
	Toast.fire({
		icon: 'error',
		title: 'Error:',
		text: message
	})
}

function successToast(message) {
	Toast.fire({
		icon: 'success',
		title: 'Éxito!',
		text: message
	})
}
/** *************** sweetalert2 **************************** */
function warningSwal(message) {
	Swal.fire({
		icon: 'warning',
		title: 'Oops...',
		text: message
	})
}

function infoSwal(message) {
	Swal.fire({
		icon: 'info',
		title: 'Información',
		text: message
	})
}

function errorSwal(message) {
	Swal.fire({
		icon: 'error',
		title: 'Error...',
		text: message
	})
}

function successSwal(message) {
	Swal.fire({
		icon: 'success',
		title: 'Éxito!',
		text: message
	})
}

function successSwalDefault() {
	Swal.fire({
		icon: 'success',
		title: 'Éxito!',
		text: "Datos Guardados."
	})
}

function successUpdate() {
	Swal.fire({
		icon: 'success',
		title: 'Éxito!',
		text: "Datos Actualizados."
	})
}

function successFinalizado() {
	Swal.fire({
		icon: 'success',
		title: 'Éxito!',
		text: "Préstamo finalizado"
	})
}

function questionSwal(message, valor) {
	Swal.fire({
		title: '¿Estás seguro (a) ?',
		text: message,
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Si, Eliminar',
		cancelButtonText: 'No, Cancelar'
	}).then((result) => {
		if (result.value) {
			document.getElementById("eliminarHos" + valor.id).submit();
		} else {
			console.log("XD");
		}
	})
}

function questionSwalSegmento(message, valor) {
	Swal.fire({
		title: '¿Estás seguro (a) ?',
		text: message,
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Si, Eliminar',
		cancelButtonText: 'No, Cancelar'
	}).then((result) => {
		if (result.value) {
			//console.log(valor.id);
			deleteSegmento(valor.id);
		} else {
			console.log("XD");
		}
	})
}

function questionSwalItem(message, valor) {
	Swal.fire({
		title: '¿Estás seguro (a) ?',
		text: message,
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Si, Eliminar',
		cancelButtonText: 'No, Cancelar'
	}).then((result) => {
		if (result.value) {
			//console.log(valor.id);
			deleteItem(valor.id);
		} else {
			console.log("XD");
		}
	})
}


function questionSwalItemSub(message, valor) {
	Swal.fire({
		title: '¿Estás seguro (a) ?',
		text: message,
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Si, Eliminar',
		cancelButtonText: 'No, Cancelar'
	}).then((result) => {
		if (result.value) {
			//console.log(valor.id);
			deleteItemSub(valor.id);
		} else {
			console.log("XD");
		}
	})
}

function questionSwalBajaChofer(message, valor) {
	Swal.fire({
		title: '¿Estás seguro (a) ?',
		text: message,
		icon: 'warning',
		title: 'Motivo de baja',
		input: 'text',
		inputPlaceholder: 'Motivo de baja',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Si, Eliminar',
		cancelButtonText: 'No, Cancelar',
		inputValidator: (value) => {
			if (!value) {
				return 'Se requiere un motivo de baja!'
			}
		}
	}).then((result) => {
		console.log(result);
		if (result.value) {
			//document.getElementById("eliminarHos"+valor.id).submit();			
			var baja_url = $("#baja_url").attr('href');
			window.location.href = baja_url + "/" + valor + "/" + result.value;
		} else {
			console.log("XD");
		}
	})
}