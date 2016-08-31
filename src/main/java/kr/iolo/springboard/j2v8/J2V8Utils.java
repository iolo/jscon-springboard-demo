package kr.iolo.springboard.j2v8;

import com.eclipsesource.v8.*;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * TODO: more sophisticated marshaling
 *
 * @author iolo
 * @see NodeJS
 * @see V8
 * @see com.eclipsesource.v8.utils.V8ObjectUtils
 */
public class J2V8Utils {

    private static <T> T withServerModule(Function<V8Object, T> function) {
        final NodeJS nodeJS = NodeJS.createNodeJS();
        final V8Object serverModule = nodeJS.require(new File("target/server.bundle.js"));
        try {
            //System.out.println(toDebugString(serverModule.twin()));
            T result = function.apply(serverModule);
            while (nodeJS.isRunning()) {
                nodeJS.handleMessage();
            }
            return result;
        } finally {
            //serverModule.release();
            //nodeJS.release();
        }
    }

    public static String render(String template, Map<String, Object> model, String url) {
        return withServerModule((serverModule) -> {
            final V8Array args = new V8Array(serverModule.getRuntime())
                    .push(template)
                    .push(toV8Object(serverModule.getRuntime(), model))
                    .push(url);
            return serverModule.executeStringFunction("render", args);
        });
    }

    public static String renderToString(String type, Map<String, ?> props) {
        return withServerModule((serverModule) -> {
            final V8Array args = new V8Array(serverModule.getRuntime())
                    .push(type)
                    .push(toV8Object(serverModule.getRuntime(), props));
            return serverModule.executeStringFunction("renderToString", args);
        });
    }

    public static void renderReactRouter(String location, BiConsumer<String, String> callback) {
        withServerModule((serverModule) -> {
            final V8Function jsCallback = new V8Function(serverModule.getRuntime(), (receiver, params) -> {
                callback.accept(params.getString(0), params.getString(1));
                return null;
            });
            final V8Array args = new V8Array(serverModule.getRuntime())
                    .push(location)
                    .push(jsCallback);
            serverModule.executeVoidFunction("renderReactRouter", args);
            return null;
        });
    }

    public static V8Object toV8Object(V8 v8, Map<?, ?> map) {
        final V8Object v8obj = new V8Object(v8);
        map.forEach((key, value) -> addToV8Object(v8, v8obj, (String) key, value));
        return v8obj;
    }

    public static V8Array toV8Array(V8 v8, Iterable elements) {
        final V8Array v8array = new V8Array(v8);
        for (final Object element : elements) {
            pushToV8Array(v8, v8array, element);
        }
        return v8array;
    }

    public static V8Array toV8Array(V8 v8, Object[] elements) {
        final V8Array v8array = new V8Array(v8);
        for (final Object element : elements) {
            pushToV8Array(v8, v8array, element);
        }
        return v8array;
    }

    public static void addToV8Object(V8 v8, V8Object v8obj, String key, Object value) {
        if (key == null || !key.matches("\\w+")) {
            return;
        }
        if (value == null) {
            v8obj.addNull(key);
        } else if (value instanceof String) {
            v8obj.add(key, (String) value);
        } else if (value instanceof Integer) {
            v8obj.add(key, (Integer) value);
        } else if (value instanceof Number) {
            v8obj.add(key, ((Number) value).doubleValue());
        } else if (value instanceof Boolean) {
            v8obj.add(key, (Boolean) value);
        } else if (value instanceof Date) {
            // FIXME:
            v8obj.add(key, ((Date) value).getTime());
        } else if (value instanceof V8Value) {
            v8obj.add(key, (V8Value) value);
        } else {
            v8obj.add(key, (V8Value) toV8Value(v8, value));
            //v8obj.addUndefined(key);
        }
    }

    public static void pushToV8Array(V8 v8, V8Array v8array, Object value) {
        if (value == null) {
            v8array.pushNull();
        } else if (value instanceof String) {
            v8array.push((String) value);
        } else if (value instanceof Integer) {
            v8array.push((Integer) value);
        } else if (value instanceof Number) {
            v8array.push(((Number) value).doubleValue());
        } else if (value instanceof Boolean) {
            v8array.push((Boolean) value);
        } else if (value instanceof V8Value) {
            v8array.push((V8Value) value);
        } else {
            v8array.push((V8Value) toV8Value(v8, value));
            //v8array.pushUndefined();
        }
    }

    // FIXME: cyclic 을 참조를 포함한 object 로 인한 무한 recursion 을 피하기 위해..
    private static final Stack<Object> cyclicStack = new Stack<>();

    public static Object toV8Value(V8 v8, Object obj) {
        if (obj == null) {
            return obj;
        } else if (obj instanceof String) {
            return obj;
        } else if (obj instanceof Number) {
            return obj;
        } else if (obj instanceof Boolean) {
            return obj;
        } else if (obj instanceof V8Value) {
            return obj;
        } else if (obj instanceof Map) {
            return toV8Object(v8, (Map) obj);
        } else if (obj instanceof Iterable) {
            return toV8Array(v8, (Iterable) obj);
        } else {
            if (cyclicStack.contains(obj)) {
                System.out.println("cyclic!!!");
                return null;
            }
            try {
                cyclicStack.push(obj);
                final V8Object v8obj = new V8Object(v8);
                final BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), obj.getClass().getSuperclass());
                for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                    final Method reader = pd.getReadMethod();
                    if (reader != null) {
                        addToV8Object(v8, v8obj, pd.getName(), reader.invoke(obj));
                    }
                }
                return v8obj;
            } catch (Exception e) {
                return null;
            } finally {
                cyclicStack.pop();
            }
            //@Autowired
            //ObjectMapper objectMapper;
            //return toV8Object(objectMapper.convertValue(obj, Map.class));
        }
    }

    public static String toDebugString(V8Object obj) {
        final StringBuilder sb = new StringBuilder();
        final V8Object twin = obj.twin();
        try {
            sb.append("{\n");
            for (final String key : twin.getKeys()) {
                sb.append("\t").append(key).append("=").append(twin.get(key));
            }
            return sb.append("}\n").toString();
        } finally {
            twin.release();
        }
    }

}
