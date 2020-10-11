<?php 
    $con = mysqli_connect("localhost", "juyeon", "a05606wEijno6CoJ", "registerdb");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userName = $_POST["userName"];
    $userID2 = $_POST["recommendID"];
	$usercode = $_POST["usercode"];
	$userPoint = $_POST["userPoint"];
	
	//$encrypted_passwd = password_hash($userPassword, PASSWORD_DEFAULT);


    $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?,?,?,?,?)");
	$state = mysqli_prepare($con, "INSERT INTO USERGROUP VALUES (?,?)");
	$st = mysqli_prepare($con, "INSERT INTO userpoint VALUES (?,0+?)");
	$s = mysqli_prepare($con, "INSERT INTO userpoint VALUES (?,500+?)");
	
    mysqli_stmt_bind_param($statement, "issss",$usercode, $userID, $userPassword, $userName, $userID2);
	mysqli_stmt_bind_param($state, "is",$usercode, $userID);
	mysqli_stmt_bind_param($st, "si", $userID, $userPoint);
	mysqli_stmt_bind_param($s, "si", $userID2, $userPoint);
    mysqli_stmt_execute($statement);
	mysqli_stmt_execute($state);
	mysqli_stmt_execute($st);
	mysqli_stmt_execute($s);

    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);



?>