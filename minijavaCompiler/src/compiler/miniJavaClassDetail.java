package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class miniJavaClassDetail {

    private static HashMap <String , String[]> implementations = new HashMap<>();
    private static HashMap <String , List<String>> methods = new HashMap<>();

    public static void addImplemente(String className, String interfaces){
        implementations.put(className , interfaces.split(","));
    }
    public static void addMethod(String name , String methodName , String accessType){
        List<String> localMethods = new ArrayList<String>();
        if (methods.containsKey(name)){
            localMethods = methods.get(name);
        }
        localMethods.add(accessType + "_" + methodName);
        methods.put(name , localMethods);
    }
    public static boolean getMethod(String ClassName , String Method){
        List<String> localMethods = methods.get(ClassName);
        if (localMethods == null)
            return true;
        for (String classNameMethodName : localMethods){
            if (classNameMethodName.equals(Method)  && Method.equals("public") ){
                return false;
            }
        }
        return true;
    }
    public static void checkImplementagtion() throws Exception{
        for (Map.Entry<String, String[]> entry : implementations.entrySet()) {
            String className = entry.getKey();
            String[] implementations = entry.getValue();
            List<String> classMethods = methods.get(className);
            for (String interfaceName : implementations) {
                List<String> interfaceMethods = methods.get(interfaceName) != null ? methods.get(interfaceName) : new ArrayList<>();
                for (String method :interfaceMethods) {
                    if (!classMethods.contains(method)){
                        throw new Exception("Error420: Class " + className + " must implement all abstract methods");
                    }

                }
            }
        }
    }
}
