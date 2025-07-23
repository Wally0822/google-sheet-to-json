const output = document.getElementById("output");

function fetchJson() {
  fetch("http://localhost:4567/api/fetch")
    .then(res => res.json())
    .then(data => {
      output.textContent = JSON.stringify(data, null, 2);
    });
}

function generateClass() {
  fetch("http://localhost:4567/api/generate")
    .then(res => res.text())
    .then(msg => {
      output.textContent = msg;
    });
}

function downloadJson() {
  window.open("http://localhost:4567/api/download");
}
