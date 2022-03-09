var password = document.getElementById("password")
  , confirm_password = document.getElementById("confirm_password");

function validatePassword(){
  if(password.value != confirm_password.value) {
    confirm_password.setCustomValidity("Passwords Don't Match");
  } else {
    confirm_password.setCustomValidity('');
  }
}
password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;

var name = document.getElementById("name")

    function validateName(){
    if(name.value == ""){
        alert("Must insert a name in the field")
        name.setCustomValidity("Must insert a name");
        }
        return;
    }

name.onchange = validateName;