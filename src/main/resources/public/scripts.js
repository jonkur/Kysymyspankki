/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


document.getElementById("kysymyslahetabtn").onclick = function(event) {
    var kurssi = document.getElementById("kurssifield").value;
    var aihe = document.getElementById("aihefield").value;
    var kysymys = document.getElementById("kysymysfield").value;
    if (kurssi == "" || aihe == "" || kysymys == "") {
        event.preventDefault();
        alert("Kaikkin kenttiin on kirjoitettava jotain!");
    }
}