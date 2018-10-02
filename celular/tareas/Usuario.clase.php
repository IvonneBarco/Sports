<?php
    require_once '../datos/Conexion.clase.php';
    class Usuario extends Conexion{
  
        private $id;
        private $username;
        private $role;
        private $created;
        private $modified;
        private $cedula;
        private $nombre;
        private $apellido;
        private $fnacimiento;
        private $sexo;
        private $telefono;
        private $direccion;
        private $estadocivil;
        private $numhijos;
        private $munnacimiento;
        private $depnacimiento;
        private $paisnacimiento;
        private $regimensalud;
        private $epservicio_id;
        private $nivelriesgo;
        private $riesgo_id;
        private $pensione_id;
        private $actividad;
        private $empresa;
        private $dirempresa;
        private $telempresa;
        private $horarios;

        
        public function agregar() {
            
            
        }
        
        public function listar(){
            $sql = "SELECT * FROM users";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        }    
    }
?>
