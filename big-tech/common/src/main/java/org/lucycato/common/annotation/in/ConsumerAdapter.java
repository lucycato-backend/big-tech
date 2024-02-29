package org.lucycato.common.annotation.in;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ConsumerAdapter {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
