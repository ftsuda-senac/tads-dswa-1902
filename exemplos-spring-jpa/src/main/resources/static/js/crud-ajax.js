// http://youmightnotneedjquery.com/#ready
// EXECUTA A FUNÇÃO fn SOMENTE APÓS CARREGAR O DOM HTML
function ready(fn) {
    if (document.attachEvent ? document.readyState === "complete" : document.readyState !== "loading") {
        fn();
    } else {
        document.addEventListener('DOMContentLoaded', fn);
    }
}

function obterImagens(imagens) {
	var imagemHtml = "";
	if (imagens != null) {
		for (var i = 0; i < imagens.length; i++) {
			imagemHtml += "<img src=\"" + imagens[i].nomeArquivo + "\" alt=\"" + imagens[i].legenda + "\" width=\"100\">";
		}
	}
	return imagemHtml;
}

function obterItem(item) {
    return "<li><div>" +
            "<h3>" + item.nome + "</h3>" +
            "<p>" + item.descricao + "</h3>" +
            "<p>Preço compra: " + item.precoCompra + "</p>" +
            "<p>Preço venda: " + item.precoVenda + "</p>" +
            obterImagens(item.imagens) +
            "</div></li>";
}

function listarProdutos(ev) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'rest/produto', true);
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 400) {
            let data = JSON.parse(xhr.responseText);
            let container = document.querySelector("#listaResultado");
            for (let i = 0; i < data.length; i++) {
                container.insertAdjacentHTML("beforeend", obterItem(data[i]));
            }
        }
    };
    xhr.send();
}

ready(function () {
	document.getElementById("btnLista").addEventListener("click", listarProdutos);
});