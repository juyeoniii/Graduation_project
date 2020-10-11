<?php 

	/*
	* Created by Belal Khan
	* website: www.simplifiedcoding.net 
	* Retrieve Data From MySQL Database in Android
	*/
	
	//database constants
	define('DB_HOST', 'localhost');
	define('DB_USER', 'juyeon');
	define('DB_PASS', 'a05606wEijno6CoJ');
	define('DB_NAME', 'registerdb');
	
	define('UPLOAD_PATH', 'uploads/');
	
	//connecting to database and getting the connection object
	$conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	
	//Checking if any error occured while connecting
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}
	
	$server_ip = gethostbyname(gethostname());
	//creating a query
	$stmt = $conn->prepare("SELECT userID, image FROM userimages;");
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($userID, $image);
	
	$userimages = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
		$temp['image'] = 'http://' . $server_ip . '/MyApi/'. UPLOAD_PATH . $image; 
		array_push($userimages, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($userimages);