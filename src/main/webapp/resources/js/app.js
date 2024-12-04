
document.addEventListener("DOMContentLoaded", () => {
    const btnSubMenu = document.querySelectorAll('#sub-menu');
    const btnMenuOpenSidebar = document.querySelector('#boton-open-sidebar');
    const btnOcultarSideBar = document.querySelector('#ocultar-sidebar');

    if (btnSubMenu) {
        btnSubMenu.forEach(boton => {
            boton.addEventListener('click', (e) => {
                const targetElement = e.currentTarget;

                // Seleccionar el <ul> asociado al botón clicado (hermano del botón)
                const subMenu = targetElement.nextElementSibling;

                // Alternar entre max-h-0 (oculto) y una altura suficiente (desplegado)
                if (subMenu.classList.contains('max-h-0')) {
                    subMenu.classList.remove('max-h-0');
                    subMenu.classList.add('max-h-[500px]');
                } else {
                    subMenu.classList.remove('max-h-[500px]');
                    subMenu.classList.add('max-h-0');
                }

                const arrowIcon = targetElement.querySelector('.arrow');
                arrowIcon.classList.toggle('rotate-90')
            })
        });
    }

    if (btnMenuOpenSidebar) {
        const sideBar = document.querySelector('#side-bar')
        btnMenuOpenSidebar.addEventListener('click', ()=> {
            sideBar.classList.add('left-0');
        })
    }
    if (btnOcultarSideBar) {
        const sideBar = document.querySelector('#side-bar')
        btnOcultarSideBar.addEventListener('click', ()=> {
            if (sideBar) {
                sideBar.classList.remove('left-0');
            }
        })
    }
});