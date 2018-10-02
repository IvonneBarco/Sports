<?php
    if (isset($_POST["username"])){
        $usuario = $_POST["username"];
    }else{
        $usuario = "";
    }
    
    if (isset($_POST["password"])){
        $clave = $_POST["password"];
    }else{
        $clave = "";
    }
    $respuesta = null;
    
    if ( ! isset($_POST["username"])){
         $respuesta = array(
             "estado"=>"error",
             "datos"=>""
         );
         echo json_encode($respuesta);
         exit();
    }
    
    
    
    require_once '../tareas/Sesion.clase.php';
    $objSesion = new Sesion();
    
   
   
    
    
    
    if ($objSesion->iniciarSesion($usuario, $clave)){
        $respuesta = array(
            "estado"=>"exito",
            "datos"=>array(               
                "username"=>$usuario,
                "nombre"=>$objSesion->obtenerNombreUsuario($usuario),
                "id"=>$objSesion->obtenerCodigoUsuario($usuario),
                "role"=>$objSesion->obtenerRoleUsuario($usuario)
            )
        );
    }else{
        $respuesta = array(
            "estado"=>"no existe",
            "datos"=>""
        );
    }
    
    echo json_encode($respuesta);
    
?>
