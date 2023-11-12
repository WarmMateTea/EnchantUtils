const buttonListarEncantamentos = document.querySelector("#listar-encantamentos");
const buttonCadastrarEncantamentos = document.querySelector("#cadastrar-encantamentos");

buttonListarEncantamentos.addEventListener("click", () => {
    window.location.href = "home/listaEncantamentos";
});

buttonCadastrarEncantamentos.addEventListener("click", () => {
    window.location.href = "home/registroEncantamentos";
});