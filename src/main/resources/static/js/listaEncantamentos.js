const enchantments = document.querySelectorAll(".encantamentosWrapper");
const returnButton = document.querySelector("#return");
const editButton = document.querySelector("#edit-enchantment");
const resetTableButton = document.querySelector("#reset-table");
const deleteButton = document.querySelector("#delete");
const createButton = document.querySelector("#create");
var selectedItemId = null;

for (let i = 0; i < enchantments.length; i++) {
    enchantments[i].addEventListener('click', () => {
        selectedItemId = enchantments[i].getAttribute('value');
    });
};

createButton.addEventListener("click", () => {
    window.location.href = "formEncantamento";
});

resetTableButton.addEventListener('click', () => {
    let confirmationModal = document.querySelector("#confirmation-modal");
    confirmationModal.style.display = "flex"
    document.querySelector("#modal-title").textContent = "Tem certeza que deseja resetar a tabela?";
    let yesButton = document.querySelector("#modal-yes");
    let noButton = document.querySelector("#modal-no");

    yesButton.addEventListener('click', () => {
        window.location.href= 'resetTable';
    }, {once: true});

    noButton.addEventListener('click', () => {
        confirmationModal.style.display = "none";
    }, {once: true});
});

editButton.addEventListener("click", () => {
    if (!selectedItemId) {
        return;
    }
    window.location.href = `formEncantamento?id=${selectedItemId}`;
})

returnButton.addEventListener("click", () => {
    window.location.href = "/home";
});

deleteButton.addEventListener("click", () => {
    if (!selectedItemId) {
        return;
    }

    let confirmationModal = document.querySelector("#confirmation-modal");
    confirmationModal.style.display = "flex"
    document.querySelector("#modal-title").textContent = "Tem certeza que deseja excluir este encantamento?";

    let yesButton = document.querySelector("#modal-yes");
    let noButton = document.querySelector("#modal-no");

    yesButton.addEventListener('click', () => {
        window.location.href = `deleteEncantamento?id=${selectedItemId}`;
    }, {once: true});

    noButton.addEventListener('click', () => {
        confirmationModal.style.display = "none";
    }, {once: true});

});