function togglePassword() {

            console.log("Toggle function called");

            const password = document.getElementById("password");
            const eyeOpen = document.getElementById("eyeOpen");
            const eyeClosed = document.getElementById("eyeClosed");

            if (password.type === "password") {
                password.type = "text";
                eyeOpen.classList.add("hidden");
                eyeClosed.classList.remove("hidden");
            } else {
                password.type = "password";
                eyeOpen.classList.remove("hidden");
                eyeClosed.classList.add("hidden");
            }
        }