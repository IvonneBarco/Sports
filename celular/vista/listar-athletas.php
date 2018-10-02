<?php

    if (isset($_POST["id"])){
        $id = $_POST["id"];
    }else{
        $id = "";
    }
    
    if (isset($_POST["liga"])){
        $liga = $_POST["liga"];
    }else{
        $liga = "";
    }
    
       
    $respuesta = null;
    
    if ( ! isset($_POST["id"])){
        $respuesta = array(
            "athletas"=>"Error"
        );
         echo json_encode($respuesta);
         exit();
    }
    
    if ( ! isset($_POST["liga"])){
        $respuesta = array(
            "athletas"=>"Error"
        );
         echo json_encode($respuesta);
         exit();
    }
    
    require_once '../tareas/Deporte.clase.php';
    $objUsuario = new Deporte();
    
            
        if ($resultado = $objUsuario->listarAtletas($id,$liga)){;
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


