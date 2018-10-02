<?php
    require_once '../tareas/Entrenop.clase.php';
    $objUsuario = new Entrenop();
    $resultado = $objUsuario->listar();
    $respuesta = array(
        "entrenop"=>$resultado
    );
    echo json_encode($respuesta);   
?>
