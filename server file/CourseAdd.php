<?php
    $con = mysqli_connect("mysql.ctsaq7jylp2v.ap-northeast-1.rds.amazonaws.com",
    "root", "19871982", "db");

    $userID = $_POST["userID"];
    $courseID = $_POST["courseID"];

    $statement = mysqli_prepare($con, "INSERT INTO schedule VALUES (?,?)");
    mysqli_stmt_bind_param($statement, "si", $userID, $courseID);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>