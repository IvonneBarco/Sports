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
    
    if (isset($_POST["liga"])){
        $liga = $_POST["liga"];
    }else{
        $liga = "";
    }
    
    $respuesta = null;
    
    if ( ! isset($_POST["id"])){
        $respuesta = array(
            "entrenador"=>"Error"
        );
         echo json_encode($respuesta);
         exit();
    }
    
    if ( ! isset($_POST["role"])){
        $respuesta = array(
            "entrenador"=>"Error"
        );
         echo json_encode($respuesta);
         exit();
    }
    
    if ( ! isset($_POST["deporte"])){
        $respuesta = array(
            "entrenador"=>"Error"
        );
         echo json_encode($respuesta);
         exit();
    }
    
    if ( ! isset($_POST["liga"])){
        $respuesta = array(
            "entrenador"=>"Error"
        );
         echo json_encode($respuesta);
         exit();
    }
    

    require_once '../tareas/Deporte.clase.php';
    $objUsuario = new Deporte();
    
    if($role=='admin'){
        if ($resultado = $objUsuario->listarEntrenadores($id,$deporte,$liga)){;
            $respuesta = array(
                "entrenadores"=>$resultado
            );
        }else{
            $respuesta = array(
                "entrenadores"=>" no hay datos"
            );
        }
    }
    echo json_encode($respuesta);   
?>


