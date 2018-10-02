<?php 
 define('HOST','mysql.localhost');
 define('USER','d3p0rt3s');
 define('PASS','6LU82bhEprUeCXVm');
 define('DB','sporte');
 
 $con = mysqli_connect(HOST,USER,PASS,DB) 
 or die('unable to connect to db');
