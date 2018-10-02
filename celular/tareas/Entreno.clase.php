<?php
    require_once '../datos/Conexion.clase.php';
    class Entreno extends Conexion{
  
        private $id;        
        private $fecha;
        private $hinicio;
        private $hfin;
        private $gps;
        private $entrenop;
        private $descripcion;
        
        function getId() {
            return $this->id;
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

        function getGps() {
            return $this->gps;
        }

        function getEntrenop() {
            return $this->entrenop;
        }

        function getDescripcion() {
            return $this->descripcion;
        }

        function setId($id) {
            $this->id = $id;
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

        function setGps($gps) {
            $this->gps = $gps;
        }

        function setEntrenop($entrenop) {
            $this->entrenop = $entrenop;
        }

        function setDescripcion($descripcion) {
            $this->descripcion = $descripcion;
        }

        
                
        public function agregar() {            
            
            $hoy= getdate();
            
            $this->setFecha($hoy['year']."-".$hoy['mon']."-".$hoy['mday']);
            $this->setHinicio($hoy['hours'].":".$hoy['minutes'].":".$hoy['seconds']);
            $this->setHfin($hoy['hours'].":".$hoy['minutes'].":".$hoy['seconds']);                        
            
            $fecha= $this->getFecha();
            $hinicio = $this->getHinicio();
            $hfin= $this->getHfin();
            $lugar= $this->getGps();
            $entre= $this->getEntrenop();
            $descrip= $this->getDescripcion();
            
            $sql = "INSERT INTO `entrenos`(`horainicio`, `horafin`, `fecha`, `gps`, `entrenoprogramado_id`, `descripcion`) VALUES "
                    . "('$hinicio','$hfin','$fecha','$lugar',$entre,'$descrip')";         
           
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
        
        /*
        public function consultarid() { 
            
            $fecha= $this->getFecha();
            $hinicio = $this->getHinicio();
            $hfin= $this->getHfin();
            $lugar= $this->getGps();
            $entre= $this->getEntrenop();
            $descrip= $this->getDescripcion();
            
            $sql = "SELECT `id` FROM `entrenos` WHERE `horainicio`='$hinicio' and `horafin`='$hfin' and `fecha` = '$fecha' and `gps`= '$lugar' and `entrenoprogramado_id`= '$entre' and `descripcion`='$descrip'";
            
            //$sentencia = $this->dblink->prepare($sql);
            
            //$sentencia = $sentencia->execute();
            //$resultado = $sentencia->fetchObject();
            
            //return $resultado->id;
            
            return $sql;
        }
        */
        
        public function consultarid() { 
            
            $fecha= $this->getFecha();
            $hinicio = $this->getHinicio();
            $hfin= $this->getHfin();
            $lugar= $this->getGps();
            $entre= $this->getEntrenop();
            $descrip= $this->getDescripcion();
            
            $sql = "SELECT `id` FROM `entrenos` WHERE horainicio=:hini and horafin=:hfin and fecha=:fec and gps= :gps "
                    . "and entrenoprogramado_id=:entre and descripcion=:des";
            
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":hini", $hinicio);
            $sentencia->bindParam(":hfin", $hfin);
            $sentencia->bindParam(":fec", $fecha);
            $sentencia->bindParam(":gps", $lugar);
            $sentencia->bindParam(":entre", $entre);
            $sentencia->bindParam(":des", $descrip);
            $sentencia->execute();
            
            $resultado = $sentencia->fetchObject();
            
            return $resultado->id;
            
            
        }
        
        
        
        public function listar(){
            $sql = "SELECT * FROM entrenoprogramados";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        } 
        
        public function listarAthletas($id){
            $sql = "SELECT `Athlete`.`id` as athlete, `User`.`nombre` ,`User`.`apellido` FROM `sporte`.`entrenoprogramados` AS `Entrenoprogramado` LEFT JOIN `sporte`.`semanas` AS `Semana` ON (`Entrenoprogramado`.`semana_id` = `Semana`.`id`) LEFT JOIN `sporte`.`meses` AS `Mese` ON (`Semana`.`mese_id` = `Mese`.`id`) LEFT JOIN `sporte`.`anos` AS `Ano` ON (`Mese`.`ano_id` = `Ano`.`id`) LEFT JOIN `sporte`.`entrenadores_athletes` AS `Entrenadores_athlete` ON (`Ano`.`entrenadore_athlete_id` = `Entrenadores_athlete`.`id`) LEFT JOIN `sporte`.`cdeportivo_users` AS `Cdeportivo_user` ON (`Entrenadores_athlete`.`cdeportivo_id` = `Cdeportivo_user`.`cdeportivo_id`) LEFT JOIN `sporte`.`athletes` AS `Athlete` ON (`Cdeportivo_user`.`athlete_id` = `Athlete`.`id`) LEFT JOIN `sporte`.`users` AS `User` ON (`Athlete`.`user_id` = `User`.`id`) where `Entrenoprogramado`.`id`=:ep group by athlete";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":ep", $id);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        } 
    }
?>
