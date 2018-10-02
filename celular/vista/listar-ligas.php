<?php

    if (isset($_POST["id"])){
        $id = $_POST["id"];
    }else{
        $id = "";
    }
    
    if (isset($_POST["role"])){
        $role = $_POST["role"];
    }else{
        $role = "";
    }
    
    if (isset($_POST["deporte"])){
        $deporte = $_POST["deporte"];
    }else{
        $deporte = "";
    }
    
    $respuesta = null;
    
    if ( ! isset($_POST["id"])){
        $respuesta = array(
            "deportes"=>"Error"
        );
         echo json_encode($respuesta);
         exit();
    }
    
    if ( ! isset($_POST["role"])){
        $respuesta = array(
            "deportes"=>"Error"
        );
         echo json_encode($respuesta);
         exit();
    }
    
    if ( ! isset($_POST["deporte"])){
        $respuesta = array(
            "ligas"=>"Error"
        );
         echo json_encode($respuesta);
         exit();
    }
    

    require_once '../tareas/Deporte.clase.php';
    $objUsuario = new Deporte();
    
    if($role=='admin'){
        if ($resultado = $objUsuario->listarLigastodo($deporte)){;
            $respuesta = array(
                "ligas"=>$resultado
            );
        }else{
            $respuesta = array(
                "ligas"=>" no hay datos"
            );
        }
    }else{
        
        if ($resultado = $objUsuario->listarLigas($id,$deporte)){;
            $respuesta = array(
                "ligas"=>$resultado
            );
        }else{
            $respuesta = array(
                "ligas"=>" no hay datos"
            );
        }
    }
    echo json_encode($respuesta);   
?>


