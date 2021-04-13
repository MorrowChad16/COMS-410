<?php
$con=mysqli_connect("coms-510-01.cs.iastate.edu","Company1","Company@1","Company1");

// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$userid = $_GET['userid'];
$tutor=$_GET['tutorid'];
$course=$_GET['courseid'];
$date =$_GET['date'];
$ftime=$_GET['ftime'];
$etime= $_GET['etime'];
//$date =date("Y-m-d", strtotime($_GET['date']));
//$ftime=date("Y-m-d H:i:s", strtotime($_GET['ftime']));
//$etime= date("Y-m-d H:i:s", strtotime($_GET['etime']));
$cost= $_GET['cost'];
$sql = "INSERT into requestTutor values ('$userid','$tutor', '$course', '$date','$ftime','$etime','$cost','0')";
if (mysqli_query($con,$sql)) {
      echo "Successful";
   }
else {
  echo (mysqli_error($con));
}
?>
