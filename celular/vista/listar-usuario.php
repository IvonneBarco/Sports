<?php
    require_once '../tareas/Usuario.clase.php';
    $objUsuario = new Usuario();
    $resultado = $objUsuario->listar();
    $respuesta = array(
        "usuario"=>$resultado
    );
    echo json_encode($respuesta);   
?>
