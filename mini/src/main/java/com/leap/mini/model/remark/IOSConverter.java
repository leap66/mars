//package com.leap.mini.model.remark;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import com.leap.mini.model.network.Response;
//import com.leap.mini.model.network.SummaryResponse;
//
//import android.databinding.repacked.apache.commons.io.IOUtils;
//
//import okhttp3.RequestBody;
//import retrofit2.http.Body;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.Part;
//import retrofit2.http.Path;
//import retrofit2.http.Query;
//import rx.Observable;
//
///**
// * @author : ylwei
// * @time : 2017/9/18
// * @description :
// */
//public class IOSConverter {
//  public static Map<String, String> globalPathMap = new HashMap<>();
//  static {
//    globalPathMap.put("shop", "SessionMgr.instance.getShop()?.id ?? \"\"");
//  }
//
//  public static List<String> fileContent = new ArrayList<>();
//  public static Map<String, List<String>> methodContent = new HashMap<>();
//
//  public static void main(String[] args) throws Exception {
//    File dir = new File(
//        "/Users/neil/Workspace/Laya/jomo/app/src/main/java/com/qianfan123/jomo/data/model");
//    buildModel(dir, new File("/Users/neil/temp/Model"));
//
//    File interfaceDir = new File(
//        "/Users/neil/Workspace/Laya/jomo/app/src/main/java/com/qianfan123/jomo/interactors");
//    buildRouter(interfaceDir, new File("/Users/neil/temp/Router"));
//  }
//
//  public static void buildRouter(File dir, File outputDir) throws Exception {
//    if (dir == null || outputDir == null) {
//      return;
//    }
//    outputDir.mkdirs();
//    for (File file : dir.listFiles()) {
//      if (file.getName().equals("usecase"))
//        continue;
//      if (file.isDirectory()) {
//        buildRouter(new File(dir, file.getName()),
//            new File(outputDir, getUpperName(file.getName())));
//      } else {
//        if (file.getName().startsWith(".") || file.getName().endsWith("Case.java")) {
//          continue;
//        }
//        String packageName = readFile(file);
//        Class<?> clazz = ClassLoader.getSystemClassLoader()
//            .loadClass(String.format("%s.%s", packageName, getSimpleName(file.getName())));
//        if (clazz.isInterface()) {
//          buildRouter(outputDir, clazz);
//        }
//      }
//    }
//    if (outputDir.listFiles().length <= 0) {
//      outputDir.delete();
//    }
//  }
//
//  private static void buildRouter(File parent, Class<?> clazz) throws IOException {
//    Method[] methods = clazz.getMethods();
//    if (clazz.equals(UpdateServiceApi.class)) {
//      return;
//    }
//    if (methods.length <= 0) {
//      return;
//    }
//    File file = new File(parent, getRouterName(getName(clazz.getSimpleName())) + ".swift");
//    FileWriter writer = null;
//    try {
//      writer = new FileWriter(file, true);
//      writer.write("import Foundation\n");
//      writer.write("import Alamofire\n\n");
//      String baseClass = "URLRequestConvertible, APIConfiguration";
//      writer.write(String.format("enum %s: " + baseClass + " {\n",
//          getRouterName(getName(clazz.getSimpleName()))));
//
//      List<MethodType> methodTypes = new ArrayList<>();
//      for (Method method : methods) {
//        MethodType methodType = new MethodType();
//        methodType.setReturnType(getReturnType(method.getGenericReturnType()));
//        methodType.setName(method.getName());
//        Annotation[] annotations = method.getAnnotations();
//        for (Annotation annotation : annotations) {
//          String url = null;
//          if (annotation.annotationType().equals(GET.class)) {
//            methodType.setMethod("get");
//            methodType.setUrl(((GET) annotation).value());
//          }
//          if (annotation.annotationType().equals(POST.class)) {
//            methodType.setMethod("post");
//            methodType.setUrl(((POST) annotation).value());
//          }
//          if (annotation.annotationType().equals(Domain.class)) {
//            methodType.setDomain(true);
//          }
//          if (annotation.annotationType().equals(PlatformAPI.class)) {
//            methodType.setPlatform(true);
//          }
//        }
//
//        Class<?>[] types = method.getParameterTypes();
//        Annotation[][] annotationList = method.getParameterAnnotations();
//        if (types.length > 0) {
//          for (int i = 0; i < types.length; i++) {
//            Annotation[] annotation = annotationList[i];
//            for (Annotation item : annotation) {
//              if (item.annotationType().equals(Query.class)) {
//                Query query = (Query) item;
//                methodType.getParamaters().put(query.value(), getParamType(types[i]));
//              } else if (item.annotationType().equals(Path.class)) {
//                Path path = (Path) item;
//                methodType.getPaths().put(path.value(), getParamType(types[i]));
//              } else if (item.annotationType().equals(Body.class)) {
//                methodType.setBody(getParamType(types[i]));
//              } else if (item.annotationType().equals(Part.class)) {
//                Part part = (Part) item;
//                if (part.value().equals("")) {
//                  continue;
//                }
//                if (types[i].equals(RequestBody.class)) {
//                  methodType.setBody(getUpperName(part.value()) + "Model");
//                } else {
//                  methodType.getParts().add(part.value());
//                }
//              } else if (item.annotationType().equals(PartName.class)) {
//                PartName part = (PartName) item;
//                if (part.value().equals("")) {
//                  continue;
//                }
//                if (types[i].equals(RequestBody.class)) {
//                  methodType.setBody(getUpperName(part.value()) + "Model");
//                } else {
//                  methodType.getParts().add(part.value());
//                }
//              }
//            }
//          }
//        }
//        methodTypes.add(methodType);
//      }
//
//      // case
//      for (MethodType item : methodTypes) {
//        List<String> comments = findMethodComment(clazz.getSimpleName(), item.getName());
//        if (comments != null) {
//          for (String comment : comments) {
//            writer.write(String.format("\t%s\n", comment));
//          }
//        }
//        writer.write(String.format("\t//%s\n", item.getReturnType()));
//        writer.write(String.format("\tcase %s", item.getName()));
//        if (item.hasParameter()) {
//          writer.write("(");
//          int i = 0;
//          if (item.isDomain()) {
//            writer.write(String.format("domain: String"));
//            i++;
//          }
//          for (String key : item.getPaths().keySet()) {
//            if (!globalPathMap.keySet().contains(key)) {
//              if (i > 0) {
//                writer.write(", ");
//              }
//              writer.write(String.format("%s: %s", key, item.getPaths().get(key)));
//              i++;
//            } else if (item.isDomain()) {
//              if (i > 0) {
//                writer.write(", ");
//              }
//              writer.write(String.format("%s: %s", key, item.getPaths().get(key)));
//              i++;
//            }
//          }
//          for (String key : item.getParamaters().keySet()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write(String.format("%s: %s", key, item.getParamaters().get(key)));
//            i++;
//          }
//          if (item.getBody() != null) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write("body: " + item.getBody());
//            i++;
//          }
//          for (String key : item.getParts()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write(key + ": [Data]?");
//            i++;
//          }
//          writer.write(")\n\n");
//        }
//      }
//
//      // method
//      writer.write("\n\tvar method: HTTPMethod {\n");
//      writer.write("\t\tswitch self {\n");
//      int i = 0;
//      for (MethodType item : methodTypes) {
//        writer.write(String.format("\t\tcase .%s:\n", item.getName()));
//        writer.write(String.format("\t\t\treturn .%s\n", item.getMethod()));
//      }
//      writer.write("\t\t}\n");
//      writer.write("\t}\n");
//
//      // path
//      writer.write("\n\tvar path: String {\n");
//      writer.write("\t\tvar path: String = \"\"\n");
//      writer.write("\t\tswitch self {\n");
//      for (MethodType item : methodTypes) {
//        writer.write(String.format("\t\tcase .%s(", item.getName()));
//        i = 0;
//        if (item.isDomain()) {
//          writer.write("_");
//          i++;
//
//          for (String key : item.getPaths().keySet()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write(String.format("let %s", key));
//            i++;
//          }
//        } else {
//          for (String key : item.getPaths().keySet()) {
//            if (!globalPathMap.keySet().contains(key)) {
//              if (i > 0) {
//                writer.write(", ");
//              }
//              writer.write(String.format("let %s", key));
//              i++;
//            }
//          }
//        }
//        for (String key : item.getParamaters().keySet()) {
//          if (i > 0) {
//            writer.write(", ");
//          }
//          writer.write("_");
//          i++;
//        }
//        if (item.getBody() != null) {
//          if (i > 0) {
//            writer.write(", ");
//          }
//          writer.write("_");
//          i++;
//        }
//        for (String key : item.getParts()) {
//          if (i > 0) {
//            writer.write(", ");
//          }
//          writer.write("_");
//          i++;
//        }
//        writer.write("):\n");
//
//        if (item.getPaths().size() > 0) {
//          String value = item.getUrl();
//          for (String key : item.getPaths().keySet()) {
//            if (item.isDomain() && globalPathMap.keySet().contains(key)) {
//
//            } else if (globalPathMap.keySet().contains(key)) {
//              continue;
//            }
//            value = value.replace(String.format("{%s}", key), String.format("\\(%s)", key));
//          }
//          if (item.isDomain()) {
//            writer.write(String.format("\t\t\treturn ", value));
//          } else {
//            writer.write("\t\t\tpath = ");
//          }
//          writer.write(String.format("\"%s\"\n", value));
//        } else {
//          writer.write(String.format("\t\t\tpath = \"%s\"\n", item.getUrl()));
//        }
//      }
//      writer.write("\t\t}\n");
//      for (String key : globalPathMap.keySet()) {
//        writer.write(String.format(
//            "\t\tpath = path.replacingOccurrences(of: \"{%s}\", with: %s, options: NSString.CompareOptions.literal, range: nil)\n",
//            key, globalPathMap.get(key)));
//      }
//      writer.write("\t\treturn path\n");
//      writer.write("\t}\n");
//
//      // parameter
//      writer.write("\n\tvar parameters: [String: Any]? {\n");
//      writer.write("\t\tswitch self {\n");
//      int count = 0;
//      for (MethodType item : methodTypes) {
//        if (item.getParamaters().size() > 0) {
//          writer.write(String.format("\t\tcase .%s(", item.getName()));
//          i = 0;
//          if (item.isDomain()) {
//            writer.write("_");
//            i++;
//
//            for (String key : item.getPaths().keySet()) {
//              if (i > 0) {
//                writer.write(", ");
//              }
//              writer.write("_");
//              i++;
//            }
//          } else {
//            for (String key : item.getPaths().keySet()) {
//              if (!globalPathMap.keySet().contains(key)) {
//                if (i > 0) {
//                  writer.write(", ");
//                }
//                writer.write("_");
//                i++;
//              }
//            }
//          }
//          for (String key : item.getParamaters().keySet()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write(String.format("let %s", key));
//            i++;
//          }
//          if (item.getBody() != null) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write("_");
//            i++;
//          }
//          for (String key : item.getParts()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write("_");
//            i++;
//          }
//          writer.write("):\n");
//          writer.write("\t\t\treturn [");
//          i = 0;
//          for (String key : item.getParamaters().keySet()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write(String.format("\"%s\": %s", key, key));
//            i++;
//          }
//          writer.write("]\n");
//          count++;
//        }
//      }
//      if (count < methodTypes.size()) {
//        writer.write("\t\tdefault:\n\t\t\treturn nil\n");
//      }
//      writer.write("\t\t}\n");
//      writer.write("\t}\n");
//
//      // body
//      writer.write("\n\tvar bodyParameters: [String: Any]? {\n");
//      writer.write("\t\tswitch self {\n");
//      count = 0;
//      for (MethodType item : methodTypes) {
//        if (item.getBody() != null) {
//          writer.write(String.format("\t\tcase .%s(", item.getName()));
//          i = 0;
//          if (item.isDomain()) {
//            writer.write("_");
//            i++;
//
//            for (String key : item.getPaths().keySet()) {
//              if (i > 0) {
//                writer.write(", ");
//              }
//              writer.write("_");
//              i++;
//            }
//          } else {
//            for (String key : item.getPaths().keySet()) {
//              if (!globalPathMap.keySet().contains(key)) {
//                if (i > 0) {
//                  writer.write(", ");
//                }
//                writer.write("_");
//                i++;
//              }
//            }
//          }
//          for (String key : item.getParamaters().keySet()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write("_");
//            i++;
//          }
//          if (i > 0) {
//            writer.write(", ");
//          }
//          writer.write("let body");
//          i++;
//          for (String key : item.getParts()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write("_");
//            i++;
//          }
//          writer.write("):\n");
//          writer.write("\t\t\treturn [\n");
//          writer.write("\t\t\t\t\"body\": body.toJSON()\n");
//          writer.write("\t\t\t]\n");
//          count++;
//        }
//      }
//      if (count < methodTypes.size()) {
//        writer.write("\t\tdefault:\n\t\t\treturn nil\n");
//      }
//      writer.write("\t\t}\n");
//      writer.write("\t}\n");
//
//      // parts
//      writer.write("\n\tvar parts: [(fieldName: String, fileContent: [Data]?)]? {\n");
//      writer.write("\t\tswitch self {\n");
//      count = 0;
//      for (MethodType item : methodTypes) {
//        if (item.getParts().size() > 0) {
//          writer.write(String.format("\t\tcase .%s(", item.getName()));
//          i = 0;
//          if (item.isDomain()) {
//            writer.write("_");
//            i++;
//
//            for (String key : item.getPaths().keySet()) {
//              if (i > 0) {
//                writer.write(", ");
//              }
//              writer.write("_");
//              i++;
//            }
//          } else {
//            for (String key : item.getPaths().keySet()) {
//              if (!globalPathMap.keySet().contains(key)) {
//                if (i > 0) {
//                  writer.write(", ");
//                }
//                writer.write("_");
//                i++;
//              }
//            }
//          }
//          for (String key : item.getParamaters().keySet()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write("_");
//            i++;
//          }
//          if (item.getBody() != null) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write("_");
//            i++;
//          }
//          for (String key : item.getParts()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write(String.format("let %s", key));
//            i++;
//          }
//          writer.write("):\n");
//          writer.write("\t\t\treturn [\n");
//          int j = 0;
//          for (String key : item.getParts()) {
//            if (j > 0) {
//              writer.write(",\n");
//            }
//            writer.write(String.format("\t\t\t\t(fieldName: \"%s\", fileContent:%s)", key, key));
//            j++;
//          }
//          writer.write("\n\t\t\t]\n");
//          count++;
//        }
//      }
//      if (count < methodTypes.size()) {
//        writer.write("\t\tdefault:\n\t\t\treturn nil\n");
//      }
//      writer.write("\t\t}\n");
//      writer.write("\t}\n");
//
//      // baseUrl
//      writer.write("\n\tvar baseUrl: String {\n");
//      writer.write("\t\tswitch self {\n");
//      count = 0;
//      for (MethodType item : methodTypes) {
//        if (item.isDomain() || item.isPlatform()) {
//          writer.write(String.format("\t\tcase .%s(", item.getName()));
//          i = 0;
//          if (item.isDomain()) {
//            writer.write("let domain");
//            i++;
//
//            for (String key : item.getPaths().keySet()) {
//              if (i > 0) {
//                writer.write(", ");
//              }
//              writer.write("_");
//              i++;
//            }
//          }
//          for (String key : item.getParamaters().keySet()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write("_");
//            i++;
//          }
//          if (item.getBody() != null) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write("_");
//            i++;
//          }
//          for (String key : item.getParts()) {
//            if (i > 0) {
//              writer.write(", ");
//            }
//            writer.write("_");
//            i++;
//          }
//          writer.write("):\n");
//          writer.write("\t\t\treturn ");
//          if (item.isDomain()) {
//            writer.write("domain\n");
//          } else if (item.isPlatform()) {
//            writer.write("PListUtil.get(AppConfig.ServerUrl)\n");
//          }
//          count++;
//        }
//      }
//      if (count < methodTypes.size()) {
//        writer.write("\t\tdefault:\n\t\t\treturn SessionMgr.instance.getShopDomain() ?? \"\"\n");
//      }
//      writer.write("\t\t}\n");
//      writer.write("\t}\n");
//
//      writer.write("}\n");
//      writer.flush();
//    } finally {
//      IOUtils.closeQuietly(writer);
//    }
//  }
//
//  private static String getReturnType(Type returnType) {
//    if (returnType instanceof Class) {
//      return getName(((Class) returnType).getSimpleName());
//    } else {
//      ParameterizedType type = (ParameterizedType) returnType;
//      if (((Class) type.getRawType()).equals(Observable.class)) {
//        return getReturnType(type.getActualTypeArguments()[0]);
//      } else if (((Class) type.getRawType()).equals(Response.class)) {
//        String sb = "Response<";
//        int i = 0;
//        for (Type item : type.getActualTypeArguments()) {
//          if (i > 0) {
//            sb += ", ";
//          }
//          sb += getReturnType(item);
//          i++;
//        }
//        sb += ">";
//        return sb;
//      } else if (((Class) type.getRawType()).equals(SummaryResponse.class)) {
//        String sb = "SumResponse<";
//        int i = 0;
//        for (Type item : type.getActualTypeArguments()) {
//          if (i > 0) {
//            sb += ", ";
//          }
//          sb += getReturnType(item);
//          i++;
//        }
//        sb += ">";
//        return sb;
//      } else if (((Class) type.getRawType()).equals(List.class)) {
//        return String.format("List<%s>", getReturnType(type.getActualTypeArguments()[0]));
//      }
//      return getName(((Class) type.getRawType()).getSimpleName());
//    }
//  }
//
//  private static String getParamType(Class<?> clazz) {
//    if (clazz.equals(String.class)) {
//      return "String";
//    } else if (clazz.equals(Integer.class) || clazz.equals(int.class) || clazz.equals(long.class)) {
//      return "Int";
//    } else if (clazz.equals(BigDecimal.class) || clazz.equals(double.class)) {
//      return "Double";
//    } else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
//      return "Bool";
//    } else if (clazz.equals(Date.class)) {
//      return "Date";
//    } else if (clazz.isArray()) {
//      String name = clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 2);
//      return String.format("[%s]", name);
//    } else {
//      String type = getName(clazz.getSimpleName());
//      if (!clazz.isEnum()) {
//        type += "Model";
//      }
//      return type;
//    }
//  }
//
//  public static class MethodType {
//    private String method;
//    private String url;
//    private String body;
//    private String name;
//    private boolean domain;
//    private boolean platform;
//    private List<String> parts = new ArrayList<>();
//    private Map<String, String> paths = new LinkedHashMap<>();
//    private Map<String, String> globalPaths = new LinkedHashMap<>();
//    private Map<String, String> paramaters = new LinkedHashMap<>();
//    private String returnType;
//
//    public boolean hasParameter() {
//      boolean result = body != null || paramaters.size() > 0 || parts.size() > 0 || domain;
//      for (String key : paths.keySet()) {
//        result = result || !globalPathMap.keySet().contains(key);
//      }
//      return result;
//    }
//
//    public String getMethod() {
//      return method;
//    }
//
//    public void setMethod(String method) {
//      this.method = method;
//    }
//
//    public String getUrl() {
//      return url;
//    }
//
//    public void setUrl(String url) {
//      this.url = url;
//    }
//
//    public String getBody() {
//      return body;
//    }
//
//    public void setBody(String body) {
//      this.body = body;
//    }
//
//    public Map<String, String> getPaths() {
//      return paths;
//    }
//
//    public void setPaths(Map<String, String> paths) {
//      this.paths = paths;
//    }
//
//    public Map<String, String> getParamaters() {
//      return paramaters;
//    }
//
//    public void setParamaters(Map<String, String> paramaters) {
//      this.paramaters = paramaters;
//    }
//
//    public String getName() {
//      return name;
//    }
//
//    public void setName(String name) {
//      this.name = name;
//    }
//
//    public Map<String, String> getGlobalPaths() {
//      return globalPaths;
//    }
//
//    public void setGlobalPaths(Map<String, String> globalPaths) {
//      this.globalPaths = globalPaths;
//    }
//
//    public List<String> getParts() {
//      return parts;
//    }
//
//    public void setParts(List<String> parts) {
//      this.parts = parts;
//    }
//
//    public boolean isDomain() {
//      return domain;
//    }
//
//    public void setDomain(boolean domain) {
//      this.domain = domain;
//    }
//
//    public boolean isPlatform() {
//      return platform;
//    }
//
//    public void setPlatform(boolean platform) {
//      this.platform = platform;
//    }
//
//    public String getReturnType() {
//      return returnType;
//    }
//
//    public void setReturnType(String returnType) {
//      this.returnType = returnType;
//    }
//  }
//
//  public static void buildModel(File dir, File outputDir) throws Exception {
//    if (dir == null || outputDir == null) {
//      return;
//    }
//    outputDir.mkdirs();
//    for (File file : dir.listFiles()) {
//      if (file.isDirectory()) {
//        buildModel(new File(dir, file.getName()),
//            new File(outputDir, getUpperName(file.getName())));
//      } else {
//        if (file.getName().startsWith(".")) {
//          continue;
//        }
//
//        String packageName = readFile(file);
//        Class<?> clazz = ClassLoader.getSystemClassLoader()
//            .loadClass(String.format("%s.%s", packageName, getSimpleName(file.getName())));
//        if (clazz.isEnum()) {
//          buildEnum(outputDir, clazz);
//        } else {
//          buildMappable(outputDir, clazz);
//        }
//      }
//    }
//  }
//
//  public static String readFile(File file) throws Exception {
//    FileReader reader = new FileReader(file);
//    try {
//      BufferedReader bufferedReader = new BufferedReader(reader);
//      String line = null;
//      List<String> contents = new LinkedList<>();
//      String packageName = null;
//      boolean reading = false;
//      do {
//        line = bufferedReader.readLine();
//        if (line != null) {
//          if (line.contains("/*")) {
//            reading = true;
//          }
//          if (reading) {
//            if (!line.contains("*") && line.trim().startsWith("Observable")) {
//              methodContent.put(line + file.getName(), contents);
//              contents = new LinkedList<>();
//              reading = false;
//            } else if (line.contains("interface")) {
//              contents = new LinkedList<>();
//              reading = false;
//            } else if (line.contains("*")) {
//              contents.add(line.trim());
//            }
//          }
//
//          if (line.startsWith("package")) {
//            packageName = line.replace("package", "").replace(";", "").trim();
//          }
//
//          fileContent.add(file.getName() + line);
//        }
//      } while (line != null);
//      return packageName;
//    } finally {
//      IOUtils.closeQuietly(reader);
//    }
//  }
//
//  public static String findFieldComment(String clazz, Field field) {
//    for (String item : fileContent) {
//      if (item.contains(field.getType().getSimpleName() + " " + field.getName() + ";")
//          && item.contains(clazz)) {
//        int index = item.indexOf(";") + 1;
//        if (index < item.length())
//          return item.substring(index);
//      }
//    }
//    return null;
//  }
//
//  public static List<String> findMethodComment(String clazzName, String method) {
//    for (String key : methodContent.keySet()) {
//      if (key.contains(method + "(") && key.contains(clazzName)) {
//        return methodContent.get(key);
//      }
//    }
//    return null;
//  }
//
//  public static void buildEnum(File parent, Class clazz) throws IOException {
//    File file = new File(parent, getName(clazz.getSimpleName()) + ".swift");
//    FileWriter writer = null;
//    try {
//      writer = new FileWriter(file, true);
//      writer.write(String.format("enum %s: String {\n", getName(clazz.getSimpleName())));
//      Map<String, String> nameMap = new LinkedHashMap<>();
//      for (Field field : clazz.getFields()) {
//        writer.write(String.format("\tcase %s\n", field.getName()));
//        Object obj = Enum.valueOf(clazz, field.getName());
//        try {
//          Field nameField = clazz.getDeclaredField("name");
//          nameField.setAccessible(true);
//          nameMap.put(field.getName(), (String) nameField.get(obj));
//        } catch (Exception e) {
//
//          System.out.println("=--------" + clazz.getName());
//          e.printStackTrace();
//        }
//      }
//      writer.write(
//          String.format("\n\tstatic func values() -> [%s] {\n", getName(clazz.getSimpleName())));
//      writer.write("\t\treturn [");
//      int i = 0;
//      for (Field field : clazz.getFields()) {
//        if (i > 0) {
//          writer.write(", ");
//        }
//        writer.write("." + field.getName());
//        i++;
//      }
//      writer.write("]\n\t}\n");
//      if (nameMap.keySet().size() > 0) {
//        writer.write("\n\tfunc toString() -> String {\n");
//        writer.write("\t\tswitch self {\n");
//        for (String key : nameMap.keySet()) {
//          writer.write(String.format("\t\tcase .%s:\n", key));
//          writer.write(String.format("\t\t\treturn \"%s\"\n", nameMap.get(key)));
//        }
//        writer.write("\t\t}\n");
//        writer.write("\t}\n");
//      }
//      writer.write("}");
//      writer.flush();
//    } finally {
//      IOUtils.closeQuietly(writer);
//    }
//  }
//
//  public static void buildMappable(File parent, Class<?> clazz)
//      throws IOException, IllegalAccessException, InstantiationException {
//    Field[] fields = clazz.getDeclaredFields();
//    if (fields.length == 0) {
//      return;
//    }
//    File file = new File(parent, getName(clazz.getSimpleName()) + "Model.swift");
//    FileWriter writer = null;
//    try {
//      writer = new FileWriter(file, true);
//      writer.write("import Foundation\n");
//      writer.write("import ObjectMapper\n\n");
//      String superClass = "";
//      boolean extend = false;
//      if (!clazz.getSuperclass().equals(Object.class)) {
//        superClass = getName(clazz.getSuperclass().getSimpleName()) + "Model";
//        extend = true;
//      } else {
//        superClass = "Mappable";
//      }
//      writer
//          .write(
//              String.format("class %sModel: %s {\n", getName(clazz.getSimpleName()), superClass));
//
//      for (Field field : fields) {
//        if (field.getName().equals("serialVersionUID")) {
//          continue;
//        }
//        if (Modifier.isStatic(field.getModifiers())) {
//          if (field.getGenericType() instanceof ParameterizedType) {
//            continue;
//          }
//          field.setAccessible(true);
//          Object value = field.get(clazz);
//          if (field.getType().isAssignableFrom(String.class)) {
//            value = String.format("\"%s\"", value);
//          }
//          writer.write(String.format("\tstatic let %s = %s\n", field.getName(), value));
//          continue;
//        }
//        String type;
//        if (field.getType().isAssignableFrom(BigDecimal.class)
//            || field.getType().isAssignableFrom(double.class)) {
//          type = "Double = 0";
//        } else if (field.getType().isAssignableFrom(Integer.class)
//            || field.getType().isAssignableFrom(int.class)
//            || field.getType().isAssignableFrom(long.class)) {
//          type = "Int = 0";
//        } else if (field.getType().isArray()) {
//          String name = field.getType().getSimpleName().substring(0,
//              field.getType().getSimpleName().length() - 2);
//          type = String.format("[%s] = []", name);
//        } else if (field.getType().isAssignableFrom(List.class)) {
//          ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
//          Class item = ((Class) parameterizedType.getActualTypeArguments()[0]);
//          String name = getName(item.getSimpleName());
//          if (!item.equals(String.class) && !item.isEnum()) {
//            name += "Model";
//          }
//          type = String.format("[%s] = []", name);
//        } else if (field.getType().isAssignableFrom(Set.class)) {
//          ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
//          Class item = ((Class) parameterizedType.getActualTypeArguments()[0]);
//          String name = getName(item.getSimpleName());
//          if (!item.equals(String.class) && !item.isEnum()) {
//            name += "Model";
//          }
//          type = String.format("Set<%s> = []", name);
//        } else if (field.getType().isAssignableFrom(Map.class)) {
//          type = "Bool = false";
//        } else if (field.getType().isAssignableFrom(Boolean.class)
//            || field.getType().isAssignableFrom(boolean.class)) {
//          type = "Bool = false";
//        } else if (field.getType().isAssignableFrom(String.class)) {
//          type = "String?";
//        } else if (field.getType().isAssignableFrom(Date.class)) {
//          type = "Date";
//          field.setAccessible(true);
//          if (field.get(clazz.newInstance()) != null) {
//            type += " = Date()";
//          } else {
//            type += "?";
//          }
//        } else {
//          type = getName(field.getType().getSimpleName());
//          if (!field.getType().isEnum()) {
//            type += "Model?";
//          } else {
//            field.setAccessible(true);
//            if (field.get(clazz.newInstance()) != null) {
//              type += " = " + type + "." + ((Enum) field.get(clazz.newInstance())).name();
//            } else {
//              type += "?";
//            }
//          }
//        }
//        String comment = findFieldComment(clazz.getSimpleName(), field);
//        if (comment != null) {
//          writer.write("\t" + comment + "\n");
//        }
//        writer.write(String.format("\tvar %s: %s\n", field.getName(), type));
//      }
//
//      writer.write("\n\t");
//      if (extend) {
//        writer.write("override ");
//      }
//      writer.write("init() {\n");
//      if (extend) {
//        writer.write("\t\tsuper.init()\n");
//      }
//      writer.write("\t}\n\t");
//
//      writer.write("\n\trequired init?(map: Map) {\n");
//      if (extend) {
//        writer.write("\t\tsuper.init(map: map)\n");
//      }
//      writer.write("\t}\n\n\t");
//
//      if (extend) {
//        writer.write("override ");
//      }
//      writer.write("func mapping(map: Map) {\n");
//      if (extend) {
//        writer.write("\t\tsuper.mapping(map: map)\n");
//      }
//      for (Field field : fields) {
//        if (field.getName().equals("serialVersionUID")) {
//          continue;
//        }
//        if (Modifier.isStatic(field.getModifiers())) {
//          continue;
//        }
//        if (field.getType().isAssignableFrom(Date.class)) {
//          writer.write(String.format("\t\t%s <- (map[\"%s\"], DateFormatterTransform())\n",
//              field.getName(), field.getName()));
//        } else {
//          writer.write(String.format("\t\t%s <- map[\"%s\"]\n", field.getName(), field.getName()));
//        }
//      }
//      writer.write("\t}\n");
//
//      writer.write("}");
//      writer.flush();
//    } finally {
//      IOUtils.closeQuietly(writer);
//    }
//  }
//
//  private static String getSimpleName(String name) {
//    System.out.println(name);
//    return name.substring(0, name.indexOf("java") - 1);
//  }
//
//  private static String getUpperName(String name) {
//    return name.substring(0, 1).toUpperCase() + name.substring(1);
//  }
//
//  private static String getRouterName(String name) {
//    if (name.endsWith("ServiceApi")) {
//      return name.replace("ServiceApi", "Router");
//    } else if (name.endsWith("Service2Api")) {
//      return name.replace("Service2Api", "2Router");
//    } else {
//      return name;
//    }
//  }
//
//  private static String getName(String name) {
//    if (name.endsWith("Model")) {
//      name = name.substring(0, name.length() - 5);
//    }
//    if (Character.isUpperCase(name.charAt(0)) && Character.isUpperCase(name.charAt(1))
//        && !name.toUpperCase().equals("SSKU")) {
//      return name.substring(1);
//    } else {
//      return name;
//    }
//  }
//}
