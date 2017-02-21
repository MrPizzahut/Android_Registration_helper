<?php

$servername = "mysql.ctsaq7jylp2v.ap-northeast-1.rds.amazonaws.com";

$username = "root";

$password = "19871982";

   

// Create connection

$conn = mysqli_connect($servername, $username, $password, "db");

   

// Check connection

if (!$conn) {

die("Connection failed: " . mysqli_connect_error());

}

echo "Connected successfully";

?>