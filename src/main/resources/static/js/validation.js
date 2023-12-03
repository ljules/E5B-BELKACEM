let forms=document.querySelectorAll("form.validation");
for(let form of forms)
{

    form.addEventListener("submit", function (event) {
        console.log(event);
        for (const input of event.target.querySelectorAll("form input,form select,form textarea")) {
            if (!input.checkValidity()) {
                event.preventDefault();
                if (!document.querySelector("#msg" + input.id)) {
                    input.classList.remove("is-valid");
                    input.classList.add("is-invalid");
                    let msg = document.createElement("div");
                    msg.id = `msg${input.id}`;
                    msg.classList.add('text-danger');
                    msg.innerText = input.validationMessage;
                    input.parentElement.insertBefore(msg, input);

                }
                else {
                    document.querySelector("#msg" + input.id).innerText = input.validationMessage;
                }
            }

            else {
                if (document.querySelector("#msg" + input.id)) {
                    console.log(input);
                    input.parentElement.removeChild(document.querySelector("#msg" + input.id));
                }
                input.classList.remove("is-invalid");
                input.classList.add("is-valid");
            }

        }
    })

    for (const input of document.querySelectorAll("form input,form select,form textarea")) {
        input.addEventListener('input', function () {

                if (!input.checkValidity()) {
                    input.classList.remove("is-valid");
                    input.classList.add("is-invalid");
                    if (!document.querySelector("#msg" + input.id)) {
                        let msg = document.createElement("div");
                        msg.id = `msg${input.id}`;
                        msg.classList.add('text-danger');
                        msg.innerText = input.validationMessage;
                        input.parentElement.insertBefore(msg, input);
                    } else {
                        document.querySelector("#msg" + input.id).innerText = input.validationMessage;
                    }
                }
                else {
                    if (document.querySelector("#msg" + input.id)) {
                        input.parentElement.removeChild(document.querySelector("#msg" + input.id));
                    }
                    input.classList.remove("is-invalid");
                    input.classList.add("is-valid");
                }
            }
        )}
}
