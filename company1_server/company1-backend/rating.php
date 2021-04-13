<?php
$con=mysqli_connect("coms-510-01.cs.iastate.edu","Company1","Company@1","Company1");

// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$tutor = $_GET['tutorid'];
$rating = $_GET['rating'];
$desc= $_GET['description'];
$sql = "INSERT into tutor_rating values ('$tutor','$rating', '$desc')";
if (mysqli_query($con,$sql)) {
      echo "Successful";
   }
else {
  echo (mysqli_error($con));
}
?>
