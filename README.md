const express=require("express");
const cors=require("cors");
const mysql=require("mysql2");
const path=require("path");
const { error } = require("console");

const app=express();
app.use(express.json());
app.use(cors());

const connectionDetails={
  host : "localhost",
  user : "root",
  password : "root123",
  database : "iacsd0923"
}

app.get("/products",(request,response)=>{
  const connection=mysql.createConnection(connectionDetails);
  var statement="select * from product";
  connection.query(statement,(error,result)=>{
    if(error==null){
      response.setHeader("Content-Type","application/json");
      var resultData=JSON.stringify(result);
      response.send(resultData);
      connection.end();
    }else{
      response.send(error);
    }
  });
});

app.post("/products",(request,response)=>{
  console.log(request.body);
  const connection=mysql.createConnection(connectionDetails);
  var statement=`insert into product values(default,'${request.body.producttitle}',${request.body.price},${request.body.stock});`;
  console.log(statement);
  connection.query(statement,(error,result)=>{
    if(error==null){
      var affectedrows=JSON.stringify(result);
      console.log(affectedrows);
      response.send(affectedrows);
      connection.end();
    }else{
      response.send(error);
    }
  });
});

app.delete("/products/:productid",(request,response)=>{
  console.log(request.params);
  const connection=mysql.createConnection(connectionDetails);
  var statement=`delete from product where productid=${request.params.productid};`;
  connection.query(statement,(error,result)=>{
    if(error==null){
      var resultData=JSON.stringify(result);
      response.send(resultData);
      connection.end();
    }else{
      response.send(error);
    }
  });
});

app.put("/products/:N",(request,response)=>{
  console.log(request.body);
  const connection=mysql.createConnection(connectionDetails);
  var statement=`update product set producttitle='${request.body.producttitle}',price=${request.body.price},stock=${reques.body.stock} where productid=${request.params.N};`;
  connection.query(statement,(error,result)=>{
    if(error==null){
      var affectedrows=JSON.stringify(result);
      response.send(affectedrows);
      connection.end();
    }else{
      response.send(error);
    }
  });
});

app.listen(5500,()=>{
  console.log("server started at port 5500.....");
});
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
<!-- views/products.ejs -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product List</title>
</head>
<body>
    <h1>Product List</h1>

    <ul>
        <% for (let i = 0; i < products.length; i++) { %>
            <li><%= products[i].producttitle %> - Price: <%= products[i].price %>, Stock: <%= products[i].stock %></li>
        <% } %>
    </ul>

    <a href="/add-product">Add Product</a>
</body>
</html>
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
<!-- views/add-product.ejs -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
</head>
<body>
    <h1>Add Product</h1>

    <form action="/add-product" method="post">
        <!-- Product form fields go here -->
        <!-- prod title,price,stock -->
        <table>
            <tr>
                <td>Product Title :</td>
                <td><input type="text" id="title" class="title"></td>
            </tr>
            <tr>
                <td>Product Price :</td>
                <td><input type="text" id="price" class="price"></td>
            </tr>
            <tr>
                <td>Product Stock :</td>
                <td><input type="text" id="stock" class="stock"></td>
            </tr>
            <tr>
                <td><button type="submit" id="btn" class="btn">Submit</button></td>
                <td><button type="reset" id="cancel" class="cancel">Cancel</button></td>
            </tr>
        </table>
    </form>

    <a href="/products">Back to Product List</a>
</body>
</html>
