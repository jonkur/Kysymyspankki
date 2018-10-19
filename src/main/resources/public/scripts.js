/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function vastausFunc(event) {
    var vastausteksti = document.getElementById("vastausfield").value.trim();
    console.log("vastausteksti on " + vastausteksti);
    if (vastausteksti === "") {
        event.preventDefault();
        alert("Kaikkin kenttiin on kirjoitettava jotain!");
    }
}

function kysymysFunc(event) {
    var kurssi = document.getElementById("kurssifield").value.trim();
    var aihe = document.getElementById("aihefield").value.trim();
    var kysymys = document.getElementById("kysymysfield").value.trim();
    if (kurssi === "" || aihe === "" || kysymys === "") {
        event.preventDefault();
        alert("Kaikkin kenttiin on kirjoitettava jotain!");
    }
}