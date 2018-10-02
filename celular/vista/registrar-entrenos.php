<?php
    if  (            
            ( !isset(  $_POST["gps"]   ) )  ||  
            ( !isset(  $_POST["entrenop"]   ) )  ||  
            ( !isset(  $_POST["descripcion"]   ) )  
        )
        {
            $respuesta = array(
                "estado"=>"error"
            );
            
            echo json_encode($respuesta);
            exit();
        }
        
        
        $lugar=$_POST["gps"]; 
        $semana=$_POST["entrenop"]; 
        $descrip=$_POST["descripcion"];
      
        require_once '../tareas/Entreno.clase.php';        
               
        $objUsuario = new Entreno();
        
        
        $objUsuario->setGps($lugar);
        $objUsuario->setEntrenop($semana);
        $objUsuario->setDescripcion($descrip);
        
        
        if ($objUsuario->agregar() == TRUE){
            
            $respuesta = array(
                "estado"=>"exito",
                "id"=>$objUsuario->consultarid()
                
                
            );
        }else{
            $respuesta = array(
                "estado"=>"error"
            );
        }
        
        //echo json_encode($respuesta);
        echo json_encode($respuesta);
        
        
        
?>
