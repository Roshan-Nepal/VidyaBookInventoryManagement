// let counter = 1;
function addNewInput(){
    // if(counter > 10){
    //     document.getElementById("toManyInputs").innerText = "Cannot Add More Books";
    //     return;
    // }
    const newInputGroup = document.createElement('div');
    newInputGroup.classList.add('input-group');

    const newBookTitleInput = document.createElement('input');
    newBookTitleInput.setAttribute('type', 'text');
    newBookTitleInput.setAttribute('name', 'bookTitle[]');
    newBookTitleInput.setAttribute('placeholder', 'Book Title');
    newBookTitleInput.setAttribute('required','')

    const newBookAuthorInput = document.createElement('input');
    newBookAuthorInput.setAttribute('type', 'text');
    newBookAuthorInput.setAttribute('name', 'bookAuthor[]');
    newBookAuthorInput.setAttribute('placeholder', 'Book Author');
    newBookAuthorInput.setAttribute('required','')


    const newQuantityInput = document.createElement('input');
    newQuantityInput.setAttribute('type', 'number');
    newQuantityInput.setAttribute('name', 'quantity[]');
    newQuantityInput.setAttribute('placeholder', 'Quantity');
    newQuantityInput.setAttribute('required','')

    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Delete';
    deleteButton.setAttribute('type', 'button');

    deleteButton.setAttribute('class', 'btn-danger');
    deleteButton.addEventListener('click', () => deleteInputGroup(newInputGroup));
    newInputGroup.appendChild(deleteButton);

    newInputGroup.appendChild(newBookTitleInput);
    newInputGroup.appendChild(newBookAuthorInput)
    newInputGroup.appendChild(newQuantityInput);
    newInputGroup.appendChild(deleteButton);


    document.getElementById('input-container').appendChild(newInputGroup);
    // counter ++;
}
function deleteInputGroup(inputGroup) {
    document.getElementById('input-container').removeChild(inputGroup);
    // counter--;
}
