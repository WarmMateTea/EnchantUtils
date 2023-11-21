const enchantments = document.querySelectorAll(".encantamentosWrapper");
const returnButton = document.querySelector("#return");
const editButton = document.querySelector("#edit-enchantment");
var selectedItemId = null;

for (let i = 0; i < enchantments.length; i++) {
    enchantments[i].addEventListener('click', () => {
        selectedItemId = enchantments[i].getAttribute('value');
    });
};

editButton.addEventListener("click", () => {
    if (!selectedItemId) {
        return;
    }
    window.location.href = `formEncantamento?id=${selectedItemId}`;
})

returnButton.addEventListener("click", () => {
    window.location.href = "/home";
});