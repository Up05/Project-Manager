package me.Ult1;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.github.tomaslanger.chalk.*;
import org.json.*;

public class Main {

    static String name, type;
    static final String mainPath = "C:\\Users\\Augustas\\Desktop\\c_Java\\Project-Manager";
    static final String pwd = System.getProperty("user.dir");
    static JSONObject json;

    public static void main(String[] args) throws IOException {

//        System.out.append(mainPath); // ❤ Java
        {
            File projectsF = new File(mainPath + "\\projects.json");
            Scanner ___ = new Scanner(projectsF);

            StringBuilder jsonB = new StringBuilder();
            while (___.hasNext())
                jsonB.append(___.next());

            json = new JSONObject(jsonB.toString());
        }
        if(args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")){
            if(args[2].equals("Javascript")) args[2] = "Website"; // ! Alias
            create(args[1], args[2]);

        } else if(args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")){
            if(args.length > 1)
                list(args[1]);
            else
                list("");

        } else if(args[0].equalsIgnoreCase("types") || args[0].equalsIgnoreCase("t")) {
            System.out.println("Project types: ");
            for (File folder : new File(mainPath + "/templates").listFiles())
                System.out.println("    - " + folder.getName());
        } else if(args[0].equalsIgnoreCase("unlist") || args[0].equalsIgnoreCase("u")){
            if(args.length > 1){
                try {
                    for(Object _obj : (JSONArray) json.get("data")) {
                        JSONObject obj = (JSONObject) _obj;
                        if (obj.get("name").equals(args[1])) {
                            obj.put("unlisted", true);
                            Files.writeString(Paths.get(mainPath + "\\projects.json"), json.toString(4));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Probably an object is null, i don't know why that could be.\n" + e);
                }


            } else
                System.out.println();
        
        } else if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?")){
            System.out.println(
                    """
                       Commands:
                           Project-Manager create <name> <type> c  - creates a new (Folder(name)\\project(type)).
                           Project-Manager list [type]          l  - lists all currently created projects. And if supplied excludes a type.
                           Project-Manager unlist [name]        u  - unlists a project (it will still be in projects.json).
                           Project-Manager types                t  - lists all possible project types.
                           Project-Manager help                 ?  - gives this message.
                           Project-Manager . [number]              - changes your cwd to latest project's directory (cwd = projects[projects.length - number])
                       
                       Example:
                           cd C:\\users\\ME\\desktop\\myCppFolder
                           Project-Manager create ILikeJava Cpp
                           Project-Manager .
                           echo Done!
                    """
            );
        } else if(args[0].equalsIgnoreCase(".")){
            JSONArray data = json.getJSONArray("data");
            JSONObject obj;
            if(args.length > 1) {
                try {
                    obj = (JSONObject) data.get(data.length() - Integer.parseInt(args[1]));
                } catch (Exception e) {
                    System.out.println("You don't have enough projects! (most recent project = 1)");
                    return;
                }
            } else
                obj = (JSONObject) data.get(data.length() - 1);
            System.out.append((CharSequence) obj.get("path"));
            return;
        } else System.out.println(Chalk.on("Please use Project-Manager help!").bold().red());

        System.out.append(pwd);

    }

    public static boolean create(String name, String type) throws IOException {
        String template = mainPath + "\\templates\\" + type;
        if(new File(pwd + "\\" + name).exists()) return false;

        copyDirectory(template, pwd + "\\" + name);

        JSONArray data = json.getJSONArray("data");
        JSONObject thisObject = new JSONObject();
        thisObject.put("path", pwd + "\\" + name);
        thisObject.put("type", type);
        thisObject.put("name", name);
        data.put(thisObject);

        Files.writeString(Paths.get(mainPath + "\\projects.json"), json.toString(4));

        return true;
    }

    public static void list(String type){
        JSONArray data = json.getJSONArray("data");
        for(Object _project : data.toList()){
            HashMap<String, String> project = (HashMap<String, String>) _project;
            if(project.get("type").equalsIgnoreCase(type)) continue;
            if(project.get("unlisted") != null && ((boolean) (Object) project.get("unlisted"))) continue;
            System.out.println(
                    Chalk.on(       project.get("name")       ).cyan().bold() + "" +
                    Chalk.on(" (" + project.get("type") + ")" ).yellow()      + "" +
                    Chalk.on(", " + project.get("path")       ).blue()
            );

        }
        System.out.println();
    }

    public static void copyDirectory(String src, String dst){
        try {
        Files.walk(Paths.get(src))
            .forEach(source -> {
                Path destination = Paths.get(dst, source.toString()
                        .substring(src.length()));
                    try {
                        Files.copy(source, destination);
                    } catch(IOException ignored){}
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // * from: https://www.baeldung.com/java-copy-directory
}
