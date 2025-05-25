/*
 * Decompiled with CFR 0.152.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import services.CommandClassMaster;

static class CommandClassMaster.1
extends ArrayList<CommandClassMaster.ClassMasterPath> {
    final /* synthetic */ List val$a;
    final /* synthetic */ List val$b;

    CommandClassMaster.1(List list, List list2) {
        this.val$a = list;
        this.val$b = list2;
        this.addAll(this.val$a);
        this.addAll(this.val$b);
    }
}
