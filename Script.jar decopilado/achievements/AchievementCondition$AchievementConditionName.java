/*
 * Decompiled with CFR 0.152.
 */
package achievements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public static @interface AchievementCondition.AchievementConditionName {
    public String value();
}
