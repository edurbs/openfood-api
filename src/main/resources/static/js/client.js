
function consultRestaurants(){
    $.ajax({
        url: "http://api.openfood.local:8080/restaurantes",
        type: "get",

        success: function(response) {
            $("#content").text(JSON.stringify(response, null, 4));
        }
    });
}



function closeRestaurants(){
    $.ajax({
        url: "http://api.openfood.local:8080/restaurantes/1/fechamento",
        type: "put",

        success: function(response) {
            alert("Restaurant closed");
        }
    });
}

function fillTable(paymentMethods){

    $("#mytable tbody tr").remove();
    
    $.each(paymentMethods, function(i, paymentMethod){
        let linkAction = $("<a href='#'>")
            .text("Delete")
            .click(function(event){
                event.preventDefault();
                remove(paymentMethod);
            });
        
        let line = $("<tr>");
        line.append(
            $("<td>").text(paymentMethod.id),
            $("<td>").text(paymentMethod.descricao),
            $("<td>").append(linkAction)            
        );

        line.appendTo("#mytable");
    });
}

function listPaymentMethods(){
    $.ajax({
        url:"http://api.openfood.local:8080/formaspagamento",
        type: "get",
        success: function(response){
            fillTable(response);
        }
    });

}

function addPaymentMethod(){
    let paymentMethodJson = JSON.stringify({
        "descricao": $("#field-description").val()
    });
    
    $.ajax({
        url: "http://api.openfood.local:8080/formaspagamento",
        type: "post",
        data: paymentMethodJson,
        contentType: "application/json",

        success: function(response){
            alert("Payment method added.");
            listPaymentMethods();
        },

        error: function(error){
            if(error.status >= 400 && error.status <=499){
                let problem = JSON.parse(error.responseText);
                alert(problem.userMessage);
            }else{
                alert("Error adding the payment method");
            }
        }


    });
}


function remove(paymentMethod){
    $.ajax({
        url: "http://api.openfood.local:8080/formaspagamento/" + paymentMethod.id,
        type: "delete",
        success: function(response){
            alert("Deleted");
            listPaymentMethods();
        },
        error: function(error){
            if(error.status >= 400 && error.status <=499){
                let problem = JSON.parse(error.responseText);
                alert(problem.userMessage);
            }else{
                alert("Error deleting payment method");
            }
        }
    });
}

$("#button_req").click(consultRestaurants);
$("#button_close").click(closeRestaurants);
$("#button_paymets").click(listPaymentMethods);
$("#btn-addPayment").click(addPaymentMethod);