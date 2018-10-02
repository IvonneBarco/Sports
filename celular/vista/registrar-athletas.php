<?php
    if  (            
            ( !isset(  $_POST["entreno"]   ) )  ||   
            ( !isset(  $_POST["athleta"]   ) )  
        )
        {
            $respuesta = array(
                "estado"=>"error"
            );
            
            echo json_encode($respuesta);
            exit();
        }
        
        
        $entreno=$_POST["entreno"]; 
        $athleta=$_POST["athleta"]; 
      
        require_once '../tareas/Asistencia.clase.php';        
               
        $objUsuario = new Asistencia();
        
        
        $objUsuario->setEntreno($entreno);
        $objUsuario->setAthleta($athleta);
        
        
        if ($objUsuario->agregar() == TRUE){
            
            $respuesta = array(
                "estado"=>"exito",
                
                
            );
        }else{
            $respuesta = array(
                "estado"=>"error"
            );
        }
        
        //echo json_encode($respuesta);
        echo json_encode($respuesta);
        
        
        
?>
