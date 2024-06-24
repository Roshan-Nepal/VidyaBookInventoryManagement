function validateSupplierForm() {
    clearSupplierErrors();

    const supplierName = document.getElementById("supplierName").value;
    const supplierEmail = document.getElementById("supplierEmail").value;
    const supplierContactNumber = document.getElementById("supplierContactNumber").value;

    let isValid = true;

    if (supplierName.trim() === "") {
        document.getElementById("supplierNameError").innerText = "SUPPLIER NAME IS REQUIRED";
        isValid = false;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(supplierEmail)) {
        document.getElementById("supplierEmailError").innerText = "INVALID EMAIL";
        isValid = false;
    }

    const contactNumberPattern = /^\d{10}$/;
    if (!contactNumberPattern.test(supplierContactNumber)) {
        document.getElementById("supplierContactNumberError").innerText = "CONTACT NUMBER SHOULD BE 10 DIGITS";
        isValid = false;
    }

    return isValid;
}

function clearSupplierErrors() {
    document.getElementById("supplierNameError").innerText = "";
    document.getElementById("supplierEmailError").innerText = "";
    document.getElementById("supplierContactNumberError").innerText = "";
}
