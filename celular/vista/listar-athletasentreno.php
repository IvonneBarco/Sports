<?php

    if (isset($_POST["id"])){
        $id = $_POST["id"];
    }else{
        $id = "";
    }
    
       
    $respuesta = null;
    
    if ( ! isset($_POST["id"])){
        $respuesta = array(
            "athletas"=>"Error"
        );
         echo json_encode($respuesta);
         exit();
    }
        
    require_once '../tareas/Entreno.clase.php';
    $objUsuario = new Entreno();
    
            
        if ($resultado = $objUsuario->listarAthletas($id)){;
            $respuesta = array(
                "athleta"=>$resultado
            );
        }else{
            $respuesta = array(
                "athleta"=>" no hay datos"
            );
        }
    
    echo json_encode($respuesta);   
?>


