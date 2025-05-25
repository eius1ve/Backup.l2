/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Effect;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public class EffectChangeSex
extends Effect {
    public EffectChangeSex(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
    }

    @Override
    public void onStart() {
        if (!this._effected.isPlayer()) {
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE characters SET sex = ? WHERE obj_Id = ?");
            preparedStatement.setInt(1, this._effected.getPlayer().getSex() == 1 ? 0 : 1);
            preparedStatement.setInt(2, this._effected.getPlayer().getObjectId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException sQLException) {
            try {
                throw new RuntimeException(sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        this._effected.getPlayer().setHairColor(0);
        this._effected.getPlayer().setHairStyle(0);
        this._effected.getPlayer().setFace(0);
        this._effected.getPlayer().logout();
        super.onStart();
    }

    @Override
    public boolean onActionTime() {
        return false;
    }
}
