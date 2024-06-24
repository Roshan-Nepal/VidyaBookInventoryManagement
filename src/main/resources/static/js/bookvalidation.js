function validateForm() {

    clearErrors();

    const title = document.getElementById("title").value;
    const author = document.getElementById("author").value;
    const publishedDate = document.getElementById("publishedDate").value;
    const price = document.getElementById("price").value;
    const quantity = document.getElementById("quantity").value;
    const description = document.getElementById("description").value;

    let isValid = true;

    if (title.trim() === "") {
        document.getElementById("titleError").innerText = "Title is required";
        isValid = false;
    }

    if (author.trim() === "") {
        document.getElementById("authorError").innerText = "Author is required";
        isValid = false;
    }

    if (publishedDate.trim() === "") {
        document.getElementById("publishedDateError").innerText = "Published Date is required";
        isValid = false;
    }


    if (isNaN(parseFloat(price)) || parseFloat(price) <= 0) {
        document.getElementById("priceError").innerText = "Price must be a valid number greater than 0";
        isValid = false;
    }


    if (isNaN(parseInt(quantity)) || parseInt(quantity) <= 0) {
        document.getElementById("quantityError").innerText = "Quantity must be a valid number greater than 0";
        isValid = false;
    }

    if (description.trim() === "") {
        document.getElementById("descriptionError").innerText = "Description is required";
        isValid = false;
    }

    return isValid;
}

function clearErrors() {
    document.getElementById("titleError").innerText = "";
    document.getElementById("authorError").innerText = "";
    document.getElementById("publishedDateError").innerText = "";
    document.getElementById("priceError").innerText = "";
    document.getElementById("quantityError").innerText = "";
    document.getElementById("descriptionError").innerText = "";
}
