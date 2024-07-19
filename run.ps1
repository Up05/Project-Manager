if($args[0] -eq ".") {
    $a = & "C:\Program Files\Java\jdk-17.0.2\bin\java.exe" -jar C:\Users\Augustas\Desktop\c_Java\Project-Manager\out\artifacts\Project_Manager_jar\Project-Manager.jar $args[0] $args[1]
    if($a -and (Test-Path $a)){
        cd $a
    }
    Write-Host $a

} else {
    & "C:\Program Files\Java\jdk-17.0.2\bin\java.exe" -jar C:\Users\Augustas\Desktop\c_Java\Project-Manager\out\artifacts\Project_Manager_jar\Project-Manager.jar $args[0] $args[1] $args[2]
}