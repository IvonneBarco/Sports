<?php
    require_once '../datos/Conexion.clase.php';
    class Ano extends Conexion{
  
        private $id;
        private $nombre;
        private $filename;
        private $dir;
        private $entrenadore_athlete_id;
        
        function getId() {
            return $this->id;
        }

        function getNombre() {
            return $this->nombre;
        }

        function getFilename() {
            return $this->filename;
        }

        function getDir() {
            return $this->dir;
        }

        function getEntrenadore_athlete_id() {
            return $this->entrenadore_athlete_id;
        }

        function setId($id) {
            $this->id = $id;
        }

        function setNombre($nombre) {
            $this->nombre = $nombre;
        }

        function setFilename($filename) {
            $this->filename = $filename;
        }

        function setDir($dir) {
            $this->dir = $dir;
        }

        function setEntrenadore_athlete_id($entrenadore_athlete_id) {
            $this->entrenadore_athlete_id = $entrenadore_athlete_id;
        }

        
        public function agregar() {
            $sql = "INSERT INTO anos(nombre, filename, dir, entrenadore_athlete_id) VALUES (:nom, :fil, :dir, :entre)";
            
            $sentencia = $this->dblink->prepare($sql);
            
            $nombre = $this->getNombre();
            $filename = $this->getFilename();
            $dir = $this->getDir();
            $entrenadore_athlete_id = $this->getEntrenadore_athlete_id();
            
            // $stmt->bindParam(':name', $userName);
            $sentencia->bindParam(":nom", $nombre  );
            $sentencia->bindParam(":fil",  $filename);
            $sentencia->bindParam(":dir", $dir );
            $sentencia->bindParam(":entre", $entrenadore_athlete_id );
           // $sentencia->bindParam(":fot", $this->getFoto() );
            
            $resultado = $sentencia->execute();
            
            if ($resultado != 1){
                //ocurrio un error al insertar
                return FALSE;
            }
            
            //InsertÃ³ correctamente
            return TRUE;
        }
        
        public function ultimoid() {
            $sql = "SELECT id FROM `anos` ORDER BY id DESC";            
            
            $sentencia = $this->dblink->prepare($sql);
            $resultado = $sentencia->execute();
 
            $resultado = $sentencia->fetchObject();
            
            return $resultado->id;
        }
        
        public function listar(){
            $sql = "SELECT * FROM users";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        }    
    }
?>
