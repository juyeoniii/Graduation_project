<?php
$con=mysqli_connect("localhost","juyeon","a05606wEijno6CoJ","registerdb");
 
if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$userID = $_GET['userID'];


$result = mysqli_query($con,"SELECT userPoint FROM userpoint where userID='$userID'");

$row = mysqli_fetch_array($result);

$data = $row[0];

 
if($data){
echo $data;
}

mysqli_close($con);
?>