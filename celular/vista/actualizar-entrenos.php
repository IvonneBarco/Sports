<?php
    if  (            
            ( !isset(  $_POST["id"]   ) )  
        )
        {
            $respuesta = array(
                "estado"=>"error"
            );
            
            echo json_encode($respuesta);
            exit();
        }
        
        
        $identificador=$_POST["id"]; 
      
        require_once '../tareas/Entreno.clase.php';        
               
        $objUsuario = new Entreno();
              
        
        if ($objUsuario->pararentreno($identificador) == TRUE){
            
            $respuesta = array(
                "estado"=>"exito"               
                
            );
        }else{
            $respuesta = array(
                "estado"=>"error"
            );
        }
        
        //echo json_encode($respuesta);
        echo json_encode($respuesta);
        
        
        
?>
