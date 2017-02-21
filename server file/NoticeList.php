<?php
    $con = mysqli_connect("mysql.ctsaq7jylp2v.ap-northeast-1.rds.amazonaws.com",
    "root", "-------", "db");
    $result = mysqli_query($con, "SELECT * FROM notice ORDER BY noticeDate DESC");
    $response = array();

    while($row = mysqli_fetch_array($result)){
        array_push($response, array("noticeContent"=>$row[0], "noticeName"=>$row[1],
        "noticeDate"=>$row[2]));
    }

    echo json_encode(array("response"=>$response));
    mysqli_close($con);
?>
