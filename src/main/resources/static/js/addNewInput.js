var counter = 1;
function addNewInput(){

    document.getElementById('addMoreInput'+counter).innerText="<div>\n" +
        "            <label for=\'quantity'+ counter +'\'>Book Title:</label>\n" +
        "            <input type=\"text\" id=\"quantity\">\n" +
        "        </div>"
    counter ++;
}