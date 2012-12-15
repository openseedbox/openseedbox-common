package com.openseedbox.gson;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.openseedbox.code.MessageException;
import com.openseedbox.code.Util;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import play.Logger;

public class AccessorBasedTypeAdapterFactory implements TypeAdapterFactory {

	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> tt) {
		Class<? super T> t = tt.getRawType();
		if (t.isAnnotationPresent(UseAccessor.class)) {
			return (TypeAdapter<T>) new AccessorBasedTypeAdaptor(gson, t, t);
		} else {
			//check interfaces
			Class[] interfaces = t.getInterfaces();
			for (Class i : interfaces) {
				if (i.isAnnotationPresent(UseAccessor.class)) {
					return (TypeAdapter<T>) new AccessorBasedTypeAdaptor(gson, i, t);
				}
			}
		}
		return null;
	}

	public class AccessorBasedTypeAdaptor<T> extends TypeAdapter<T> {

		private Gson gson;
		private Class requestedClass;
		private Class classWithTheAnnotations; //not necessarily the class being serialized/deserialized - sometimes the annotations are on the interface

		public AccessorBasedTypeAdaptor(Gson gson, Class classWithTheAnnotations, Class requestedClass) {
			this.gson = gson;
			this.classWithTheAnnotations = classWithTheAnnotations;
			this.requestedClass = requestedClass;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void write(JsonWriter out, T value) throws IOException {
			out.beginObject();			
			for (Method method : value.getClass().getMethods()) {		
				try {
					String name = getJsonFieldName(method);
					if (name == null) { continue; }
					Object returnValue = method.invoke(value);
					if (returnValue != null) {
						TypeToken<?> token = TypeToken.get(returnValue.getClass());
						TypeAdapter adapter = gson.getAdapter(token);
						out.name(name);
						adapter.write(out, returnValue);
					}
				} catch (IllegalAccessException ex) {
					throw new MessageException(Util.getStackTrace(ex));
				} catch (InvocationTargetException ex) {
					throw new MessageException(Util.getStackTrace(ex));
				}
			}
			out.endObject();
		}
		
		@Override
		public T read(JsonReader in) throws IOException {			
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			String currentName = null;
			try {
				Object o = requestedClass.newInstance();
				Method[] methods = requestedClass.getMethods();	
				
				Map<String, Method> jsonNames = getJsonFieldNames(methods);
				
				in.beginObject();
				while (in.hasNext()) {	
					if (in.peek() != JsonToken.NAME) {
						in.skipValue(); continue;
					}
					String name = in.nextName();	
					currentName = name;
					Method m = jsonNames.get(name);
					if (m != null) {
						//Replace the "get" at the start with "set" to get the set method name
						String setterName;
						if (m.getName().startsWith("is")) {
							setterName = m.getName().substring("is".length());
						} else {
							setterName = m.getName().substring("get".length());
						}
						setterName = "set" + setterName;
						Method setterMethod = getMethodByName(methods, setterName);
						if (setterMethod != null) {
							Class[] paramTypes = setterMethod.getParameterTypes();
							if (paramTypes.length > 0) {
								//call the set method, passing in the value	
								Class paramType = paramTypes[0];
								setterMethod.invoke(o, gson.fromJson(in, paramType));
							}							
						}						
					}
				}
				in.endObject();
				return (T) o;
			} catch (InstantiationException ex) {
				throw new MessageException(Util.getStackTrace(ex));
			} catch (IllegalAccessException ex) {
				throw new MessageException(Util.getStackTrace(ex));
			} catch (InvocationTargetException ex) {
				throw new MessageException(Util.getStackTrace(ex));
			} catch (JsonSyntaxException ex) {
				Logger.error("Error while processing %s: %s", currentName, ex);
			}
			return null;
		}
		
		//check this class, then interfaces
		private String getJsonFieldName(Method method) {			
			if (!method.isAnnotationPresent(SerializedAccessorName.class)) {
				//find the method in the "classWithAnnotations" class and check it
				Method[] methods = classWithTheAnnotations.getMethods();
				for (Method m : methods) {
					if (m.getName().equals(method.getName())) {
						if (m.isAnnotationPresent(SerializedAccessorName.class)) {
							return m.getAnnotation(SerializedAccessorName.class).value();
						}							
					}
				}							
			} else {
				return method.getAnnotation(SerializedAccessorName.class).value();
			}
			return null;
		}
		
		private Map<String, Method> getJsonFieldNames(Method[] methods) {
			Map<String, Method> ret = new HashMap<String, Method>();
			for(Method m : methods) {
				String name = getJsonFieldName(m);
				if (name != null) {
					ret.put(name, m);
				}
			}
			return ret;
		}
		
		private Method getMethodByName(Method[] methods, String name) {
			for (Method m : methods) {
				if (m.getName().equals(name)) {
					return m;
				}
			}
			return null;
		}
	}
}
