# Project-Manager


I guess, it works like so:
```
java -jar .\Project-Manager.jar help # So you can set up a bat in %path% for this, I guess, I lost mine...
```
```
Commands:
   Project-Manager create <name> <type> c  - creates a new (Folder(name)\project(type)).
   Project-Manager list [type]          l  - lists all currently created projects. And if supplied excludes a type.
   Project-Manager unlist [name]        u  - unlists a project (it will still be in projects.json).
   Project-Manager types                t  - lists all possible project types.
   Project-Manager help                 ?  - gives this message.
   Project-Manager . [number]              - changes your cwd to latest project's directory (cwd = projects[projects.length - number])

Example:
   cd C:\users\ME\desktop\myCppFolder
   Project-Manager create ILikeJava Cpp
   Project-Manager .
   echo Done!
```

