package org.jeesl.interfaces.qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Qualifier
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE,ElementType.METHOD })
public @interface RestDescription
{
   String label() default "";
   String description() default "";
   int orderNr() default 0;
   boolean withListing() default true;
}