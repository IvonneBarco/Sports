<?php
    if  (
            ( !isset(  $_POST["nombre"]     ) )     ||            
            ( !isset(  $_POST["foto"]   ) )     ||
            ( !isset(  $_POST["combo"]   ) )        
        )
        {
            $respuesta = array(
                "estado"=>"error"
            );
            
            echo json_encode($respuesta);
            exit();
        }
        
        
        require_once '../tareas/Ano.clase.php';
        
        $objUsuario = new Ano();
        
        $id = $objUsuario->ultimoid();
 
        $nombre = $_POST["nombre"];        
        $dir = $id+1;
        $filename = $dir.".png";
        $entre_athle = $_POST["combo"];
        $imagen= $_POST['foto'];
        $path = "uploads/$dir.png";
        $actualpath = "http://www.iucesmag.edu.co/celular/$path";

        
        echo $path;
        
        require_once '../tareas/Ano.clase.php';
        
        $objUsuario = new Ano();
        $objUsuario->setNombre($nombre);
        $objUsuario->setFilename($filename);
        $objUsuario->setDir($dir);
        $objUsuario->setEntrenadore_athlete_id($entre_athle);
        
        
        
        
        if ($objUsuario->agregar()==TRUE){
            file_put_contents($actualpath,base64_decode($imagen));
            
            $respuesta = array(
                "estado"=>"exito",
                "ruta"=>$actualpath
            );
        }else{
            $respuesta = array(
                "estado"=>"error"
            );
        }
        
        //echo json_encode($respuesta);
        echo json_encode($respuesta);
        
?>
