$(document).ready(function () {

    $(".product-item").on("click",(event)=>updateItemsInStock(event.target.id, 1));

});



// Function to handle the update of items in stock
function updateItemsInStock(productId, decreaseAmount) {
    console.log("update");
    console.log(productId, decreaseAmount);
    $.ajax(
        {
        url: `http://localhost:8080/apill/api/product/${productId}`,
        type: "GET",
        dataType: "json",
        success: function (data) {
            console.log("Product Data:");
            const { items_in_stock, item_name, price } = data;

            if (items_in_stock - decreaseAmount < 0) {
                alert("Item out of stock");
            } else {
                // Update items in stock
                const updatedItemsInStock = items_in_stock - decreaseAmount;
                $.ajax({
                    url: `http://localhost:8080/apill/api/product/${productId}`,
                    type: "PUT",
                    dataType: "json",
                    contentType: "application/json",
                    data: JSON.stringify({
                        item_name: item_name,
                        items_in_stock: updatedItemsInStock,
                        price: price,
                    }),
                    success: function (updatedData) {
                        console.log("Product Updated Successfully:", updatedData);
                        alert("Product Updated Successfully");
                        //window.location.replace("checkout.html");
                        // Handle success, update UI, etc.
                    },
                    error: function (xhr, status, error) {
                        console.error("Error updating product:", status, error);
                        // Handle error, show error message, etc.
                    }
                });
            }
        },
        error: function (xhr, status, error) {
            xhr.statusCode().then(function (status) {
                console.log(status);
            });
            console.error("Error fetching product:", status, error, xhr.statusCode());
            // Handle error, show error message, etc.
        }
    });
}


  