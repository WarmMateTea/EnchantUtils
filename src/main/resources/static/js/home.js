const buttonListarEncantamentos = document.querySelector("#listar-encantamentos");
const buttonCadastrarEncantamentos = document.querySelector("#cadastrar-encantamentos");
const buttonEncantarItem = document.querySelector("#encantar-item");

buttonListarEncantamentos.addEventListener("click", () => {
    window.location.href = "home/listaEncantamentos";
});

buttonCadastrarEncantamentos.addEventListener("click", () => {
    window.location.href = "home/formEncantamento";
});

buttonEncantarItem.addEventListener("click", () => {
    window.location.href = "home/encantar";
});