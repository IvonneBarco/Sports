<?php
    
    require_once '../datos/Conexion.clase.php';
    
    class Sesion extends Conexion {
        
        public function iniciarSesion($usuario, $clave) {
            
            $sql = "SELECT * FROM users WHERE username = :em";
            
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $usuario);
            $sentencia->execute();
            
                        
            if ($sentencia->rowCount()<=0){
                
                return FALSE;
            }
            
            $resultado = $sentencia->fetchObject();
            
            
            //$datos= md5($clave);
            
            
            
            if ($resultado->contrasena == md5($clave)){
                return TRUE;                
            }

            
            return FALSE;
            
        }
        
        public function obtenerNombreUsuario($username){
            $sql = "SELECT nombre, apellido FROM users WHERE username = :em";
            
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $username);
            $sentencia->execute();
            
            $resultado = $sentencia->fetchObject();
            
            $res=$resultado->nombre." ".$resultado->apellido;
            
            return $res;
        }
  
  public function obtenerCodigoUsuario($username){
            $sql = "SELECT id FROM users WHERE username = :em";
            
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $username);
            $sentencia->execute();
            
            $resultado = $sentencia->fetchObject();
            
            return $resultado->id;
        }
        
    
   
     public function obtenerRoleUsuario($username){
            $sql = "SELECT role FROM users WHERE username = :em";
            
            $sentencia = $this->dblink->prepare($sql);
            $sentencia->bindParam(":em", $username);
            $sentencia->execute();
            
            $resultado = $sentencia->fetchObject();
            
            
            
            return $resultado->role;
        }
        
    
        
    }
?>
