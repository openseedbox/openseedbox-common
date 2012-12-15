/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openseedbox.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializedAccessorName {
	String value();
}
