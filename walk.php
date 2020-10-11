<?php 
	
	//Constants for database connection
	define('DB_HOST','localhost');
	define('DB_USER','juyeon');
	define('DB_PASS','a05606wEijno6CoJ');
	define('DB_NAME','registerdb');

	//We will upload files to this folder
	//So one thing don't forget, also create a folder named uploads inside your project folder i.e. MyApi folder

	//connecting to database 
	$conn = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME) or die('Unable to connect');
	
	$userID = $_POST["userID"];
	$userPoint = $_POST["userPoint"];
	
	mysqli_query($conn, "set names utf8");

	//An array to display the response
	$response = array();


	try{
		
		$stmt= mysqli_prepare($conn, "UPDATE userpoint SET userPoint = ?+userPoint WHERE userID = (?)");
		
		mysqli_stmt_bind_param($stmt, "ss", $userPoint, $userID);
		
		if(mysqli_stmt_execute($stmt)){
			$response["success"] = true;
		}else{
			throw new Exception("Could not upload file");
			}

	}catch(Exception $e){
		$response['error'] = true;
	}


	
	//displaying the response in json 
	header('Content-Type: application/json');
	echo json_encode($response);