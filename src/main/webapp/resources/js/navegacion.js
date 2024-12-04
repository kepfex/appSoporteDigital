document.addEventListener("DOMContentLoaded", () => {
    const dropdown = document.getElementById("profile-menu");
    const button = document.getElementById("avatar-btn");

    if (dropdown && button) {
        const toggleDropdown = () => {
            const isOpen = dropdown.classList.contains("opacity-100");
            dropdown.classList.toggle("opacity-100", !isOpen);
            dropdown.classList.toggle("scale-100", !isOpen);
            dropdown.classList.toggle("opacity-0", isOpen);
            dropdown.classList.toggle("scale-0", isOpen);
        };

        button.addEventListener("click", (event) => {
            event.stopPropagation();
            toggleDropdown();
        });

        document.body.addEventListener("click", (event) => {
            if (!dropdown.contains(event.target) && !button.contains(event.target)) {
                dropdown.classList.add("opacity-0", "scale-0");
                dropdown.classList.remove("opacity-100", "scale-100");
            }
        });
    }
});
