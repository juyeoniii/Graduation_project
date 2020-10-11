<?php
    $con = mysqli_connect("localhost", "juyeon", "a05606wEijno6CoJ", "registerdb");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    
    
    $statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $usercode, $userID, $userPassword, $userName, $recommendID);

 
	$response = array();
	$response["success"] = false;	
		 
	
	while(mysqli_stmt_fetch($statement)) {
		$response["success"] = true;
		$response["usercode"] = $usercode;
		$response["userID"] = $userID;
		$response["userPassword"] = $userPassword;
		$response["userName"] = $userName;
		$response["recommendID"] = $recommendID;
	}
			
	echo json_encode($response);




?>