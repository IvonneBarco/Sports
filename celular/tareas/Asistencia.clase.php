<?php
    require_once '../datos/Conexion.clase.php';
    class Asistencia extends Conexion{
  
        private $id;        
        private $entreno;
        private $hora;
        private $athleta;
        
        
        function getId() {
            return $this->id;
        }

        function getEntreno() {
            return $this->entreno;
        }

        function getHora() {
            return $this->hora;
        }

        function getAthleta() {
            return $this->athleta;
        }

        function setId($id) {
            $this->id = $id;
        }

        function setEntreno($entreno) {
            $this->entreno = $entreno;
        }

        function setHora($hora) {
            $this->hora = $hora;
        }

        function setAthleta($athleta) {
            $this->athleta = $athleta;
        }

        
        
                
        public function agregar() {            
            
            $hoy= getdate();
            
            $this->hora=($hoy['hours'].":".$hoy['minutes'].":".$hoy['seconds']);                  
            
            $hora= $this->getHora();
            $enteno= $this->getEntreno();
            $athleta = $this->getAthleta();
            
            $sql = "INSERT INTO `asistencias`(`entreno_id`, `hora`, `athletes_id`) VALUES "
                    . "('$enteno','$hora','$athleta')";         
           
            $sentencia = $this->dblink->prepare($sql);
            
            $resultado = $sentencia->execute();
            
            if ($resultado != 1){
                //ocurrio un error al insertar
                return FALSE;
            }
            
            //InsertÃ³ correctamente
            return TRUE;
            
        }
        
        public function pararentreno($id) {            
            
            $hoy= getdate();
            
            $hfin= $hoy['hours'].":".$hoy['minutes'].":".$hoy['seconds'];
            
            $sql = "UPDATE `entrenos` SET `horafin`='$hfin' WHERE `id`= '$id'";        
           
            $sentencia = $this->dblink->prepare($sql);
            
            $resultado = $sentencia->execute();
            
            if ($resultado != 1){
                //ocurrio un error al insertar
                return FALSE;
            }
            
            //InsertÃ³ correctamente
            return TRUE;
            
        }
        
        
        
        
    }
?>
