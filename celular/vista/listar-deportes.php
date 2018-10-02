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
    

    require_once '../tareas/Deporte.clase.php';
    $objUsuario = new Deporte();
    
    /*
    
    if ($objUsuario->listarDeporte($id)){
        $respuesta = array(
            "estado"=>"exito",
            "datos"=>array(        
                "nombre"=>$objUsuario->obtenerNombredeporte($id),
                "id"=>$objUsuario->obteneriddeporte($id)
            )
        );
    }else{
        $respuesta = array(
            "estado"=>"no existe",
            "datos"=>""
        );
    }
    
    echo json_encode($respuesta);
    */
    
    if($role=='admin'){
        if ($resultado = $objUsuario->listarDeportestodo()){;
            $respuesta = array(
                "deportes"=>$resultado
            );
            
        }else{
            $respuesta = array(
                "deportes"=>" no hay datos"
            );
        }
    }else{
        
        if ($resultado = $objUsuario->listarDeportes($id)){;
            $respuesta = array(
                "deportes"=>$resultado
            );
        }else{
            $respuesta = array(
                "deportes"=>" no hay datos"
            );
        }
    }
    echo json_encode(compact('resultado', 'comments'));  
    //echo json_encode($respuesta);
    
?>

