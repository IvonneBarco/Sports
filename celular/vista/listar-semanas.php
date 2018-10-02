<?php
    require_once '../tareas/Semana.clase.php';
    $objUsuario = new Semana();
    $resultado = $objUsuario->listar();
    $respuesta = array(
        "semanas"=>$resultado
    );
    echo json_encode($respuesta);   
?>
