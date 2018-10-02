<?php
    class Conexion {
        protected $dblink;
                
        function __construct() {
            $servidor = "mysql:host=localhost;dbname=sporte";
            $usuario = "d3p0rt3s";
            $clave = "6LU82bhEprUeCXVm";
            
            $this->dblink = new PDO($servidor, $usuario, $clave);
            $this->dblink->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            $this->dblink->exec("SET NAMES utf8");
        }
        
}
?>
