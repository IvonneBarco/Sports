<?php
    if  (
            ( !isset(  $_POST["nombre"]     ) )     ||            
            ( !isset(  $_POST["fecha"]   ) )     ||
            ( !isset(  $_POST["hinicio"]   ) )  ||  
            ( !isset(  $_POST["hfin"]   ) )  ||  
            ( !isset(  $_POST["lugar"]   ) )  ||  
            ( !isset(  $_POST["semana_id"]   ) )  ||  
            ( !isset(  $_POST["descripcion"]   ) )  
        )
        {
            $respuesta = array(
                "estado"=>"error"
            );
            
            echo json_encode($respuesta);
            exit();
        }
        
        $nombre = $_POST["nombre"];  
        $fecha=$_POST["fecha"];
        $hinicio=$_POST["hinicio"];
        $hfin=$_POST["hfin"];
        $lugar=$_POST["lugar"]; 
        $semana=$_POST["semana_id"]; 
        $descrip=$_POST["descripcion"];
      
        require_once '../tareas/Entrenop.clase.php';        
               
        $objUsuario = new Entrenop();
        
        $objUsuario->setNombre($nombre);
        $objUsuario->setFecha($fecha);
        $objUsuario->setHinicio($hinicio);
        $objUsuario->setHfin($hfin);
        $objUsuario->setLugar($lugar);
        $objUsuario->setSemana_id($semana);
        $objUsuario->setDescripcion($descrip);
        
        
        if ($objUsuario->agregar() == TRUE){
            
            $respuesta = array(
                "estado"=>"exito",
                "nombre"=>$objSesion->consultarid()
            );
        }else{
            $respuesta = array(
                "estado"=>"error"
            );
        }
        
        //echo json_encode($respuesta);
        echo json_encode($respuesta);
        
        
        
?>
