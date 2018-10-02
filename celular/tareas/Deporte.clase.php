<?php
    require_once '../datos/Conexion.clase.php';
    class Deporte extends Conexion{
         
        public function listarDeportestodo(){
            //$sql = "SELECT `Coach`.`id`, `Coach`.`user_id`, `Coach`.`liga_id`, `Coach`.`disponibilidad`, `Coach`.`descripcion`, `User`.`id`, `User`.`username`, `User`.`password`, `User`.`contrasena`, `User`.`role`, `User`.`created`, `User`.`modified`, `User`.`cedula`, `User`.`nombre`, `User`.`apellido`, `User`.`fnacimiento`, `User`.`sexo`, `User`.`telefono`, `User`.`direccion`, `User`.`estadocivil`, `User`.`numhijos`, `User`.`munnacimiento`, `User`.`depnacimiento`, `User`.`paisnacimiento`, `User`.`regimensalud`, `User`.`epservicio_id`, `User`.`nivelriesgo`, `User`.`riesgo_id`, `User`.`pensione_id`, `User`.`actividad`, `User`.`empresa`, `User`.`dirempresa`, `User`.`telempresa`, `User`.`horarios`, `Liga`.`id`, `Liga`.`nombre`, `Liga`.`descripcion`, `Liga`.`sport_id` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) WHERE `user_id`=3";
            $sql = "SELECT `Sport`.`id` as iddeporte, `Sport`.`nombre` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) LEFT JOIN `sporte`.`sports` AS `Sport` ON (`Liga`.`sport_id` = `Sport`.`id`) WHERE 1=1";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        } 
        
        public function listarDeportes($id){
            //$sql = "SELECT `Coach`.`id`, `Coach`.`user_id`, `Coach`.`liga_id`, `Coach`.`disponibilidad`, `Coach`.`descripcion`, `User`.`id`, `User`.`username`, `User`.`password`, `User`.`contrasena`, `User`.`role`, `User`.`created`, `User`.`modified`, `User`.`cedula`, `User`.`nombre`, `User`.`apellido`, `User`.`fnacimiento`, `User`.`sexo`, `User`.`telefono`, `User`.`direccion`, `User`.`estadocivil`, `User`.`numhijos`, `User`.`munnacimiento`, `User`.`depnacimiento`, `User`.`paisnacimiento`, `User`.`regimensalud`, `User`.`epservicio_id`, `User`.`nivelriesgo`, `User`.`riesgo_id`, `User`.`pensione_id`, `User`.`actividad`, `User`.`empresa`, `User`.`dirempresa`, `User`.`telempresa`, `User`.`horarios`, `Liga`.`id`, `Liga`.`nombre`, `Liga`.`descripcion`, `Liga`.`sport_id` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) WHERE `user_id`=3";
            $sql = "SELECT `Sport`.`id` as iddeporte, `Sport`.`nombre` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) LEFT JOIN `sporte`.`sports` AS `Sport` ON (`Liga`.`sport_id` = `Sport`.`id`) WHERE `user_id`=:em";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $id);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        }  
        
        public function listarDeporte($id){
            //$sql = "SELECT `Coach`.`id`, `Coach`.`user_id`, `Coach`.`liga_id`, `Coach`.`disponibilidad`, `Coach`.`descripcion`, `User`.`id`, `User`.`username`, `User`.`password`, `User`.`contrasena`, `User`.`role`, `User`.`created`, `User`.`modified`, `User`.`cedula`, `User`.`nombre`, `User`.`apellido`, `User`.`fnacimiento`, `User`.`sexo`, `User`.`telefono`, `User`.`direccion`, `User`.`estadocivil`, `User`.`numhijos`, `User`.`munnacimiento`, `User`.`depnacimiento`, `User`.`paisnacimiento`, `User`.`regimensalud`, `User`.`epservicio_id`, `User`.`nivelriesgo`, `User`.`riesgo_id`, `User`.`pensione_id`, `User`.`actividad`, `User`.`empresa`, `User`.`dirempresa`, `User`.`telempresa`, `User`.`horarios`, `Liga`.`id`, `Liga`.`nombre`, `Liga`.`descripcion`, `Liga`.`sport_id` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) WHERE `user_id`=3";
            $sql = "SELECT `Sport`.`id` as iddeporte, `Sport`.`nombre` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) LEFT JOIN `sporte`.`sports` AS `Sport` ON (`Liga`.`sport_id` = `Sport`.`id`) WHERE `user_id`=:em";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $id);
            $sentencia->execute();            
            
            if ($sentencia->rowCount()<=0){
                
                return FALSE;
            }
            
                return TRUE;
            
        }  
        
        
        public function obteneriddeporte($id){
            //$sql = "SELECT `Coach`.`id`, `Coach`.`user_id`, `Coach`.`liga_id`, `Coach`.`disponibilidad`, `Coach`.`descripcion`, `User`.`id`, `User`.`username`, `User`.`password`, `User`.`contrasena`, `User`.`role`, `User`.`created`, `User`.`modified`, `User`.`cedula`, `User`.`nombre`, `User`.`apellido`, `User`.`fnacimiento`, `User`.`sexo`, `User`.`telefono`, `User`.`direccion`, `User`.`estadocivil`, `User`.`numhijos`, `User`.`munnacimiento`, `User`.`depnacimiento`, `User`.`paisnacimiento`, `User`.`regimensalud`, `User`.`epservicio_id`, `User`.`nivelriesgo`, `User`.`riesgo_id`, `User`.`pensione_id`, `User`.`actividad`, `User`.`empresa`, `User`.`dirempresa`, `User`.`telempresa`, `User`.`horarios`, `Liga`.`id`, `Liga`.`nombre`, `Liga`.`descripcion`, `Liga`.`sport_id` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) WHERE `user_id`=3";
            $sql = "SELECT Sport.id as iddeporte FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) LEFT JOIN `sporte`.`sports` AS `Sport` ON (`Liga`.`sport_id` = `Sport`.`id`) WHERE `user_id`=:em";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $id);
            $sentencia->execute(); 
            $resultado = $sentencia->fetchObject();
            return $resultado->iddeporte;
        }   
                
        
        public function obtenerNombredeporte($id){
            //$sql = "SELECT `Coach`.`id`, `Coach`.`user_id`, `Coach`.`liga_id`, `Coach`.`disponibilidad`, `Coach`.`descripcion`, `User`.`id`, `User`.`username`, `User`.`password`, `User`.`contrasena`, `User`.`role`, `User`.`created`, `User`.`modified`, `User`.`cedula`, `User`.`nombre`, `User`.`apellido`, `User`.`fnacimiento`, `User`.`sexo`, `User`.`telefono`, `User`.`direccion`, `User`.`estadocivil`, `User`.`numhijos`, `User`.`munnacimiento`, `User`.`depnacimiento`, `User`.`paisnacimiento`, `User`.`regimensalud`, `User`.`epservicio_id`, `User`.`nivelriesgo`, `User`.`riesgo_id`, `User`.`pensione_id`, `User`.`actividad`, `User`.`empresa`, `User`.`dirempresa`, `User`.`telempresa`, `User`.`horarios`, `Liga`.`id`, `Liga`.`nombre`, `Liga`.`descripcion`, `Liga`.`sport_id` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) WHERE `user_id`=3";
            $sql = "SELECT `Sport`.`nombre` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) LEFT JOIN `sporte`.`sports` AS `Sport` ON (`Liga`.`sport_id` = `Sport`.`id`) WHERE `user_id`=:em";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $id);
            $sentencia->execute();  
            $resultado = $sentencia->fetchObject();
            return $resultado->nombre;
        } 
        
        public function listarLigastodo($deporte){
            //$sql = "SELECT `Coach`.`id`, `Coach`.`user_id`, `Coach`.`liga_id`, `Coach`.`disponibilidad`, `Coach`.`descripcion`, `User`.`id`, `User`.`username`, `User`.`password`, `User`.`contrasena`, `User`.`role`, `User`.`created`, `User`.`modified`, `User`.`cedula`, `User`.`nombre`, `User`.`apellido`, `User`.`fnacimiento`, `User`.`sexo`, `User`.`telefono`, `User`.`direccion`, `User`.`estadocivil`, `User`.`numhijos`, `User`.`munnacimiento`, `User`.`depnacimiento`, `User`.`paisnacimiento`, `User`.`regimensalud`, `User`.`epservicio_id`, `User`.`nivelriesgo`, `User`.`riesgo_id`, `User`.`pensione_id`, `User`.`actividad`, `User`.`empresa`, `User`.`dirempresa`, `User`.`telempresa`, `User`.`horarios`, `Liga`.`id`, `Liga`.`nombre`, `Liga`.`descripcion`, `Liga`.`sport_id` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) WHERE `user_id`=3";
            $sql = "SELECT `Liga`.`id` as idliga, `Liga`.`nombre` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) LEFT JOIN `sporte`.`sports` AS `Sport` ON (`Liga`.`sport_id` = `Sport`.`id`) WHERE Sport.id=:em";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $deporte);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        }
        
        public function listarLigas($id,$deporte){
            //$sql = "SELECT `Coach`.`id`, `Coach`.`user_id`, `Coach`.`liga_id`, `Coach`.`disponibilidad`, `Coach`.`descripcion`, `User`.`id`, `User`.`username`, `User`.`password`, `User`.`contrasena`, `User`.`role`, `User`.`created`, `User`.`modified`, `User`.`cedula`, `User`.`nombre`, `User`.`apellido`, `User`.`fnacimiento`, `User`.`sexo`, `User`.`telefono`, `User`.`direccion`, `User`.`estadocivil`, `User`.`numhijos`, `User`.`munnacimiento`, `User`.`depnacimiento`, `User`.`paisnacimiento`, `User`.`regimensalud`, `User`.`epservicio_id`, `User`.`nivelriesgo`, `User`.`riesgo_id`, `User`.`pensione_id`, `User`.`actividad`, `User`.`empresa`, `User`.`dirempresa`, `User`.`telempresa`, `User`.`horarios`, `Liga`.`id`, `Liga`.`nombre`, `Liga`.`descripcion`, `Liga`.`sport_id` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) WHERE `user_id`=3";
            $sql = "SELECT `Liga`.`id` as idliga, `Liga`.`nombre` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) LEFT JOIN `sporte`.`sports` AS `Sport` ON (`Liga`.`sport_id` = `Sport`.`id`) WHERE `user_id`=:em and Sport.id=:en";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $id);
            $sentencia->bindParam(":en", $deporte);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        } 
        
        public function listarEntrenadores($id,$deporte,$liga){
            //$sql = "SELECT `Coach`.`id`, `Coach`.`user_id`, `Coach`.`liga_id`, `Coach`.`disponibilidad`, `Coach`.`descripcion`, `User`.`id`, `User`.`username`, `User`.`password`, `User`.`contrasena`, `User`.`role`, `User`.`created`, `User`.`modified`, `User`.`cedula`, `User`.`nombre`, `User`.`apellido`, `User`.`fnacimiento`, `User`.`sexo`, `User`.`telefono`, `User`.`direccion`, `User`.`estadocivil`, `User`.`numhijos`, `User`.`munnacimiento`, `User`.`depnacimiento`, `User`.`paisnacimiento`, `User`.`regimensalud`, `User`.`epservicio_id`, `User`.`nivelriesgo`, `User`.`riesgo_id`, `User`.`pensione_id`, `User`.`actividad`, `User`.`empresa`, `User`.`dirempresa`, `User`.`telempresa`, `User`.`horarios`, `Liga`.`id`, `Liga`.`nombre`, `Liga`.`descripcion`, `Liga`.`sport_id` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) WHERE `user_id`=3";
            $sql = "SELECT `Coach`.id as entrenadorid,`Liga`.`id` as idliga, `Liga`.`nombre`,`User`.`nombre`, `User`.`apellido` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) LEFT JOIN `sporte`.`sports` AS `Sport` ON (`Liga`.`sport_id` = `Sport`.`id`) WHERE `Liga`.id=:em and Sport.id=:en";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $liga);
            $sentencia->bindParam(":en", $deporte);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        }
        
        public function listarAtletas($id,$liga){
            //$sql = "SELECT `Coach`.`id`, `Coach`.`user_id`, `Coach`.`liga_id`, `Coach`.`disponibilidad`, `Coach`.`descripcion`, `User`.`id`, `User`.`username`, `User`.`password`, `User`.`contrasena`, `User`.`role`, `User`.`created`, `User`.`modified`, `User`.`cedula`, `User`.`nombre`, `User`.`apellido`, `User`.`fnacimiento`, `User`.`sexo`, `User`.`telefono`, `User`.`direccion`, `User`.`estadocivil`, `User`.`numhijos`, `User`.`munnacimiento`, `User`.`depnacimiento`, `User`.`paisnacimiento`, `User`.`regimensalud`, `User`.`epservicio_id`, `User`.`nivelriesgo`, `User`.`riesgo_id`, `User`.`pensione_id`, `User`.`actividad`, `User`.`empresa`, `User`.`dirempresa`, `User`.`telempresa`, `User`.`horarios`, `Liga`.`id`, `Liga`.`nombre`, `Liga`.`descripcion`, `Liga`.`sport_id` FROM `sporte`.`coaches` AS `Coach` LEFT JOIN `sporte`.`users` AS `User` ON (`Coach`.`user_id` = `User`.`id`) LEFT JOIN `sporte`.`ligas` AS `Liga` ON (`Coach`.`liga_id` = `Liga`.`id`) WHERE `user_id`=3";
            //$sql = "SELECT `EntrenadoresAthlete`.`id`, `EntrenadoresAthlete`.`coache_id`, `CdeportivoUser`.`athlete_id`, `CdeportivoUser`.`cdeportivo_id`, `CdeportivoUser`.`nivelrendimiento`,`Coache`.`id`, `Coache`.`user_id`, `Coache`.`liga_id`, `Coache`.`disponibilidad`, `Coache`.`descripcion` FROM `sporte`.`entrenadores_athletes` AS `EntrenadoresAthlete` LEFT JOIN `sporte`.`cdeportivo_users` AS `CdeportivoUser` ON (`EntrenadoresAthlete`.`cdeportivo_id` = `CdeportivoUser`.`cdeportivo_id`) LEFT JOIN `sporte`.`athletes` AS `Athlete` ON (`CdeportivoUser`.`athlete_id`=`Athlete`.`id`) LEFT JOIN `sporte`.`coaches` AS `Coache` ON (`EntrenadoresAthlete`.`coache_id` = `Coache`.`id`) WHERE `Coache`.`liga_id`=:em and `Coache`.`user_id`=:enSELECT `EntrenadoresAthlete`.`id`, `EntrenadoresAthlete`.`coache_id`, `CdeportivoUser`.`athlete_id`, `User`.nombre,`User`.apellido, `CdeportivoUser`.`cdeportivo_id`, `CdeportivoUser`.`nivelrendimiento`, `Coache`.`user_id`, `Coache`.`liga_id` FROM `sporte`.`entrenadores_athletes` AS `EntrenadoresAthlete` LEFT JOIN `sporte`.`cdeportivo_users` AS `CdeportivoUser` ON (`EntrenadoresAthlete`.`cdeportivo_id` = `CdeportivoUser`.`cdeportivo_id`) LEFT JOIN `sporte`.`athletes` AS `Athlete` ON (`CdeportivoUser`.`athlete_id`=`Athlete`.`id`) LEFT JOIN `sporte`.`users` AS `User` ON (`Athlete`.`user_id`=`User`.`id`) LEFT JOIN `sporte`.`coaches` AS `Coache` ON (`EntrenadoresAthlete`.`coache_id` = `Coache`.`id`) WHERE `Coache`.`liga_id`= :em and `Coache`.`user_id`= :en";
            $sql="SELECT `EntrenadoresAthlete`.`id`, `EntrenadoresAthlete`.`coache_id`, `CdeportivoUser`.`athlete_id`, `User`.nombre,`User`.apellido, `CdeportivoUser`.`cdeportivo_id`, `CdeportivoUser`.`nivelrendimiento`, `Coache`.`user_id`, `Coache`.`liga_id` FROM `sporte`.`entrenadores_athletes` AS `EntrenadoresAthlete` LEFT JOIN `sporte`.`cdeportivo_users` AS `CdeportivoUser` ON (`EntrenadoresAthlete`.`cdeportivo_id` = `CdeportivoUser`.`cdeportivo_id`) LEFT JOIN `sporte`.`athletes` AS `Athlete` ON (`CdeportivoUser`.`athlete_id`=`Athlete`.`id`) LEFT JOIN `sporte`.`users` AS `User` ON (`Athlete`.`user_id`=`User`.`id`) LEFT JOIN `sporte`.`coaches` AS `Coache` ON (`EntrenadoresAthlete`.`coache_id` = `Coache`.`id`) WHERE `Coache`.`liga_id`= :em and `Coache`.`user_id`= :en";
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $liga);
            $sentencia->bindParam(":en", $id);
            $sentencia->execute();            
            return $sentencia->fetchAll(PDO::FETCH_OBJ);
        }
        
        
        
    }
?>

