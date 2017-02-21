<?php
    $con = mysqli_connect("mysql.ctsaq7jylp2v.ap-northeast-1.rds.amazonaws.com",
    "root", "19871982", "db");

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userGender = $_POST["userGender"];
    $userMajor = $_POST["userMajor"];
    $userEmail = $_POST["userEmail"];

    $statement = mysqli_prepare($con, "INSERT INTO user VALUES (?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, 'sssss', $userID, $userPassword, $userGender, 
    $userMajor, $userEmail);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>