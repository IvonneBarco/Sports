<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $imagen= $_POST['foto'];
                $nombre = $_POST['nombre'];
 
 require_once('dbConnect.php');
 
 $sql ="SELECT id FROM frutas ORDER BY id ASC";
 
 $res = mysqli_query($con,$sql);
 
 $id = 0;
 
 while($row = mysqli_fetch_array($res)){
 $id = $row['id'];
 }
 
 $path = "uploads/$id.png";
 
 $actualpath = "http://www.iucesmag.edu.co/celular/$path";
 
 $sql = "INSERT INTO frutas (foto,nombre) VALUES ('$actualpath','$nombre')";
 
 if(mysqli_query($con,$sql)){
 file_put_contents($path,base64_decode($imagen));
 echo "Subio imagen Correctamente";
 }
 
 mysqli_close($con);
 }else{
 echo "Error";
 }
