<?php
    require_once '../datos/Conexion.clase.php';
    class Entrenop extends Conexion{
  
        private $id;
        private $nombre;
        private $fecha;
        private $hinicio;
        private $hfin;
        private $lugar;
        private $semana_id;
        private $descripcion;
        
        function getId() {
            return $this->id;
        }

        function getNombre() {
            return $this->nombre;
        }

        function getFecha() {
            return $this->fecha;
        }

        function getHinicio() {
            return $this->hinicio;
        }

        function getHfin() {
            return $this->hfin;
        }

        function getLugar() {
            return $this->lugar;
        }

        function getSemana_id() {
            return $this->semana_id;
        }

        function getDescripcion() {
            return $this->descripcion;
        }

        function setId($id) {
            $this->id = $id;
        }

        function setNombre($nombre) {
            $this->nombre = $nombre;
        }

        function setFecha($fecha) {
            $this->fecha = $fecha;
        }

        function setHinicio($hinicio) {
            $this->hinicio = $hinicio;
        }

        function setHfin($hfin) {
            $this->hfin = $hfin;
        }

        function setLugar($lugar) {
            $this->lugar = $lugar;
        }

        function setSemana_id($semana_id) {
            $this->semana_id = $semana_id;
        }

        function setDescripcion($descripcion) {
            $this->descripcion = $descripcion;
        }

                
        public function agregar() {
            
            $nombre = $this->getNombre();
            $fecha = $this->getFecha();
            $hinicio = $this->getHinicio();
            $hfin = $this->getHfin();
            $lugar= $this->getLugar();
            $sem= $this->getSemana_id();
            $descrip= $this->getDescripcion();
            
            $sql = "INSERT INTO entrenoprogramados(nombre, fecha, horainicio, horafin, lugar, semana_id, descripcion)VALUES "
                    . "('$nombre','$fecha','$hinicio','$hfin','$lugar',$sem,'$descrip')";         
           
            $sentencia = $this->dblink->prepare($sql);
            
            $resultado = $sentencia->execute();
            
            if ($resultado != 1){
                //ocurrio un error al insertar
                return FALSE;
            }
            
            //InsertÃ³ correctamente
            return TRUE;
             
        }
        
        
        
        public function listar(){
            $sql = "SELECT * FROM entrenoprogramados";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        }    
    }
?>
