<?php
    require_once '../datos/Conexion.clase.php';
    
    class Semana extends Conexion{
  
        private $id;
        private $nombre;
        private $filename;
        private $dir;
        private $mese_id;
        
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

        function getMese_id() {
            return $this->mese_id;
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

        function setMese_id($mese_id) {
            $this->mese_id = $mese_id;
        }

        
        
        public function agregar() {
            $sql = "INSERT INTO semanas(nombre, filename, dir, mese_id) VALUES (:nom, :fil, :dir, :entre)";
            
            $sentencia = $this->dblink->prepare($sql);
            
            $nombre = $this->getNombre();
            $filename = $this->getFilename();
            $dir = $this->getDir();
            $mese_id = $this->getMese_id();
            
            // $stmt->bindParam(':name', $userName);
            $sentencia->bindParam(":nom", $nombre  );
            $sentencia->bindParam(":fil",  $filename);
            $sentencia->bindParam(":dir", $dir );
            $sentencia->bindParam(":entre", $mese_id );
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
            $sql = "SELECT id FROM `semanas` ORDER BY id DESC";            
            
            $sentencia = $this->dblink->prepare($sql);
            $resultado = $sentencia->execute();
 
            $resultado = $sentencia->fetchObject();
            
            return $resultado->id;
        }
        
        public function listar(){
            $sql = "SELECT * FROM semanas";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        }    
    }
?>
