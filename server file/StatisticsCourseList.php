<?php
	header("Content-Type: text/html; charset=UTF-8");
    $con = mysqli_connect("mysql.ctsaq7jylp2v.ap-northeast-1.rds.amazonaws.com",
    "root", "19871982", "db");

    $userID = $_GET["userID"];

    $result = mysqli_query($con, "SELECT course.courseID, course.courseGrade, course.courseTitle, 
    course.courseDivide, course.coursePersonnel, COUNT(schedule.courseID), course.courseCredit
    FROM course, schedule WHERE schedule.courseID IN (SELECT schedule.courseID FROM schedule 
    WHERE schedule.userID = '$userID') AND schedule.courseID = course.courseID GROUP BY schedule.courseID");
    $response = array();

    while($row = mysqli_fetch_array($result)){
        array_push($response, array("courseID"=>$row[0], "courseGrade"=>$row[1],
        "courseTitle"=>$row[2], "courseDivide"=>$row[3], "coursePersonnel"=>$row[4]
        , "COUNT(schedule.courseID)"=>$row[5], "courseCredit"=>$row[6]));
    }

    echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
?>