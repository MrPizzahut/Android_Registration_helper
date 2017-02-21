<?php
    $con = mysqli_connect("mysql.ctsaq7jylp2v.ap-northeast-1.rds.amazonaws.com",
    "root", "19871982", "db");

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];

    $statement = mysqli_prepare($con, "SELECT userID, userPassword FROM user WHERE userID = ? AND
     userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPassword);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["userID"] = $userID;
    }

    echo json_encode($response);
?>