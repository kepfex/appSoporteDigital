
function ocultarFiltros() {
    const filters = document.querySelectorAll('.ui-column-filter');

    if (filters) {
        filters.forEach(filter => {
            filter.style.display = 'none'; // Oculta visualmente
            filter.disabled = true;       // Deshabilita el input para evitar interacción
        });
    }
}
// Llama a la función automáticamente al cargar el DOM
document.addEventListener('DOMContentLoaded', function () {
    ocultarFiltros();
});
