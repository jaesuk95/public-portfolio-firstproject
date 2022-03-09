        document.getElementById("editProfile").addEventListener("click", function () {
            document.querySelector(".popup").style.display = "flex";
        })

        // same logic as above, when users click the "x" button, it will immediately change the display to none
        document.querySelector(".signup-close").addEventListener("click", function () {
            document.querySelector(".popup").style.display = "none";
        })