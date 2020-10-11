<?php 
	
	//Constants for database connection
	define('DB_HOST','localhost');
	define('DB_USER','juyeon');
	define('DB_PASS','a05606wEijno6CoJ');
	define('DB_NAME','registerdb');

	//We will upload files to this folder
	//So one thing don't forget, also create a folder named uploads inside your project folder i.e. MyApi folder
	
	define('UPLOAD_PATH', 'uploads/');
	
	//connecting to database 
	$conn = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME) or die('Unable to connect');
	
	$userID = $_POST["userID"];
	$image = $_FILES['pic']['name'];
	
	mysqli_query($conn, "set names utf8");

	//An array to display the response
	$response = array();
	$server_ip = gethostbyname(gethostname());


	try{
		move_uploaded_file($_FILES['pic']['tmp_name'], UPLOAD_PATH . $_FILES['pic']['name']);
		
		$state = mysqli_prepare($conn, "INSERT INTO userprofile(userID, image) VALUES (?,?)");
		
		mysqli_stmt_bind_param($state, "ss", $userID, $_FILES['pic']['name']);
	
		
		if(mysqli_stmt_execute($state)){
			$response['error'] = false;
			$response['message'] = "업로드 완료";
		}else{
			throw new Exception("Could not upload filefdf");
			}

	}catch(Exception $e){
		$response['error'] = true;
	}

	
	
	//displaying the response in json 
	header('Content-Type: application/json');
	echo json_encode($response);
