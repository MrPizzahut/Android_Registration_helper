<?php
	header("Content-Type: text/html; charset=UTF-8");
    $con = mysqli_connect("mysql.ctsaq7jylp2v.ap-northeast-1.rds.amazonaws.com",
    "root", "-------", "db");

    $userID = $_GET["userID"];

    $result = mysqli_query($con, "SELECT course.courseID, course.courseTime, course.courseProfessor,
    course.courseTitle, course.courseCredit 
    FROM user, course, schedule WHERE user.userID = '$userID' AND user.userID = schedule.userID AND
    schedule.courseID = course.courseID");
    $response = array();

    while($row = mysqli_fetch_array($result)){
        array_push($response, array("courseID"=>$row[0], "courseTime"=>$row[1],
        "courseProfessor"=>$row[2], "courseTitle"=>$row[3], "courseCredit"=>$row[4]));
    }

    echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
?>
